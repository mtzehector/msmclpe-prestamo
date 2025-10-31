/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import mx.gob.imss.dpes.prestamoback.entity.MclcSubTipoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juan.garfias
 */
public interface SubTipoReporteRepository  extends JpaRepository<MclcSubTipoReporte,Long>,
        JpaSpecificationExecutor<MclcSubTipoReporte>{
    
}
