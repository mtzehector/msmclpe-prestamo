/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.prestamoback.model.PersonaRequest;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.PersonaClient;
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

/**
 * @author salvador.pocteco
 */
@Provider
public class ReadPersonaService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

  @Inject
  @RestClient
  private PersonaClient personaClient;

  @Override
  public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {

    log.log(Level.INFO, "El request general es : {0}", request.getPayload());
    PersonaRequest personaRequest = new PersonaRequest();
    personaRequest.setCurp(request.getPayload().getSolicitud().getCurp());
    Response respuesta = personaClient.load(personaRequest);

    if (respuesta.getStatus() == 200) {
      Persona persona = respuesta.readEntity(Persona.class);
      request.getPayload().setPersona(persona);
      log.log(Level.INFO, "Los datos de la Persona pensionada son {0}", request.getPayload().getPersona());
      return new Message<>(request.getPayload());
    }
    return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
  }
}
