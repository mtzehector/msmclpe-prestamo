/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.respository;

import java.util.List;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionTabla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author gabriel.rios
 */
public interface AmortizacionTablaRepository extends JpaRepository<AmortizacionTabla, Long>, 
        JpaSpecificationExecutor<AmortizacionTabla>{
    List<AmortizacionTabla> findByAmortizacionDatosBaseOrderByPeriodo(AmortizacionDatosBase amortizacionDatosBase);
    
}
