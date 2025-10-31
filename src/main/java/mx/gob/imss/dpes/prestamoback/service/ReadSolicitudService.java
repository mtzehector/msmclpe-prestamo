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
import mx.gob.imss.dpes.interfaces.solicitud.model.Solicitud;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.SolicitudClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author eduardo.loyo
 */
@Provider
public class ReadSolicitudService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    @RestClient
    private SolicitudClient service;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {
        
        log.log( Level.INFO, "Request hacia SolicitudBack: {0}", request.getPayload());
        Response load = service.load( request.getPayload().getSolicitud().getId() );
        if (load.getStatus() == 200) {
            Solicitud solicitud = load.readEntity(Solicitud.class);
            request.getPayload().setSolicitud(solicitud);
            // Incluier el assembler
            return new Message<>(request.getPayload());
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }

}
