/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.assembler;

import java.io.Serializable;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.assembler.BaseAssembler;
import mx.gob.imss.dpes.common.enums.TipoDocumentoEnum;
import mx.gob.imss.dpes.interfaces.documento.model.Documento;
import mx.gob.imss.dpes.interfaces.prestamo.model.ResumenCartaInstruccion;
import mx.gob.imss.dpes.prestamoback.model.ReporteCartaInstruccion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.reporteCompraCartera.model.ReporteCompraCartera;
import mx.gob.imss.dpes.interfaces.reporteCompraCartera.model.ReporteCompraCarteraResponse;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.*;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class ReportePrestamosAutorizadosPorEFAssambler
 extends BaseAssembler{

 

  
  public List<ReportePrestamosAutorizadosPorEFResponse> assemble(List<ReportePrestamosAutorizadosPorEFModel> source) {
  List<ReportePrestamosAutorizadosPorEFResponse> respuesta = new ArrayList<>();
  for (ReportePrestamosAutorizadosPorEFModel rep : source) {
            ReportePrestamosAutorizadosPorEFResponse reporte = new ReportePrestamosAutorizadosPorEFResponse();
            reporte.setCatPromedio(rep.getCAT_PROMEDIO().doubleValue());
            reporte.setDescuentoPromedio(rep.getDESCUENTO_PROMEDIO().doubleValue());
            reporte.setImportePromedio(rep.getIMPORTE_PROMEDIO().doubleValue());
            reporte.setMesesPromedio(rep.getMESES_PROMEDIO().intValue());
            reporte.setDelegacion(rep.getDELEGACION());
            reporte.setSubDelegacion(rep.getSUBDELEGACION());
            reporte.setEntidadFinanciera(rep.getENTIDAD_FINANCIERA());
            reporte.setTotalAutorizados(rep.getTOTAL_AUTORIZADOS().doubleValue());
            respuesta.add(reporte);
        }
    return respuesta;
  }

   public List<ReportePrestamosAutorizadosDetalleEFResponse> assembleDetalle(List<ReportePrestamosAutorizadosDetalleEFModel> source) {
  List<ReportePrestamosAutorizadosDetalleEFResponse> respuesta = new ArrayList<>();
  for (ReportePrestamosAutorizadosDetalleEFModel rep : source) {
            ReportePrestamosAutorizadosDetalleEFResponse reporte = new ReportePrestamosAutorizadosDetalleEFResponse();
            reporte.setCatPromedio(rep.getCAT_PROMEDIO().doubleValue());
            reporte.setDescuentoPromedio(rep.getDESCUENTO_PROMEDIO().doubleValue());
            reporte.setImportePromedio(rep.getIMPORTE_PROMEDIO().doubleValue());
            reporte.setMesesPromedio(rep.getMESES_PROMEDIO().intValue());
            reporte.setEntidadFinanciera(rep.getENTIDAD_FINANCIERA());
            reporte.setTotalAutorizados(rep.getTOTAL_AUTORIZADOS().doubleValue());
            reporte.setSimulacionNuevos(rep.getSIMULACION_NUEVOS().intValue());
            reporte.setSimulacionRenovacion(rep.getSIMULACION_RENOVACION().intValue());
            reporte.setSimulacionCartera(rep.getSIMULACION_CARTERA().intValue());
            reporte.setPromotorNuevos(rep.getPROMOTOR_NUEVOS().intValue());
            reporte.setPromotorRenovacion(rep.getPROMOTOR_RENOVACION().intValue());
            reporte.setPromotorCartera(rep.getPROMOTOR_CARTERA().intValue());
            reporte.setTotalHombres(rep.getTOTAL_HOMBRES().intValue());
            reporte.setTotalMujeres(rep.getTOTAL_MUJERES().intValue());
            reporte.setEdadPromedioHombres(rep.getEDAD_PROMEDIO_HOMBRES().intValue());
            reporte.setEdadPromedioMujeres(rep.getEDAD_PROMEDIO_MUJERES().intValue());

            respuesta.add(reporte);
        }
    return respuesta;
  }
   
   
  public List<ReportePrestamosAutorizadosDetallePorDelegacionResponse> assembleDetalleDelegacion(List<ReportePrestamosAutorizadosDetallePorDelegacionModel> source) {
  List<ReportePrestamosAutorizadosDetallePorDelegacionResponse> respuesta = new ArrayList<>();
  for (ReportePrestamosAutorizadosDetallePorDelegacionModel rep : source) {
            ReportePrestamosAutorizadosDetallePorDelegacionResponse reporte = new ReportePrestamosAutorizadosDetallePorDelegacionResponse();
            reporte.setDelegacion(rep.getDELEGACION());
            reporte.setSubDelegacion(rep.getSUBDELEGACION());
            reporte.setTotalCreditos(rep.getTOTAL_CREDITOS().intValue());
            reporte.setTotalLiquidados(rep.getTOTAL_LIQUIDADOS().intValue());
            reporte.setTotalRecuperacion(rep.getTOTAL_RECUPERACION().intValue());
            reporte.setLiquidadosCartera(rep.getLIQUIDADOS_CARTERA().intValue());
            reporte.setLiquidadosEnPlazo(rep.getLIQUIDADOS_EN_PLAZO().intValue());
            reporte.setLiquidadosRenovados(rep.getLIQUIDADOS_RENOVADOS().intValue());
            reporte.setBajasPorDefuncion(rep.getBAJAS_POR_DEFUNCION().intValue());
            reporte.setBajasPorEF(rep.getBAJAS_POR_EF().intValue());
            reporte.setSubtotalCredito(rep.getSUBTOTAL_CREDITO().intValue());
            
            respuesta.add(reporte);
        }
    return respuesta;
  }
  
  

  public List<ReporteMonitorPensionadosResponse> assembleMonitorPensionados(List<ReporteMonitorPensionadosModel> source) {
  List<ReporteMonitorPensionadosResponse> respuesta = new ArrayList<>();
  for (ReporteMonitorPensionadosModel rep : source) {
            ReporteMonitorPensionadosResponse reporte = new ReporteMonitorPensionadosResponse();
            reporte.setDelegacion(rep.getDELEGACION());
            reporte.setSubDelegacion(rep.getSUBDELEGACION());
            reporte.setTotalSimulaciones(rep.getTOTAL_SIMULACIONES().intValue());
            reporte.setTotalPrestamoPromotor(rep.getTOTAL_PRESTAMO_PROMOTOR().intValue());
            reporte.setSimulacionesVigentes(rep.getSIMULACIONES_VIGENTES().intValue());
            reporte.setSimulacionesCanceladas(rep.getSIMULACIONES_CANCELADAS().intValue());
            reporte.setTiempoPromedioHoras(rep.getTIEMPO_PROMEDIO_HORAS().intValue());
            reporte.setTotalHombres(rep.getTOTAL_HOMBRES().intValue());
            reporte.setTotalMujeres(rep.getTOTAL_MUJERES().intValue());
            reporte.setEdadPromedioHombres(rep.getEDAD_PROMEDIO_HOMBRES().intValue());
            reporte.setEdadPromedioMujeres(rep.getEDAD_PROMEDIO_MUJERES().intValue());
            
            respuesta.add(reporte);
        }
    return respuesta;
  }
    
   
    @Override
    public Serializable toEntity(BaseModel source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable toPKEntity(Serializable source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseModel assemble(Serializable source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable assemblePK(Serializable source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
