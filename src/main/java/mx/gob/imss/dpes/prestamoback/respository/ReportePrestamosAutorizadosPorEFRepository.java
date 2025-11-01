/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReporteMonitorPensionadosModel;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosDetalleEFModel;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosDetallePorDelegacionModel;
import mx.gob.imss.dpes.interfaces.reportesEstadisticos.model.ReportePrestamosAutorizadosPorEFModel;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

/**
 *
 * @author ernesto.palacios
 */
@Component
public class ReportePrestamosAutorizadosPorEFRepository {

    
    protected final transient Logger log = Logger.getLogger(getClass().getName());
    @PersistenceContext
    private EntityManager entityManager;
    
    public ReportePrestamosAutorizadosPorEFRepository() {
    }
    
    public List<ReportePrestamosAutorizadosPorEFModel> getReportePrestamosAutorizadosPorEF(String fechaInicio,
            String fechaFin) {
        log.info("fechaInicio"+fechaInicio+" fechaFin"+fechaFin);
        List<ReportePrestamosAutorizadosPorEFModel> reporte = entityManager.createNativeQuery( 
"select "+
"md.cve_delegacion ||' - ' || md.des_delegacion as DELEGACION, "+
"ms.cve_subdelegacion ||' - '  as SUBDELEGACION, "+
"mef.cve_entidad_financiera||' - ' || mef.nom_razon_social as ENTIDAD_FINANCIERA, "+
"mef.cve_entidad_financiera as  PIVOTE, "+
"count(ms.num_folio_solicitud) as TOTAL_AUTORIZADOS, "+
"TRUNC(AVG(mp.IMP_TOTAL_PAGAR),2) as IMPORTE_PROMEDIO, "+
"TRUNC(AVG(POR_CAT),2) as CAT_PROMEDIO, "+
"TRUNC(AVG(mp.IMP_DESCUENTO_NOMINA),2) as DESCUENTO_PROMEDIO, "+
"TRUNC(AVG(NUM_MESES)) as MESES_PROMEDIO "+
"from "+
"mclc_entidad_financiera mef  "+
"inner join mclt_solicitud ms on ms.cve_entidad_financiera=mef.cve_entidad_financiera "+
"inner join mclt_prestamo mp on ms.cve_solicitud=mp.cve_solicitud "+
"inner join mclc_delegacion md on md.cve_delegacion = ms.cve_delegacion "+
"inner join mclc_condicion_oferta mco on mp.cve_condicion_oferta=mco.cve_condicion_oferta "+
"inner join mclc_plazo on mclc_plazo.cve_plazo=mco.cve_plazo "+
"WHERE  "+
"ms.cve_estado_solicitud=3 "+
"and to_date(ms.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy') "+
"group by ms.cve_subdelegacion, ms.cve_subdelegacion, mef.cve_entidad_financiera, md.cve_delegacion ||' - ' || md.des_delegacion, ms.cve_subdelegacion ||' - ',  "+
"mef.cve_entidad_financiera||' - ' || mef.nom_razon_social,mef.cve_entidad_financiera "+
"order by 3 ")  .setParameter(1,fechaInicio )
                .setParameter(2,fechaFin )
                .unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(ReportePrestamosAutorizadosPorEFModel.class)).getResultList();
        log.info("reporte {1}" +reporte.toString());

