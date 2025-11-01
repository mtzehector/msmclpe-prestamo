package mx.gob.imss.dpes.prestamoback.assembler;

import mx.gob.imss.dpes.common.assembler.BaseAssembler;
import mx.gob.imss.dpes.common.enums.TipoCreditoEnum;
import mx.gob.imss.dpes.common.enums.TipoSimulacionEnum;
import mx.gob.imss.dpes.prestamoback.entity.Prestamo;

import javax.ws.rs.ext.Provider;

@Provider
public class PrestamoAssembler extends BaseAssembler<Prestamo, mx.gob.imss.dpes.interfaces.prestamo.model.Prestamo> {
    @Override
    public mx.gob.imss.dpes.interfaces.prestamo.model.Prestamo assemble(Prestamo source) {
        if(source == null)
            return null;

        mx.gob.imss.dpes.interfaces.prestamo.model.Prestamo p = new mx.gob.imss.dpes.interfaces.prestamo.model.Prestamo();
        p.setId(source.getId());
        p.setSolicitud(source.getSolicitud());
        p.setImpMontoSol(source.getMonto() == null? null : source.getMonto().doubleValue());
        p.setImpDescNomina(source.getImpDescNomina() == null? null : source.getImpDescNomina().doubleValue());
        p.setImpTotalPagar(source.getImpTotalPagar() == null? null : source.getImpTotalPagar().doubleValue());
        p.setPrimerDescuento(source.getPrimerDescuento());
        p.setIdOferta(source.getIdOferta());
        p.setNumPeriodoNomina(source.getNumPeriodoNomina());
        p.setPromotor(source.getPromotor());
        p.setTipoSimulacion(TipoSimulacionEnum.forValue(source.getTipoSimulacion()));
        p.setTipoCredito(TipoCreditoEnum.forValue(source.getTipoCredito()));
        p.setRefCuentaClabe(source.getRefCuentaClabe());
        p.setAltaRegistro(source.getAltaRegistro());
        return p;
    }
}
