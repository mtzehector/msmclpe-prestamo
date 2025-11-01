package mx.gob.imss.dpes.prestamoback.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.prestamoback.model.DelegacionModel;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.DelegacionCatalogoClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
public class ConsultaDelegacionService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {
    @Inject
    @RestClient
    DelegacionCatalogoClient delegacionCatalogoClient;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {

        try {
            log.log(Level.INFO, "Buscando informacion de la delegacion: {0}", request.getPayload().getSolicitud().getDelegacion());
            Response response = delegacionCatalogoClient.getDelegacion(request.getPayload().getSolicitud().getDelegacion());

            if(response != null && response.getStatus() == 200) {
                DelegacionModel delegacion = response.readEntity(DelegacionModel.class);
                request.getPayload().getResumenCartaInstruccion().setDelegacion(delegacion.getNumDelegacion());
                request.getPayload().getResumenCartaInstruccion().setDelegacionDesc(delegacion.getDesDelegacion());
                return request;
            }
        }
        catch(Exception e) {
            log.log(Level.SEVERE, "ConsultaDelegacionService.execute - Buscando informacion de la delegacion", e);
        }

        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }
}
