/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import java.util.List;
import mx.gob.imss.dpes.prestamoback.entity.PrestamoRecuperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juan.garfias
 */
public interface PrestamoRecuperacionRepository extends JpaRepository<PrestamoRecuperacion, Long>,
        JpaSpecificationExecutor<PrestamoRecuperacion> {
        
        List<PrestamoRecuperacion> findBySolicitud(Long id);
        List<PrestamoRecuperacion> findBySolicitudAndNumEntidadFinanciera(Long solicitud, String numEntidadFinanciera);
}

