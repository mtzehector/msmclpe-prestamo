package mx.gob.imss.dpes.prestamoback.service;

import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.prestamoback.entity.McltPrestamoInsumoTa;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoInsumoTaException;
import mx.gob.imss.dpes.prestamoback.respository.PrestamoInsumoTaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PrestamoInsumoTaService extends BaseCRUDService<McltPrestamoInsumoTa, McltPrestamoInsumoTa, Long, Long> {

    @Autowired
    private PrestamoInsumoTaRepository repository;

    @Override
    public JpaSpecificationExecutor<McltPrestamoInsumoTa> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltPrestamoInsumoTa, Long> getJpaRepository() {
        return repository;
    }

    public McltPrestamoInsumoTa obtenerPrestamoInsumoPorCveSolicitud(Long cveSolicitud) throws BusinessException {
        try {
            return repository.findByCveSolicitud(cveSolicitud);
        } catch(Exception e) {
            log.log(Level.SEVERE, "ERROR PrestamoInsumoTaService.obtenerPrestamoInsumoTaPorCveSolicitud - [" +
                cveSolicitud + "]",e);

            throw new PrestamoInsumoTaException(PrestamoInsumoTaException.ERROR_DE_LECTURA_DE_BD);
        }
    }
}
