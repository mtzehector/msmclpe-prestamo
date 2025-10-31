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
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.OfertaClient;
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.Oferta;
/**
 *
 * @author antonio
 */
@Provider
public class ReadOfertaService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    @RestClient
    private OfertaClient service;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {
        
        log.log( Level.INFO, "Request hacia OfertaBack: {0}", request.getPayload());
        Response load = service.load( request.getPayload().getPrestamo().getIdOferta() );
        if (load.getStatus() == 200) {
            Oferta oferta = load.readEntity(Oferta.class);
            request.getPayload().setOferta(oferta);
            // Incluier el assembler
            return new Message<>(request.getPayload());
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }

}
