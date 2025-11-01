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
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;

/**
 *
 * @author gabriel.rios
 */
public class AmortizacionInsumosAssembler extends BaseAssembler<AmortizacionDatosBase, AmortizacionInsumos>{
    
    @Override
    public AmortizacionInsumos assemble(AmortizacionDatosBase amortizacionDatosBase){
        
        AmortizacionInsumos insumos = new AmortizacionInsumos();
        insumos.setCat(amortizacionDatosBase.getCat());
        insumos.setCveSolicitud(amortizacionDatosBase.getCveSolicitud());
        insumos.setImporteMontoSolicitado(amortizacionDatosBase.getImporteMontoSolicitado()!=null?amortizacionDatosBase.getImporteMontoSolicitado().doubleValue():null);
        insumos.setImporteDescuentoMensual(amortizacionDatosBase.getImporteDescuentoMensual()!=null?amortizacionDatosBase.getImporteDescuentoMensual().doubleValue():null);
        insumos.setImporteTotalPagar(amortizacionDatosBase.getImporteTotalPagar()!=null?amortizacionDatosBase.getImporteTotalPagar().doubleValue():null);
        insumos.setCveTipoSimulacion(amortizacionDatosBase.getCveTipoSimulacion());
        insumos.setPlazo(amortizacionDatosBase.getPlazo());
        insumos.setHistorico(amortizacionDatosBase.getHistorico());
        insumos.setFolioSipre(amortizacionDatosBase.getFolioSipre());
        
        log.log(Level.INFO, ">>> AmortizacionInsumosAssembler return : {0}", insumos);
        return insumos;
    }
}
