/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.assembler;

import java.util.logging.Level;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.assembler.BaseAssembler;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionPorDescuento;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionTabla;

/**
 *
 * @author gabriel.rios
 */
public class AmortizacionTablaAssembler extends BaseAssembler<AmortizacionPorDescuento, AmortizacionTabla>{
    
    @Override
    public AmortizacionTabla assemble(AmortizacionPorDescuento amortizacionPorDescuento){
        
        AmortizacionTabla amortizacionTabla = new AmortizacionTabla();
        amortizacionTabla.setPeriodo(amortizacionPorDescuento.getPeriodo());
        amortizacionTabla.setSaldo(amortizacionPorDescuento.getSaldo());
        amortizacionTabla.setCapital(amortizacionPorDescuento.getCapital());
        amortizacionTabla.setIntereses(amortizacionPorDescuento.getIntereses());
        amortizacionTabla.setIva(amortizacionPorDescuento.getIva());
        amortizacionTabla.setDescuento(amortizacionPorDescuento.getDescuento());
        return amortizacionTabla;
    }
}