        return reporte;
    }
    
    
    
    
    
      public List<ReportePrestamosAutorizadosDetalleEFModel> getReportePrestamosAutorizadosDetalleEF(String fechaInicio,
            String fechaFin) {
        log.info("fechaInicio"+fechaInicio+" fechaFin"+fechaFin);
        List<ReportePrestamosAutorizadosDetalleEFModel> reporte = entityManager.createNativeQuery( 
"select "+
"mef.cve_entidad_financiera||' - ' || mef.nom_razon_social as ENTIDAD_FINANCIERA, "+
"mef.cve_entidad_financiera as  PIVOTE, "+
"count(ms.num_folio_solicitud) as TOTAL_AUTORIZADOS, "+
"TRUNC(AVG(mp.IMP_TOTAL_PAGAR),2) as IMPORTE_PROMEDIO, "+
"TRUNC(AVG(POR_CAT),2) as CAT_PROMEDIO, "+
"TRUNC(AVG(mp.IMP_DESCUENTO_NOMINA),2) as DESCUENTO_PROMEDIO, "+
"TRUNC(AVG(NUM_MESES)) as MESES_PROMEDIO, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud) where  cve_tipo_credito=1 AND cve_estado_solicitud=3 and mclt_solicitud.cve_origen_solicitud=1 and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy'))as SIMULACION_NUEVOS, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud) where  cve_tipo_credito=2 AND cve_estado_solicitud=3 and mclt_solicitud.cve_origen_solicitud=1 and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy'))as SIMULACION_RENOVACION, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud) where  cve_tipo_credito=3 AND cve_estado_solicitud=3 and mclt_solicitud.cve_origen_solicitud=1 and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy'))as SIMULACION_CARTERA, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud) where  cve_tipo_credito=1 AND cve_estado_solicitud=3 and mclt_solicitud.cve_origen_solicitud=4 and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy'))as PROMOTOR_NUEVOS, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud) where  cve_tipo_credito=2 AND cve_estado_solicitud=3 and mclt_solicitud.cve_origen_solicitud=4 and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy'))as PROMOTOR_RENOVACION, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud) where  cve_tipo_credito=3 AND cve_estado_solicitud=3 and mclt_solicitud.cve_origen_solicitud=4 and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy'))as PROMOTOR_CARTERA, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_persona using (cve_curp) where cve_sexo=1  and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera AND cve_estado_solicitud=3 and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy')) as TOTAL_HOMBRES, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_persona using (cve_curp) where cve_sexo=2  and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera AND cve_estado_solicitud=3 and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy')) as TOTAL_MUJERES, "+
"(SELECT COALESCE(TRUNC(AVG(TRUNC( ( TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMMDD')) -  TO_NUMBER(TO_CHAR(fec_nacimiento,'YYYYMMDD') ) ) / 10000))),0) from mclt_solicitud inner join mclt_persona using (cve_curp) where cve_sexo=1  and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera AND cve_estado_solicitud=3 and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy')) as EDAD_PROMEDIO_HOMBRES, "+
"(SELECT COALESCE(TRUNC(AVG(TRUNC( ( TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMMDD')) -  TO_NUMBER(TO_CHAR(fec_nacimiento,'YYYYMMDD') ) ) / 10000))),0) from mclt_solicitud inner join mclt_persona using (cve_curp) where cve_sexo=2  and mef.cve_entidad_financiera = mclt_solicitud.cve_entidad_financiera AND cve_estado_solicitud=3 and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy')) as EDAD_PROMEDIO_MUJERES "+
"from "+
"mclc_entidad_financiera mef  "+
"inner join mclt_solicitud ms on ms.cve_entidad_financiera=mef.cve_entidad_financiera "+
"inner join mclt_prestamo mp on ms.cve_solicitud=mp.cve_solicitud "+
"inner join mclc_delegacion md on md.cve_delegacion = ms.cve_delegacion "+
"inner join mclc_condicion_oferta mco on mp.cve_condicion_oferta=mco.cve_condicion_oferta "+
"inner join mclc_plazo on mclc_plazo.cve_plazo=mco.cve_plazo "+
"WHERE  "+
"ms.cve_estado_solicitud=3 "+
"and to_date(ms.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy') "+
"group by mef.cve_entidad_financiera,   "+
"mef.cve_entidad_financiera||' - ' || mef.nom_razon_social,mef.cve_entidad_financiera "+
"order by 1 "
)  .setParameter(1,fechaInicio )
                .setParameter(2,fechaFin )
                .unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(ReportePrestamosAutorizadosDetalleEFModel.class)).getResultList();
        log.info("reporte {1}" +reporte.toString());

        return reporte;
    }
    
      
      
      
 public List<ReportePrestamosAutorizadosDetallePorDelegacionModel> getReportePrestamosAutorizadosDetalleDelegacion(String fechaInicio,
            String fechaFin) {
        log.info("fechaInicio"+fechaInicio+" fechaFin"+fechaFin);
        List<ReportePrestamosAutorizadosDetallePorDelegacionModel> reporte = entityManager.createNativeQuery( 
"select "+
"md.cve_delegacion ||' - ' || md.des_delegacion as DELEGACION, "+
"ms.cve_subdelegacion ||' - '  as SUBDELEGACION, "+
"mef.cve_entidad_financiera as  PIVOTE, "+
"md.cve_delegacion as PIVOTE2, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_delegacion =md.cve_delegacion )as TOTAL_CREDITOS, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud=8 and mclt_solicitud.cve_delegacion =md.cve_delegacion )as TOTAL_RECUPERACION, "+
"COALESCE(0+(select count(mclt_prestamo_recuperacion.cve_prestamo) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud)  inner join mclt_prestamo_recuperacion using(cve_solicitud)where cve_Estado_solicitud=3 and cve_tipo_credito in(6,2,3)  and   mclt_solicitud.cve_delegacion =md.cve_delegacion  and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) group by mclt_prestamo_recuperacion.cve_prestamo),0) as TOTAL_LIQUIDADOS, "+
"COALESCE(0+(select count(mclt_prestamo_recuperacion.cve_prestamo) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud)  inner join mclt_prestamo_recuperacion using(cve_solicitud)where cve_Estado_solicitud=3 and cve_tipo_credito = 3  and   mclt_solicitud.cve_delegacion =md.cve_delegacion  and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) group by mclt_prestamo_recuperacion.cve_prestamo),0) as LIQUIDADOS_CARTERA, "+
"COALESCE(0+(select count(mclt_prestamo_recuperacion.cve_prestamo) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud)  inner join mclt_prestamo_recuperacion using(cve_solicitud)where cve_Estado_solicitud=3 and cve_tipo_credito = 2  and   mclt_solicitud.cve_delegacion =md.cve_delegacion  and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) group by mclt_prestamo_recuperacion.cve_prestamo),0) as LIQUIDADOS_RENOVADOS, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud=11 and mclt_solicitud.cve_delegacion =md.cve_delegacion )as LIQUIDADOS_EN_PLAZO, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud IN (9,10,13) and mclt_solicitud.cve_delegacion =md.cve_delegacion )as BAJAS_POR_EF, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud =12 and mclt_solicitud.cve_delegacion =md.cve_delegacion )as BAJAS_POR_DEFUNCION, "+
"COALESCE((select count(mclt_prestamo_recuperacion.cve_prestamo) from mclt_solicitud inner join mclt_prestamo using(cve_solicitud)  inner join mclt_prestamo_recuperacion using(cve_solicitud)where cve_Estado_solicitud=3 and cve_tipo_credito in(6,2,3)  and   mclt_solicitud.cve_delegacion =md.cve_delegacion  and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) group by mclt_prestamo_recuperacion.cve_prestamo)+(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud IN(12,8) and mclt_solicitud.cve_delegacion =md.cve_delegacion ),0)as SUBTOTAL_CREDITO "+
"from mclc_entidad_financiera mef  "+
"inner join mclt_solicitud ms on ms.cve_entidad_financiera=mef.cve_entidad_financiera "+
"inner join mclt_prestamo mp on ms.cve_solicitud=mp.cve_solicitud "+
"inner join mclc_delegacion md on md.cve_delegacion = ms.cve_delegacion "+
"WHERE ms.cve_estado_solicitud not in (5,6,20,4,1,2,15,14) "+
"AND to_date(ms.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy') "+
"and ms.cve_delegacion=md.cve_delegacion "+
"group by ms.cve_subdelegacion, md.cve_delegacion,ms.cve_subdelegacion,mef.cve_entidad_financiera,  md.cve_delegacion ||' - ' || md.des_delegacion, ms.cve_subdelegacion ||' - ' "+
"order by 3  "
)  .setParameter(1,fechaInicio )
                .setParameter(2,fechaFin )
                .unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(ReportePrestamosAutorizadosDetallePorDelegacionModel.class)).getResultList();
        log.info("reporte {1}" +reporte.toString());

        return reporte;
    }
    
 
 
 
      
 public List<ReporteMonitorPensionadosModel> getMonitorPensionados(String fechaInicio,
            String fechaFin) {
        log.info("fechaInicio"+fechaInicio+" fechaFin"+fechaFin);
        List<ReporteMonitorPensionadosModel> reporte = entityManager.createNativeQuery( 
"select "+
"md.cve_delegacion ||' - ' || md.des_delegacion as DELEGACION, "+
"ms.cve_subdelegacion ||' - '  as SUBDELEGACION, "+
"mef.cve_entidad_financiera as  PIVOTE, "+
"md.cve_delegacion as PIVOTE2, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud<>6 and mclt_solicitud.cve_origen_solicitud=1 and mclt_solicitud.cve_delegacion =md.cve_delegacion)as TOTAL_SIMULACIONES, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud<>6 and mclt_solicitud.cve_origen_solicitud=4 and mclt_solicitud.cve_delegacion =md.cve_delegacion)as TOTAL_PRESTAMO_PROMOTOR, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud<>6 and mclt_solicitud.cve_origen_solicitud=1 and mclt_solicitud.cve_delegacion =md.cve_delegacion AND mclt_solicItud.fec_vigencia_folio<= SYSDATE)as SIMULACIONES_VIGENTES, "+
"(select count(num_folio_solicitud) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud=6 and mclt_solicitud.cve_origen_solicitud=1 and mclt_solicitud.cve_delegacion =md.cve_delegacion)as SIMULACIONES_CANCELADAS, "+
"(select COALESCE(TRUNC(AVG((fec_registro_actualizado-fec_registro_alta)*24)),0) from mclt_solicitud  where to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy' ) and TO_DATE(?2,'dd-mm-yy' ) and mclt_solicitud.cve_estado_solicitud<>6 and mclt_solicitud.cve_origen_solicitud=1 and mclt_solicitud.cve_delegacion =md.cve_delegacion)as TIEMPO_PROMEDIO_HORAS, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_persona using (cve_curp) where cve_sexo=1  and mclt_solicitud.cve_delegacion =md.cve_delegacion AND cve_estado_solicitud<>6 and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy') AND mclt_solicitud.cve_origen_solicitud=1 ) as TOTAL_HOMBRES, "+
"(SELECT count(*) from mclt_solicitud inner join mclt_persona using (cve_curp) where cve_sexo=2  and mclt_solicitud.cve_delegacion =md.cve_delegacion AND cve_estado_solicitud<>6 and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy') AND mclt_solicitud.cve_origen_solicitud=1) as TOTAL_MUJERES, "+
"(SELECT COALESCE(TRUNC(AVG(TRUNC( ( TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMMDD')) -  TO_NUMBER(TO_CHAR(fec_nacimiento,'YYYYMMDD') ) ) / 10000))),0) from mclt_solicitud inner join mclt_persona using (cve_curp) where cve_sexo=1  and mclt_solicitud.cve_delegacion =md.cve_delegacion AND cve_estado_solicitud<>6 AND mclt_solicitud.cve_origen_solicitud=1 and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy')) as EDAD_PROMEDIO_HOMBRES, "+
"(SELECT COALESCE(TRUNC(AVG(TRUNC( ( TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMMDD')) -  TO_NUMBER(TO_CHAR(fec_nacimiento,'YYYYMMDD') ) ) / 10000))),0) from mclt_solicitud inner join mclt_persona using (cve_curp) where cve_sexo=2  and mclt_solicitud.cve_delegacion =md.cve_delegacion AND cve_estado_solicitud<>6 AND mclt_solicitud.cve_origen_solicitud=1 and to_date(mclt_solicitud.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy')) as EDAD_PROMEDIO_MUJERES "+
"from "+
"mclc_entidad_financiera mef  "+
"inner join mclt_solicitud ms on ms.cve_entidad_financiera=mef.cve_entidad_financiera "+
"inner join mclt_prestamo mp on ms.cve_solicitud=mp.cve_solicitud "+
"inner join mclc_delegacion md on md.cve_delegacion = ms.cve_delegacion "+
"WHERE  "+
"ms.cve_estado_solicitud not in (6,20,15,14) "+
"AND to_date(ms.fec_registro_actualizado,'dd-mm-yy') between TO_DATE(?1,'dd-mm-yy') and TO_DATE(?2,'dd-mm-yy') "+
"and ms.cve_delegacion=md.cve_delegacion "+
"group by ms.cve_subdelegacion, md.cve_delegacion,ms.cve_subdelegacion,mef.cve_entidad_financiera,  md.cve_delegacion ||' - ' || md.des_delegacion, ms.cve_subdelegacion ||' - ' "+
"order by 3 "

)  .setParameter(1,fechaInicio )
                .setParameter(2,fechaFin )
                .unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(ReporteMonitorPensionadosModel.class)).getResultList();
        log.info("reporte {1}" +reporte.toString());

        return reporte;
    }
}

    

