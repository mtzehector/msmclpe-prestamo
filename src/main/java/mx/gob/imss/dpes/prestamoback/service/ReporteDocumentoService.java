/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.interfaces.reportesMclp.model.ReporteDocumento;
import mx.gob.imss.dpes.interfaces.reportesMclp.model.ReporteRq;
import mx.gob.imss.dpes.prestamoback.entity.McltReporteDocumento;
import mx.gob.imss.dpes.prestamoback.respository.DocumentoRepository;
import mx.gob.imss.dpes.prestamoback.respository.ReporteDocumentoRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author juan.garfias
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ReporteDocumentoService {

    private static final Logger LOG = Logger.getLogger(ReporteDocumentoService.class.getName());

    @Autowired
    private ReporteDocumentoRepository reporteDocRepository;

    @Autowired
    private DocumentoRepository docRepository;

    public List<McltReporteDocumento> getDocumentosByCveReporte(Long cveReporte) {
        LOG.log(Level.ALL, "REPOSITORY cveReporte: {0}", cveReporte);
        return reporteDocRepository.findAllByCveReporte(cveReporte);
    }

    public List<McltReporteDocumento> addDocumentosByCveReporte(ReporteRq reporte) {

        List<McltReporteDocumento> ld = new ArrayList<>();

        for (ReporteDocumento rd : reporte.getReporteDocumentos()) {
            McltReporteDocumento mrd = new McltReporteDocumento();

            mrd.setCveReporte(reporte.getReporte().getId());
            mrd.setDocumento(docRepository.findOne(rd.getDocumento().getId()));
            mrd.setAltaRegistro(new Date());
            ld.add(reporteDocRepository.save(mrd));
        }
        return ld;
    }
}
