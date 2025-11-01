/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.endpoint;

import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.interfaces.reporteCompraCartera.model.ReporteCompraCarteraRequest;
import mx.gob.imss.dpes.interfaces.reporteCompraCartera.model.ReporteCompraCarteraResponse;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReporteMonitorPensionadosResponse;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosDetalleEFResponse;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosDetallePorDelegacionResponse;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosPorEF;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosPorEFResponse;
import mx.gob.imss.dpes.interfaces.reportesMclp.model.Reporte;
import mx.gob.imss.dpes.interfaces.reportesMclp.model.ReporteRq;
import mx.gob.imss.dpes.prestamoback.assembler.ReporteCompraCarteraEFAssembler;
import mx.gob.imss.dpes.prestamoback.assembler.ReportePrestamosAutorizadosPorEFAssambler;
import mx.gob.imss.dpes.prestamoback.entity.McltReporte;
import mx.gob.imss.dpes.prestamoback.entity.McltReporteDocumento;
import mx.gob.imss.dpes.prestamoback.model.ReporteRs;
import mx.gob.imss.dpes.prestamoback.service.CorreoService;
import mx.gob.imss.dpes.prestamoback.service.ReporteDocumentoService;
import mx.gob.imss.dpes.prestamoback.service.ReporteService;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;

/**
 *
 * @author juan.garfias
 */
