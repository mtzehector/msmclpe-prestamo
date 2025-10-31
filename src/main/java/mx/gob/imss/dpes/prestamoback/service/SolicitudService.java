/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.service.BaseService;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;
import mx.gob.imss.dpes.prestamoback.entity.McltSolicitud;
import mx.gob.imss.dpes.prestamoback.respository.SolicitudRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juanf.barragan
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class SolicitudService  extends BaseCRUDService<McltSolicitud, McltSolicitud, Long, Long>{
    
    @Autowired
    private SolicitudRepository repository;

    @Override
    public JpaSpecificationExecutor<McltSolicitud> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltSolicitud, Long> getJpaRepository() {
        return repository;
    }
    
    public Long getSolicitudbyFolio(String FolioSolicitud) throws BusinessException{
        log.log(Level.INFO,"Inicia el recuperado de la CVE_SOLICITUD del folio {0}", FolioSolicitud);
        McltSolicitud sol = repository.findByNumFolioSolicitud(FolioSolicitud);
        log.log(Level.INFO,"la CVE_SOLICITUD recuperada es {0}", sol.getId());
        return sol.getId();
    }
    
    
}
