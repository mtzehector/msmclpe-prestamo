package mx.gob.imss.dpes.prestamoback.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.prestamoback.entity.Prestamo;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoException;
import mx.gob.imss.dpes.prestamoback.respository.PrestamoBySolicitudSpecification;
import mx.gob.imss.dpes.prestamoback.respository.PrestamoRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PrestamoPersistenceService extends BaseCRUDService<Prestamo, Prestamo, Long, Long> {

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

    /**
     * @param request
     * @return
     * @throws BusinessException
     */
    public Message<Prestamo> execute(Message<Prestamo> request) throws BusinessException {
        try {
            Prestamo saved = save(request.getPayload());
            return new Message<>(saved);
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PrestamoPersistenceService.execute - [" + request + "]", e);
            throw new PrestamoException(PrestamoException.ERROR_DE_ESCRITURA_EN_BD);
        }
    }

    public Message<Prestamo> consultaPrestamoPorSolicitud(Message<Prestamo> request) throws BusinessException {
        Collection<BaseSpecification> constraints = new ArrayList<>();
        constraints.add(new PrestamoBySolicitudSpecification(request.getPayload().getSolicitud()));
        Prestamo prestamo = findOne(constraints);

        if (prestamo != null) {
            return new Message<>(prestamo);
        }
        return new Message<>(prestamo);
    }

}
