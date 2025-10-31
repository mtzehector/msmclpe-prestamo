/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import mx.gob.imss.dpes.prestamoback.entity.MclcEntidadFinanciera;
import mx.gob.imss.dpes.prestamoback.entity.MclcTipoReporte;
import mx.gob.imss.dpes.prestamoback.entity.McltReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juan.garfias
 */
public interface ReporteRepository extends JpaRepository<McltReporte, Long>,
        JpaSpecificationExecutor<McltReporte> {

    McltReporte findByPeriodoNominaAndTipoReporteAndEntidadFinancieraIsNull(
            String periodoNomina,
            MclcTipoReporte tipoReporte);

    McltReporte findByPeriodoNominaAndTipoReporteAndEntidadFinanciera(
            String periodoNomina,
            MclcTipoReporte tipoReporte,
            MclcEntidadFinanciera entidadFinanciera);
}
