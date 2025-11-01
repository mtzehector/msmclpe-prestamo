/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.interfaces.reporteCompraCartera.model.ReporteCompraCartera;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReporteMonitorPensionadosModel;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosDetalleEFModel;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosDetallePorDelegacionModel;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosPorEFModel;
import mx.gob.imss.dpes.interfaces.reportesMclp.model.EstadoReporte;
import mx.gob.imss.dpes.interfaces.reportesMclp.model.Reporte;
import mx.gob.imss.dpes.interfaces.reportesMclp.model.ReporteRq;
import mx.gob.imss.dpes.prestamoback.entity.MclcEntidadFinanciera;
import mx.gob.imss.dpes.prestamoback.entity.MclcEstadoReporte;
import mx.gob.imss.dpes.prestamoback.entity.MclcSubTipoReporte;
import mx.gob.imss.dpes.prestamoback.entity.MclcTipoReporte;
import mx.gob.imss.dpes.prestamoback.entity.McltReporte;
import mx.gob.imss.dpes.prestamoback.respository.EntidadFinancieraRepository;
import mx.gob.imss.dpes.prestamoback.respository.EstadoReporteRepository;
import mx.gob.imss.dpes.prestamoback.respository.ReporteCompraCarteraRepository;
import mx.gob.imss.dpes.prestamoback.respository.ReportePrestamosAutorizadosPorEFRepository;
import mx.gob.imss.dpes.prestamoback.respository.ReporteRepository;
import mx.gob.imss.dpes.prestamoback.respository.SubTipoReporteRepository;
import mx.gob.imss.dpes.prestamoback.respository.TipoReporteRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author juan.garfias
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ReporteService {

    private static final Logger LOG = Logger.getLogger(ReporteService.class.getName());

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private EstadoReporteRepository estadoReporteRepository;

    @Autowired
    private ReporteCompraCarteraRepository reporteCompraCarteraRepository;
      @Autowired
    private ReportePrestamosAutorizadosPorEFRepository reportePrestamosAutorizadosPorEFRepository;

    @Autowired
    private TipoReporteRepository tipoReporteRepository;

    @Autowired
    private EntidadFinancieraRepository efRepository;

    @Autowired
    private SubTipoReporteRepository subTipoReporteRepository;

    /*
    public McltReporte getReporteByPeriodoNominaAndTipoReporte(String periodoNomina,
            Long tipoReporte) {
        LOG.log(Level.INFO, "REPOSITORY PERIODO NOMINA: {0}", periodoNomina);
        return reporteRepository.findByPeriodoNominaAndTipoReporte(
                periodoNomina,
                tipoReporteRepository.findOne(tipoReporte)
        );
    }
     */
    public McltReporte addReporte(ReporteRq reporte) {
        LOG.log(Level.INFO, "addReporte(McltReporte ReporteRq reporte): {0}", reporte);
        LOG.log(Level.INFO, "addReporte(McltReporte ReporteRq reporte): {0}", reporte.getReporte().getCveEntidadFinanciera());

        McltReporte r = new McltReporte();

        r.setPeriodoNomina(reporte.getReporte().getPeriodoNomina());
        r.setEstadoReporte(estadoReporteRepository.findOne(reporte.getReporte().getEstadoReporte().getId()));
        r.setTipoReporte(tipoReporteRepository.findOne(reporte.getReporte().getTipoReporte().getId()));
        r.setSubTipoReporte(subTipoReporteRepository.findOne(reporte.getReporte().getSubTipoReporte().getId()));

        if (reporte.getReporte().getCveEntidadFinanciera() != null) {
            try {
                r.setEntidadFinanciera(efRepository.findOne(reporte.getReporte().getCveEntidadFinanciera()));
            } catch (Exception e) {
                LOG.log(Level.WARNING, "Exception efRepository.findOne(: {0}", e);
            }
        }
        r.setAltaRegistro(new Date());

        LOG.log(Level.INFO, "addReporte(McltReporte reporte): {0}", r);
        try {
            return reporteRepository.save(r);
        } catch (Exception e) {

            LOG.log(Level.WARNING, "Exception: {0}", e);

            return null;
        }

    }

    public List<ReporteCompraCartera> getReporteByPeriodoNominaAndCveEntidadFinanciera(String periodoNomina,
            Long cveEntidadFinanciera) {
        LOG.log(Level.ALL, "REPOSITORY PERIODO NOMINA: {0}", periodoNomina);
        return reporteCompraCarteraRepository.getReporteCompraCartera(periodoNomina, cveEntidadFinanciera);

    }
      
      
      
    public List<ReportePrestamosAutorizadosPorEFModel> getReportePrestamosAutorizadosPorEF(String fechaInicio,
                                                       String fechaFin) {
        return reportePrestamosAutorizadosPorEFRepository.getReportePrestamosAutorizadosPorEF(fechaInicio,fechaFin);
        
    }
    
    public List<ReportePrestamosAutorizadosDetalleEFModel> getReportePrestamosAutorizadosDetalleEF(String fechaInicio,
                                                       String fechaFin) {
        return reportePrestamosAutorizadosPorEFRepository.getReportePrestamosAutorizadosDetalleEF(fechaInicio,fechaFin);
        
    }

     public List<ReportePrestamosAutorizadosDetallePorDelegacionModel> getReportePrestamosAutorizadosDetalleDelegacion(String fechaInicio,
                                                       String fechaFin) {
        return reportePrestamosAutorizadosPorEFRepository.getReportePrestamosAutorizadosDetalleDelegacion(fechaInicio,fechaFin);
        
    }
    
     
     
     public List<ReporteMonitorPensionadosModel> getReporteMonitorPensionados(String fechaInicio,
                                                       String fechaFin) {
        return reportePrestamosAutorizadosPorEFRepository.getMonitorPensionados(fechaInicio,fechaFin);
        
    }
    
    public McltReporte updateReporte(McltReporte reporte, EstadoReporte estadoReporte) {

        reporte.setEstadoReporte(
                estadoReporteRepository.findOne(estadoReporte.getId())
        );
        return reporteRepository.save(reporte);

    }

    public McltReporte getReporteByPeriodoNominaAndTipoReporteAndEntidadFinanciera(String periodoNomina,
            Long tipoReporte, Long entidadFinanciera) {
        LOG.log(Level.INFO, "REPOSITORY PERIODO NOMINA: {0}", periodoNomina);
        if (entidadFinanciera == null) {
            return reporteRepository.findByPeriodoNominaAndTipoReporteAndEntidadFinancieraIsNull(
                    periodoNomina,
                    tipoReporteRepository.findOne(tipoReporte)
            );
        } else {
            return reporteRepository.findByPeriodoNominaAndTipoReporteAndEntidadFinanciera(
                    periodoNomina,
                    tipoReporteRepository.findOne(tipoReporte),
                    efRepository.findOne(entidadFinanciera)
            );
        }

    }
}
