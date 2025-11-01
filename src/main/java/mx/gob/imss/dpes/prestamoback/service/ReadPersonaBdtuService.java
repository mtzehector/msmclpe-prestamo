/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import mx.gob.imss.dpes.common.service.ServiceDefinition;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.serviciosdigitales.model.Persona;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

import mx.gob.imss.dpes.interfaces.serviciosdigitales.model.bdtu.BDTURequest;
import mx.gob.imss.dpes.interfaces.serviciosdigitales.model.bdtu.BDTURequestIn;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.PersonaBDTUOnlyClient;
import mx.gob.imss.dpes.prestamoback.restclient.PersonabdtuClient;

/**
 * @author salvador.pocteco
 */
@Provider
public class ReadPersonaBdtuService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    @RestClient
    private PersonabdtuClient personaClient;
    
    //@Inject
    //@RestClient
    //private PersonaBDTUOnlyClient personabdtuOnlyClient;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {

        Response respuesta;
        BDTURequestIn requestIn = new BDTURequestIn(request.getPayload().getSolicitud().getCurp(), request.getPayload().getSolicitud().getNss());
        log.log(Level.INFO, "El request a persona BDTU es : {0}", requestIn);
        
        //if(request.getPayload().getFlatPrestamoPromotor() == 1){//1 == Prestamo por promotor
        //     respuesta = personabdtuOnlyClient.load(requestIn);
        //}else{
             respuesta = personaClient.persona(requestIn);
        //}
       
        if (respuesta.getStatus() == 200) {

            BDTURequest bdturequest = respuesta.readEntity(BDTURequest.class);

            Persona persona = new Persona();
            persona.setNombre(bdturequest.getBdtuOut().getNombre());
            persona.setPrimerApellido(bdturequest.getBdtuOut().getPrimerApellido());
            persona.setSegundoApellido(bdturequest.getBdtuOut().getSegundoApellido());

            persona.setCurp(bdturequest.getBdtuOut().getCurp());
            persona.setNss(bdturequest.getBdtuOut().getNss());

            //persona.setFechaNacimiento(bdturequest.getBdtuOut().getFechaNacimiento());
            //persona.setCveIdPersona(bdturequest.getBdtuOut().getCveIdPersona());
            //persona.setSexo(bdturequest.getBdtuOut().getSexo());
            
            if(request.getPayload().getFlatPrestamoPromotor() == 0){
                 persona.setCorreoElectronico(bdturequest.getUserData().getNOM_USUARIO());
                 persona.setTelefono(bdturequest.getUserData().getTEL_CELULAR().toString());
            }else{
                 persona.setCorreoElectronico(request.getPayload().getPersona().getCorreoElectronico());
                 persona.setTelefono(request.getPayload().getPersona().getTelefono());
            }

            request.getPayload().setPersona(persona);
            log.log(Level.INFO, "Los datos de la Persona pensionada son {0}", request.getPayload().getPersona());
            return new Message<>(request.getPayload());
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }
}
