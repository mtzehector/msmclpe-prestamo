/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoInsumoTaException;
import mx.gob.imss.dpes.prestamoback.respository.AmortizacionRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.logging.Level;

/**
 *
 * @author edgar.arenas
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class AmortizacionDatosBasePersistenceService extends BaseCRUDService<AmortizacionDatosBase, AmortizacionDatosBase, Long, Long>{
   
    @Autowired
    private AmortizacionRepository repository;

    @Override
    public JpaSpecificationExecutor<AmortizacionDatosBase> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<AmortizacionDatosBase, Long> getJpaRepository() {
        return repository;
    }
    
    public Message<AmortizacionDatosBase> execute(Message<AmortizacionDatosBase> request) throws BusinessException {
        AmortizacionDatosBase saved = null;
        if(request.getPayload().getTest()!=null && request.getPayload().getTest()>0){
            return request;
        }
        saved = save(request.getPayload());
        return new Message<>(saved);
    }
    
    
    
    public AmortizacionDatosBase getAmortizacionDatosBaseByCveId(Long cveId) throws BusinessException {
        try {
            AmortizacionDatosBase saved = repository.findByCveSolicitud(cveId);
            return saved;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR AmortizacionDatosBasePersistenceService.getAmortizacionDatosBaseByCveId = [" +
                    cveId + "]", e);
        }
        throw new PrestamoInsumoTaException(PrestamoInsumoTaException.ERROR_DE_LECTURA_DE_BD);
    }
    
    public AmortizacionDatosBase getAmortizacionDatosBaseByFolioSipre(String folioSipre) throws BusinessException {
        
        AmortizacionDatosBase saved = repository.findByFolioSipre(folioSipre);
        return saved;
    }
    
}
