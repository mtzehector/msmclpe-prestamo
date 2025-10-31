/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.List;
import java.util.logging.Logger;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.prestamoback.entity.MclcCorreo;
import mx.gob.imss.dpes.prestamoback.respository.CorreoRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author juan.garfias
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class CorreoService {

    private static final Logger LOG = Logger.getLogger(CorreoService.class.getName());

    @Autowired
    private CorreoRepository correoRepository;

    public List<MclcCorreo> getReporteByPeriodoNominaAndTipoReporte() {
        return correoRepository.findByCorreoActivo(1L);
    }

}
