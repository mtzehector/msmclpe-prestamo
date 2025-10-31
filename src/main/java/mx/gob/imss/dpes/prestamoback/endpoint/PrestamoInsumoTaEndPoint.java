package mx.gob.imss.dpes.prestamoback.endpoint;

import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.prestamoback.assembler.PrestamoInsumoTaAssembler;
import mx.gob.imss.dpes.prestamoback.entity.McltPrestamoInsumoTa;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoInsumoTaException;
import mx.gob.imss.dpes.prestamoback.service.PrestamoInsumoTaService;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@ApplicationScoped
@Path("/prestamoInsumosTa")
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PrestamoInsumoTaEndPoint extends
    BaseGUIEndPoint<McltPrestamoInsumoTa, McltPrestamoInsumoTa, McltPrestamoInsumoTa> {

    @Inject
    private PrestamoInsumoTaService prestamoInsumoService;

    @Inject
    private PrestamoInsumoTaAssembler prestamoInsumoAssembler;

    @GET
    @Path("/{idSolicitud}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrestamoInsumoPorCveSolicitud(@PathParam("idSolicitud") Long cveSolicitud) {
        try {
            McltPrestamoInsumoTa prestamoInsumo =
                prestamoInsumoService.obtenerPrestamoInsumoPorCveSolicitud(cveSolicitud);

            if (prestamoInsumo == null)
                return Response.noContent().build();

            return toResponse(new Message<>(
                prestamoInsumoAssembler.assemble(prestamoInsumo)
            ));
        }catch (BusinessException e){
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        }catch (Exception e){
            log.log(Level.SEVERE, "ERROR PrestamoInsumoTaEndPoint.obtenerPrestamoInsumoPorCveSolicitud - [" +
                cveSolicitud + "]", e);

            return toResponse(new Message(
                null,
                ServiceStatusEnum.EXCEPCION,
                new PrestamoInsumoTaException(PrestamoInsumoTaException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                null
            ));
        }
    }

}
