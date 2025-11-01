/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author antonio
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(mx.gob.imss.dpes.common.exception.AlternateFlowMapper.class);
        resources.add(mx.gob.imss.dpes.common.exception.BusinessMapper.class);
        resources.add(mx.gob.imss.dpes.common.rule.MontoTotalRule.class);
        resources.add(mx.gob.imss.dpes.common.rule.PagoMensualRule.class);
        resources.add(mx.gob.imss.dpes.prestamoback.assembler.ReporteCompraCarteraEFAssembler.class);
        resources.add(mx.gob.imss.dpes.prestamoback.assembler.ReportePrestamosAutorizadosPorEFAssambler.class);
        resources.add(mx.gob.imss.dpes.prestamoback.assembler.ResumenCartaInstruccionAssembler.class);
        resources.add(mx.gob.imss.dpes.prestamoback.assembler.ResumenCartaReinstalacionAssembler.class);
        resources.add(mx.gob.imss.dpes.prestamoback.assembler.ResumenSimulacionAssembler.class);
        resources.add(mx.gob.imss.dpes.prestamoback.assembler.PrestamoAssembler.class);
        resources.add(mx.gob.imss.dpes.prestamoback.assembler.PrestamoInsumoTaAssembler.class);
        resources.add(mx.gob.imss.dpes.prestamoback.endpoint.AmortizacionEndPoint.class);
        resources.add(mx.gob.imss.dpes.prestamoback.endpoint.ConsultaPrestamosVigentesEndPoint.class);
        resources.add(mx.gob.imss.dpes.prestamoback.endpoint.PrestamoEndPoint.class);
        resources.add(mx.gob.imss.dpes.prestamoback.endpoint.ReporteEndPoint.class);
        resources.add(mx.gob.imss.dpes.prestamoback.endpoint.PrestamoInsumoTaEndPoint.class);
        resources.add(mx.gob.imss.dpes.prestamoback.rules.CadenaOriginalCartaInstruccionRule.class);
        resources.add(mx.gob.imss.dpes.prestamoback.rules.CadenaOriginalRule.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.AmortizacionDatosBasePersistenceService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.AmortizacionTablaPersistenceService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CalcularTblAmortizacionDupService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CalcularTblAmortizacionService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CorreoService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ConsultaDelegacionService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CreateEventoService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CreateReporteCartaInstruccionService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CreateReporteCartaReinstalacionService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CreateReporteSimulacionService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CreateSelloElectronicoCartaInstruccionService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.CreateSelloElectronicoSimulacionService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.PrestamoPersistenceService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.PrestamoRecuperacionPersistenceJpaService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.PrestamoRecuperacionPersistenceService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.PrestamosVigentesPersistenceService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReadOfertaService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReadPensionadoService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReadPersonaBdtuService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReadPersonaService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReadPrestamoRecuperacionService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReadPrestamoService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReadPromotorService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReadSolicitudService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReporteDocumentoService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ReporteService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.SolicitudService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.TblAmortizacionDescuentoMensualService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.TblAmortizacionMontoSolicitadoService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ConsultarDocumentosService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.ConsultarOfertaService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.PrestamoService.class);
        resources.add(mx.gob.imss.dpes.prestamoback.service.PrestamoInsumoTaService.class);
    }

}
