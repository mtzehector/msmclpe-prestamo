package mx.gob.imss.dpes.prestamoback.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.Oferta;
import mx.gob.imss.dpes.prestamoback.entity.McltPrestamoInsumoTa;
import mx.gob.imss.dpes.prestamoback.exception.OfertaException;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.OfertaClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
public class ConsultarOfertaService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    @RestClient
    private OfertaClient ofertaClient;

    @Inject
    private PrestamoInsumoTaService prestamoInsumoTaService;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> resumenSimulacionRequest) throws BusinessException {

        try {
            Response responseOferta = ofertaClient.load(resumenSimulacionRequest.getPayload().getPrestamo().getIdOferta());
            if (responseOferta != null && responseOferta.getStatus() == 200){
                Oferta oferta = responseOferta.readEntity(Oferta.class);
                McltPrestamoInsumoTa prestamoInsumo = prestamoInsumoTaService.obtenerPrestamoInsumoPorCveSolicitud(
                        resumenSimulacionRequest.getPayload().getSolicitud().getId()
                );
                if (prestamoInsumo != null){
                    oferta.setCat(prestamoInsumo.getCat() == null ? 0.0 : prestamoInsumo.getCat().doubleValue());
                    resumenSimulacionRequest.getPayload().setOferta(oferta);
                    return resumenSimulacionRequest;
                }
            }
        }catch (Exception e){
            log.log(Level.SEVERE, "ConsultarOfertaService.execute - obtener datos de oferta y cat", e);
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new OfertaException(), null);
    }
}
