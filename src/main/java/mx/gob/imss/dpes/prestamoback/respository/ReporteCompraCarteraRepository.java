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
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.Plazo;
import mx.gob.imss.dpes.interfaces.pensionado.model.Pensionado;
import mx.gob.imss.dpes.interfaces.reporteCompraCartera.model.ReporteCompraCartera;
import mx.gob.imss.dpes.interfaces.userdata.model.UserData;
import mx.gob.imss.dpes.prestamoback.entity.McltReporte;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

/**
 *
 * @author ernesto.palacios
 */
@Component
public class ReporteCompraCarteraRepository {

    
    protected final transient Logger log = Logger.getLogger(getClass().getName());
    @PersistenceContext
    private EntityManager entityManager;
    
    public ReporteCompraCarteraRepository() {
    }
    
    public List<ReporteCompraCartera> getReporteCompraCartera(String periodoNomina,
            Long cveEntidadFinanciera) {
        log.info("periodoNomina"+periodoNomina+" cveEntidadFinanciera"+ cveEntidadFinanciera);
        List<ReporteCompraCartera> reporte = entityManager.createNativeQuery( 
"select (select nom_comercial from mclc_entidad_financiera where cve_entidad_financiera = ms.cve_entidad_financiera)NOMBRE_COMERCIAL, "
+"(select numero_proveedor from mclc_entidad_financiera where cve_entidad_financiera = ms.cve_entidad_financiera) NUMERO_DE_PROVEEDOR, "
+"SYSDATE EMISION, "
+"mpr.num_solicitud_sipre FOLIO, "
+"ms.num_nss NSS, "
+"mpp.cve_curp CURP, "
+"mpp.nombre ||' '|| mpp.primer_apellido ||' '|| mpp.segundo_apellido NOMBRE_COMPLETO, "
+"mpr.IMP_REAL_PRESTAMO IMPORTE, "
+"mpr.can_descuento_mensual DESCUENTO, "
+"mpr.num_mes_recuperado NUM_DESCUENTO, "
+"COALESCE(mpr.can_monto_sol,((mpr.num_plazo_prestamo-mpr.num_mes_recuperado)*mpr.can_descuento_mensual)) IMPORTE_LIQUIDADO, "
+"mpr.can_cat_prestamo CAT "
+"from mclc_calendario_nomina mcn, "
+"mclc_entidad_financiera ef  "
+"inner join mclt_prestamo_recuperacion mpr on "
+"ef.cve_entidad_financiera_sipre =mpr.num_entidad_financiera "
+"JOIN mclt_prestamo mp on "
+"mp.cve_prestamo=mpr.cve_prestamo "
+"join mclt_solicitud ms on "
+"ms.cve_solicitud=mpr.cve_solicitud  "
+" join mclt_persona mpp on "
+"mpp.cve_curp=ms.cve_curp "
+"where  "
+"mpr.num_entidad_financiera =(select ef.cve_entidad_financiera_sipre from mclc_entidad_financiera where cve_entidad_financiera = ?1) "
+"and ms.cve_entidad_financiera<>?2 "
+"and ms.cve_estado_solicitud = 3 "
+"and mp.cve_tipo_credito in (3,6) "
+"and mcn.num_periodo_nomina=?3 "
+"and ms.fec_registro_actualizado BETWEEN mcn.fec_inicio_ejecucion and mcn.fec_fin_ejecucion ")
                .setParameter(1,cveEntidadFinanciera )
                .setParameter(2,cveEntidadFinanciera )
                .setParameter(3, periodoNomina )                
                .unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(ReporteCompraCartera.class)).getResultList();
        log.info("reporte {1}" +reporte.toString());

        return reporte;
    }
    
}

    

