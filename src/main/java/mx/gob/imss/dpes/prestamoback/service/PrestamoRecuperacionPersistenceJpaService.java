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
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.prestamoback.entity.PrestamoRecuperacion;
import mx.gob.imss.dpes.prestamoback.model.PrestamosEnRecuperacionResponse;
import mx.gob.imss.dpes.prestamoback.respository.PrestamoRecuperacionRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author juan.garfias
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PrestamoRecuperacionPersistenceJpaService {

    @Autowired
    private PrestamoRecuperacionRepository repository;

    public PrestamosEnRecuperacionResponse consultaPrestamosPorSolicitud(Long id) throws BusinessException {

        List<PrestamoRecuperacion> prestamos = repository.findBySolicitud(id);
        PrestamosEnRecuperacionResponse p = new PrestamosEnRecuperacionResponse();
        p.setPrestamosEnRecuperacion(prestamos);
        return p;
    }
    
    public PrestamosEnRecuperacionResponse consultaPrestamosMontoLiquidar(Long solicitud, String numEntidadFinanciera ) throws BusinessException {

        List<PrestamoRecuperacion> prestamos = repository.findBySolicitudAndNumEntidadFinanciera(solicitud, numEntidadFinanciera);
        PrestamosEnRecuperacionResponse p = new PrestamosEnRecuperacionResponse();
        p.setPrestamosEnRecuperacion(prestamos);
        return p;
    }
    
}