@ApplicationScoped
@Path("/reporte")
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ReporteEndPoint extends BaseGUIEndPoint<McltReporte, McltReporte, McltReporte> {

    @Inject
    private ReporteService reporteService;

    @Inject
    private ReporteCompraCarteraEFAssembler assembler;
    
    @Inject 
    private ReportePrestamosAutorizadosPorEFAssambler assamblerPA;

    @Inject
    private CorreoService correoService;

    @Inject
    private ReporteDocumentoService reporteDocService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response load(ReporteRq reporte) {
        log.log(Level.INFO, "Inicia insert de Reporte: {0}", reporte);

        McltReporte rep = null;
        if (reporte.getReporte().getCveEntidadFinanciera() == null) {
            rep = reporteService.getReporteByPeriodoNominaAndTipoReporteAndEntidadFinanciera(
                    reporte.getReporte().getPeriodoNomina(),
                    reporte.getReporte().getTipoReporte().getId(),
                    null);

        } else {
            rep = reporteService.getReporteByPeriodoNominaAndTipoReporteAndEntidadFinanciera(
                    reporte.getReporte().getPeriodoNomina(),
                    reporte.getReporte().getTipoReporte().getId(),
                    reporte.getReporte().getCveEntidadFinanciera());
        }

        if (rep != null) {
            return Response.notModified().build();
        }

        McltReporte r = new McltReporte();

        try {
            r = reporteService.addReporte(reporte);
        } catch (Exception e) {
            log.log(Level.INFO, "Error integridad SQL: {0}", e.getMessage());
            return Response.notModified().build();
        }

        if (r == null) {
            return Response.notModified().build();
        }

        reporte.getReporte().setId(r.getId());
        List<McltReporteDocumento> mrd = reporteDocService.addDocumentosByCveReporte(reporte);

        ReporteRs response = new ReporteRs(r, mrd);

        return Response.ok(response).build();

    }

    @GET
    @Path("/{periodoNomina}/{tipoReporte}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReporteByPeriodoNominaAndTipoReporteAndSubTipoReporte(
            @PathParam("periodoNomina") String periodoNomina,
            @PathParam("tipoReporte") Long tipoReporte) {
        log.log(Level.INFO, "Consulta Por periodo Nomina: {0}", periodoNomina);

        McltReporte rep = reporteService.getReporteByPeriodoNominaAndTipoReporteAndEntidadFinanciera(
                periodoNomina,
                tipoReporte,
                null);

        if (rep == null) {
            return Response.noContent().build();
        }

        ReporteRs rs = new ReporteRs(rep, reporteDocService.getDocumentosByCveReporte(rep.getId()));

        return Response.ok(rs).build();
    }

    /*  @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delegaciones")
    public Response findDelegaciones(EntidadFinanciera entidadFinanciera)  throws BusinessException,TransactionRequiredException {
        log.log(Level.INFO, ">>> entidadFinancieraBack CondicionEntidadFederativaEndPoint findDelegaciones id={0}", id);
        LinkedList<Delegacion> delegacionesLista = service.findDelegaciones(entidadFinanciera.getId());
        log.log(Level.INFO, ">>> entidadFinancieraBack CondicionEntidadFederativaEndPoint delegacionesLista={0}", delegacionesLista);
        
        return toResponse(new Message<>(delegacionesLista));
        
    }*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/compraCarteraEF")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReporteCompraCarteraEF(ReporteCompraCarteraRequest request) {
        log.log(Level.INFO, "Consulta Por periodo Nomina: {0}", request);

        List<ReporteCompraCarteraResponse> rep = assembler.assemble(reporteService.getReporteByPeriodoNominaAndCveEntidadFinanciera(
                request.getPeriodoNomina(),
                request.getCveEntidadFinanciera()));

        if (rep == null) {
            return Response.noContent().build();
        }

        return Response.ok(rep).build();
    }

    
       @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/prestamosAutorizadosDetalleEF")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReportePrestamosAutorizadosDetallePorEF(ReportePrestamosAutorizadosPorEF request) {
        log.log(Level.INFO, "Consulta Por periodo Nomina: {0}", request);

        List<ReportePrestamosAutorizadosDetalleEFResponse> rep = assamblerPA.assembleDetalle(reporteService.getReportePrestamosAutorizadosDetalleEF(
                request.getFechaInicio(),
                request.getFechaFin()));

        if (rep == null) {
            return Response.noContent().build();
        }

        return Response.ok(rep).build();
    }
  
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/prestamosAutorizadosDetalleDelegacion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReportePrestamosAutorizadosDetallePorDelegacion(ReportePrestamosAutorizadosPorEF request) {
        log.log(Level.INFO, "Consulta Por periodo Nomina: {0}", request);

        List<ReportePrestamosAutorizadosDetallePorDelegacionResponse> rep = assamblerPA.assembleDetalleDelegacion(reporteService.getReportePrestamosAutorizadosDetalleDelegacion(
                request.getFechaInicio(),
                request.getFechaFin()));

        if (rep == null) {
            return Response.noContent().build();
        }

        return Response.ok(rep).build();
    }
    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/monitorPensionados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReporteMonitorPensioandos(ReportePrestamosAutorizadosPorEF request) {
        log.log(Level.INFO, "Consulta Por periodo Nomina: {0}", request);

        List<ReporteMonitorPensionadosResponse> rep = assamblerPA.assembleMonitorPensionados(reporteService.getReporteMonitorPensionados(
                request.getFechaInicio(),
                request.getFechaFin()));

        if (rep == null) {
            return Response.noContent().build();
        }

        return Response.ok(rep).build();
    }
    
        

    
      @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/prestamosAutorizadosEF")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReportePrestamosAutorizadosPorEF(ReportePrestamosAutorizadosPorEF request) {
        log.log(Level.INFO, "Consulta Por periodo Nomina: {0}", request);

        List<ReportePrestamosAutorizadosPorEFResponse> rep = assamblerPA.assemble(reporteService.getReportePrestamosAutorizadosPorEF(
                request.getFechaInicio(),
                request.getFechaFin()));

        if (rep == null) {
            return Response.noContent().build();
        }

        return Response.ok(rep).build();
    }

  
    
    
    
    @PUT
    @Path("/updateEstado")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Reporte reporte) {
        log.log(Level.INFO, "Inicia Update de Reporte: {0}", reporte);

        McltReporte rep = reporteService.getReporteByPeriodoNominaAndTipoReporteAndEntidadFinanciera(
                reporte.getPeriodoNomina(),
                reporte.getTipoReporte().getId(),
                null);

        if (rep == null) {
            return Response.noContent().build();
        }

        return Response.ok(
                reporteService.updateReporte(rep, reporte.getEstadoReporte())
        ).build();

    }

    @GET
    @Path("/correos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorreos() {

        ReporteRs rs = new ReporteRs();

        rs.setCorreos(correoService.getReporteByPeriodoNominaAndTipoReporte());

        return Response.ok(rs).build();
    }

    @GET
    @Path("/{periodoNomina}/{tipoReporte}/{cveEF}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReporteByPeriodoNominaAndTipoReporteAndEF(
            @PathParam("periodoNomina") String periodoNomina,
            @PathParam("tipoReporte") Long tipoReporte,
            @PathParam("cveEF") Long cveEntidadFinanciera) {
        log.log(Level.INFO, "getReporteByPeriodoNominaAndTipoReporteAndEF: {0}", periodoNomina);
        log.log(Level.INFO, "getReporteByPeriodoNominaAndTipoReporteAndEF: {0}", tipoReporte);
        log.log(Level.INFO, "getReporteByPeriodoNominaAndTipoReporteAndEF: {0}", cveEntidadFinanciera);

        McltReporte rep = reporteService.getReporteByPeriodoNominaAndTipoReporteAndEntidadFinanciera(
                periodoNomina,
                tipoReporte,
                cveEntidadFinanciera);

        if (rep == null) {
            return Response.noContent().build();
        }

        ReporteRs rs = new ReporteRs(rep, reporteDocService.getDocumentosByCveReporte(rep.getId()));
        log.log(Level.INFO, "getReporteByPeriodoNominaAndTipoReporteAndEF ReporteRs: {0}", rs);

        return Response.ok(rs).build();
    }
}
