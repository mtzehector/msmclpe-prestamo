package mx.gob.imss.dpes.prestamoback.endpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.prestamoback.entity.Prestamo;
import mx.gob.imss.dpes.prestamoback.model.PrestamosBySolicitudesRequest;
import mx.gob.imss.dpes.prestamoback.respository.PrestamosBySolicitudesSpecification;
import mx.gob.imss.dpes.prestamoback.service.PrestamosVigentesPersistenceService;


/**
 *
 * @author antonio
 */
@ApplicationScoped
@Path("/prestamosVigentes")
public class ConsultaPrestamosVigentesEndPoint extends BaseGUIEndPoint<PrestamosBySolicitudesRequest, 
        Prestamo, Prestamo>{
    

    @Inject
    private PrestamosVigentesPersistenceService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
  @Override
    public Response create(PrestamosBySolicitudesRequest solicitudes) throws BusinessException {
     
        //Agregar las condiciones de los prestamos a obtener
        //nss y grupo familiar
        log.log(Level.INFO, "solicitudes recibidas para obtener sus prestamos:{0}" , solicitudes.getSolicitudes().size() );
        
        Collection<BaseSpecification> constraints = new ArrayList<>();
        constraints.add( new PrestamosBySolicitudesSpecification( solicitudes ) );
    
        List<Prestamo> prestamos =
           service.load(constraints);
        return Response.ok( prestamos ).build();
    }    
}
