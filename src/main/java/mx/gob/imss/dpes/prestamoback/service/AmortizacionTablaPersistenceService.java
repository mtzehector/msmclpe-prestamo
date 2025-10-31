/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.List;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionTabla;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoInsumoTaException;
import mx.gob.imss.dpes.prestamoback.respository.AmortizacionTablaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author gabriel.rios
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class AmortizacionTablaPersistenceService extends BaseCRUDService<AmortizacionTabla, AmortizacionTabla, Long, Long>{
   
    @Autowired
    private AmortizacionTablaRepository repository;

    @Override
    public JpaSpecificationExecutor<AmortizacionTabla> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<AmortizacionTabla, Long> getJpaRepository() {
        return repository;
    }
    
    public Message<AmortizacionTabla> execute(Message<AmortizacionTabla> request) throws BusinessException {
        
        AmortizacionTabla saved = save(request.getPayload());
        return new Message<>(saved);
    }
    
    public AmortizacionTabla saveAmortizacionTabla(AmortizacionTabla amortizacionTabla) throws BusinessException {
        
        AmortizacionTabla saved = save(amortizacionTabla);
        return saved;
    }
    
    public List<AmortizacionTabla> getAmortizacionTablaList(AmortizacionDatosBase amortizacionDatosBase)
        throws BusinessException {

        try {
            List<AmortizacionTabla> list =
                repository.findByAmortizacionDatosBaseOrderByPeriodo(amortizacionDatosBase);

            return list;
        }  catch (Exception e) {
            log.log(Level.SEVERE,
                    "ERROR AmortizacionTablaPersistenceService.getAmortizacionTablaList - [" +
                            amortizacionDatosBase + "]", e);
        }

        throw new PrestamoInsumoTaException(PrestamoInsumoTaException.ERROR_DE_LECTURA_DE_BD);
    }
}
