/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.assembler;

import java.util.logging.Level;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.assembler.BaseAssembler;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionInsumos;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionPorDescuento;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionTabla;

/**
 *
 * @author gabriel.rios
 */
public class AmortizacionPorDescuentoAssembler extends BaseAssembler<AmortizacionTabla, AmortizacionPorDescuento>{
    
    @Override
    public AmortizacionPorDescuento assemble(AmortizacionTabla amortizacionTabla){
        
        AmortizacionPorDescuento insumos = new AmortizacionPorDescuento();
        insumos.setPeriodo(amortizacionTabla.getPeriodo());
        insumos.setSaldo(amortizacionTabla.getSaldo());
        insumos.setCapital(amortizacionTabla.getCapital());
        insumos.setIntereses(amortizacionTabla.getIntereses());
        insumos.setIva(amortizacionTabla.getIva());
        insumos.setDescuento(amortizacionTabla.getDescuento());
        return insumos;
    }
}
