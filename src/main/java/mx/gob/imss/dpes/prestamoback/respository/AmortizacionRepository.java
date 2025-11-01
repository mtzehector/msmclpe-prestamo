/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author edgar.arenas
 */
public interface AmortizacionRepository extends JpaRepository<AmortizacionDatosBase, Long>,
        JpaSpecificationExecutor<AmortizacionDatosBase>{
    
    AmortizacionDatosBase findByCveSolicitud(Long cveSolicitud);
    AmortizacionDatosBase findByFolioSipre(String folioSipre);
}
