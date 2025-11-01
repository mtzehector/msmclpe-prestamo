/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularRequest;
import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularResponse;
import mx.gob.imss.dpes.interfaces.sipre.model.DatosContactoTitular;
import mx.gob.imss.dpes.interfaces.sipre.model.TelefonoReinstalacionPrestamo;
import mx.gob.imss.dpes.prestamoback.model.PensionadoRequest;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.PensionadoClient;
import mx.gob.imss.dpes.prestamoback.restclient.SistrapClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.persistence.CacheStoreMode;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import mx.gob.imss.dpes.interfaces.pensionado.model.Pensionado;
import mx.gob.imss.dpes.interfaces.serviciosdigitales.model.Persona;
import mx.gob.imss.dpes.prestamoback.model.PersonaModel;
import mx.gob.imss.dpes.prestamoback.restclient.ConsultaPensionadoClient;

/**
 * @author salvador.pocteco
 */
@Provider
public class ReadPensionadoService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    @RestClient
    private PensionadoClient pensionadoSistrapClient;

    @Inject
    @RestClient
    private ConsultaPensionadoClient pensionadoClient;

    @Inject
    @RestClient
    private SistrapClient sistrapClient;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request)
        throws BusinessException {

        try {
            PensionadoRequest pensionadoRequest = new PensionadoRequest();
            pensionadoRequest.setNss(request.getPayload().getSolicitud().getNss());
            pensionadoRequest.setGrupoFamiliar(request.getPayload().getSolicitud().getGrupoFamiliar());

            //Consulta información de SISTRAP unicamente recupera tipoPension y entidadFederativa
            Response respuesta = pensionadoSistrapClient.load(pensionadoRequest);

            if (respuesta.getStatus() != 200)
                return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);

            Pensionado pensionado = respuesta.readEntity(Pensionado.class);
            request.getPayload().getResumenSimulacion().setTipoPension(pensionado.getTipoPension());
            request.getPayload().getResumenSimulacion().setCiudad(
                    pensionado.getEntidadFederativa().getDescEntidadFederativa());

            //Obtiene la información de la tabla persona de SIPRE2.0
            Response response = pensionadoClient.load(request.getPayload().getSolicitud().getCurp());
            if (response.getStatus() != 200)
                return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);

            PersonaModel personaModel = response.readEntity(PersonaModel.class);

            if (!(personaModel.getNombre() != null && !personaModel.getNombre().isEmpty())) {
                personaModel = this.obtenerDatosTitularSistrap(request);

                if (personaModel == null)
                    return response(null, ServiceStatusEnum.EXCEPCION,
                            new RecursoNoExistenteException(), null);
            }

            Persona persona = new Persona();
            persona.setCurp(request.getPayload().getSolicitud().getCurp());
            persona.setNss(request.getPayload().getSolicitud().getNss());

            persona.setNombre(personaModel.getNombre());
            persona.setPrimerApellido(personaModel.getPrimerApellido());
            persona.setSegundoApellido(personaModel.getSegundoApellido());

            if (request.getPayload().getFlatPrestamoPromotor() == 0) {
                persona.setCorreoElectronico(personaModel.getCorreoElectronico());
                persona.setTelefono(personaModel.getTelCelular());
            } else {
                persona.setCorreoElectronico(request.getPayload().getPersona().getCorreoElectronico());
                persona.setTelefono(request.getPayload().getPersona().getTelefono());
            }

            request.getPayload().setPersona(persona);

            return new Message<>(request.getPayload());
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR ReadPensionadoService.execute - request = [" +
                request + "]", e);
        }

        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }

    private PersonaModel obtenerDatosTitularSistrap(Message<ReporteResumenSimulacion> request) {
        try {

            ConsultaDatosTitularRequest datosTitularRequest = new ConsultaDatosTitularRequest();
            datosTitularRequest.setNss(request.getPayload().getSolicitud().getNss());
            datosTitularRequest.setIdGrupoFamiliar(request.getPayload().getSolicitud().getGrupoFamiliar());

            Response response = sistrapClient.obtenerDatosTituar(datosTitularRequest);

            if (response.getStatus() != 200)
                return null;

            ConsultaDatosTitularResponse datosTitular = response.readEntity(ConsultaDatosTitularResponse.class);

            if (!"200".equals(datosTitular.getCodigoError()))
                return null;

            if (datosTitular.getConsultaDatosPersonalesTitularVO() == null)
                return null;

            PersonaModel persona = new PersonaModel();
            persona.setNombre(datosTitular.getConsultaDatosPersonalesTitularVO().getNombre());
            persona.setPrimerApellido(datosTitular.getConsultaDatosPersonalesTitularVO().getApellidoPaterno());
            persona.setSegundoApellido(datosTitular.getConsultaDatosPersonalesTitularVO().getApellidoMaterno());

            DatosContactoTitular[] datosContactoTitular = datosTitular.getConsultaDatosPersonalesTitularVO().getDatosDeContacto();
            String[] correos = null;
            TelefonoReinstalacionPrestamo[] telefonos = null;
            String telefonoMovil = null;
            String telefonoCasa = null;

            if (!(datosContactoTitular != null && datosContactoTitular.length > 0))
                return persona;

            for (DatosContactoTitular datoContactoTitular : datosContactoTitular) {

                //Realiza la busqueda del correo
                if (persona.getCorreoElectronico() == null &&
                        (correos = datoContactoTitular.getCorreoElectronico()) != null &&
                        correos.length > 0) {
                    persona.setCorreoElectronico(correos[0]);
                }

                if ((telefonoMovil == null || telefonoCasa == null) &&
                        (telefonos = datoContactoTitular.getTelefonos()) != null &&
                        telefonos.length > 0) {

                    for (TelefonoReinstalacionPrestamo telefono : telefonos) {
                        if (telefonoMovil == null && telefono.getTipoNumero() == 3)
                            telefonoMovil = telefono.getNumero().toString();

                        if (telefonoCasa == null && telefono.getTipoNumero() == 1)
                            telefonoCasa = telefono.getNumero().toString();
                    }
                }
            }

            persona.setTelCelular(telefonoMovil != null ? telefonoMovil : telefonoCasa);

            return persona;
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR ReadPensionadoService.obtenerDatosTitularSistrap - request = [" +
                request + "]", e);
        }

        return null;
    }
}
