package mx.gob.imss.dpes.prestamoback.service;

import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.prestamoback.entity.Prestamo;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoException;
import mx.gob.imss.dpes.prestamoback.respository.PrestamoRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.logging.Level;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PrestamoService extends BaseCRUDService<Prestamo, Prestamo, Long, Long> {
    @Autowired
    private PrestamoRepository repository;

    @Override
    public JpaSpecificationExecutor<Prestamo> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<Prestamo, Long> getJpaRepository() {
        return repository;
    }

    public List<Prestamo> obtenerListaPrestamosPorIdSolicitud(List<Long> idSolicitudList) throws BusinessException {
        try {
            return repository.buscarPrestamosPorIdSolicitud(idSolicitudList);
        }catch(Exception e){
            log.log(Level.SEVERE, "ERROR PrestamoService.obtenerListaPrestamosPorIdSolicitud()",e);
            throw new PrestamoException(PrestamoException.ERROR_DE_LECTURA_DE_BD);
        }
    }

}
