/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.evento.model.Evento;
import mx.gob.imss.dpes.prestamoback.restclient.EventoClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author antonio
 */
@Provider
public class CreateEventoService extends ServiceDefinition<Evento, Evento> {

    @Inject
    @RestClient
    private EventoClient client;

    @Override
    public Message<Evento> execute(Message<Evento> request) throws BusinessException {
        log.log(Level.INFO,"Events");
        log.log( Level.INFO, "Solicitando el Evento: {0}", request.getPayload());        
        Response event = client.create( request.getPayload() );
        if (event.getStatus() == 200) {
          return request;
        }        
        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }

}
