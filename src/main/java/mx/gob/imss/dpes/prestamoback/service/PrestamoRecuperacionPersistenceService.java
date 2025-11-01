/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.prestamoback.entity.PrestamoRecuperacion;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoException;
import mx.gob.imss.dpes.prestamoback.respository.PrestamoBySolicitudSpecification;
import mx.gob.imss.dpes.prestamoback.respository.PrestamoRecuperacionRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juan.garfias
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PrestamoRecuperacionPersistenceService extends BaseCRUDService<PrestamoRecuperacion, PrestamoRecuperacion, Long, Long> {

    @Autowired
    private PrestamoRecuperacionRepository repository;

    @Override
    public JpaSpecificationExecutor<PrestamoRecuperacion> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<PrestamoRecuperacion, Long> getJpaRepository() {
        return repository;
    }

    /**
     *
     * @param request
     * @return
     * @throws BusinessException
     */
    public Message<PrestamoRecuperacion> execute(Message<PrestamoRecuperacion> request) throws BusinessException {
        try {
            PrestamoRecuperacion saved = save(request.getPayload());
            return new Message<>(saved);
        }  catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PrestamoRecuperacionPersistenceService.execute - [" + request + "]", e);
            throw new PrestamoException(PrestamoException.ERROR_DE_ESCRITURA_EN_BD);
        }
    }

    public Message<PrestamoRecuperacion> consultaPrestamoPorSolicitud(Message<PrestamoRecuperacion> request) throws BusinessException {

        Collection<BaseSpecification> constraints = new ArrayList<>();

        constraints.add(new PrestamoBySolicitudSpecification(request.getPayload().getSolicitud()));

        PrestamoRecuperacion prestamo = findOne(constraints);

        if (prestamo != null) {
            return new Message<>(prestamo);
        }
        return new Message<>(prestamo);
    }


}
