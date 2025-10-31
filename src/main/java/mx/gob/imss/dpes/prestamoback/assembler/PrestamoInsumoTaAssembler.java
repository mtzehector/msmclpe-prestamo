package mx.gob.imss.dpes.prestamoback.assembler;

import mx.gob.imss.dpes.common.assembler.BaseAssembler;
import mx.gob.imss.dpes.interfaces.prestamo.model.PrestamoInsumoTa;
import mx.gob.imss.dpes.prestamoback.entity.McltPrestamoInsumoTa;

import javax.ws.rs.ext.Provider;

@Provider
public class PrestamoInsumoTaAssembler extends BaseAssembler<McltPrestamoInsumoTa, PrestamoInsumoTa> {
    @Override
    public PrestamoInsumoTa assemble(McltPrestamoInsumoTa source) {
        if(source == null)
            return null;

        PrestamoInsumoTa prestamoInsumo = new PrestamoInsumoTa();
        prestamoInsumo.setId(source.getId());
        prestamoInsumo.setHistorico(source.getHistorico());
        prestamoInsumo.setCveSolicitud(source.getCveSolicitud());
        prestamoInsumo.setPlazo(source.getPlazo());
        prestamoInsumo.setCat(source.getCat());
        prestamoInsumo.setMonto(source.getMonto());
        prestamoInsumo.setTotalPagar(source.getTotalPagar());
        prestamoInsumo.setCveTipoSimulacion(source.getCveTipoSimulacion());
        prestamoInsumo.setFolioSipre(source.getFolioSipre());
        prestamoInsumo.setDescuentoMensual(source.getDescuentoMensual());
        return prestamoInsumo;
    }
}
