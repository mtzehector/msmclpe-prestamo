/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import java.util.List;
import mx.gob.imss.dpes.prestamoback.entity.MclcCorreo;
import mx.gob.imss.dpes.prestamoback.entity.MclcSubTipoReporte;
import mx.gob.imss.dpes.prestamoback.entity.MclcTipoReporte;
import mx.gob.imss.dpes.prestamoback.entity.McltReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juan.garfias
 */
public interface CorreoRepository extends JpaRepository<MclcCorreo, Long>,
        JpaSpecificationExecutor<MclcCorreo> {

    List<MclcCorreo> findByCorreoActivo(Long correoActivo);

}
