/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import static java.lang.Math.log;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.enums.TipoSimulacionEnum;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.BaseService;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionInsumos;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionPorDescuento;
import mx.gob.imss.dpes.prestamoback.assembler.AmortizacionInsumosAssembler;
import mx.gob.imss.dpes.prestamoback.assembler.AmortizacionPorDescuentoAssembler;
import mx.gob.imss.dpes.prestamoback.assembler.AmortizacionTablaAssembler;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionTabla;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoInsumoTaException;

/**
 *
 * @author juanf.barragan
 */
@Provider
public class CalcularTblAmortizacionDupService extends BaseService {
     @Inject
    TblAmortizacionDescuentoMensualService tblAmortizacionDescuentoService;

    @Inject
    TblAmortizacionMontoSolicitadoService tblAmortizacionMontoService;

    @Inject
    AmortizacionTablaPersistenceService tblPersistenceService;

    public Message<LinkedList<AmortizacionPorDescuento>> execute(Message<AmortizacionDatosBase> request)
            throws BusinessException {
        AmortizacionInsumosAssembler amortizacionInsumosAssembler = new AmortizacionInsumosAssembler();
        AmortizacionTablaAssembler amortizacionTablaAssembler = new AmortizacionTablaAssembler();
        AmortizacionPorDescuentoAssembler amortizacionPorDescuentoAssembler = new AmortizacionPorDescuentoAssembler();
        //log.log(Level.INFO, ">>>CalcularTblAmortizacionService request.getPayload()= {0}", request.getPayload());
        AmortizacionDatosBase amortizacionDatosBase = request.getPayload();
        LinkedList<AmortizacionPorDescuento> amortizacionTablaList = new LinkedList<AmortizacionPorDescuento>();

        LinkedList<AmortizacionPorDescuento> amortizacionPorDescuentoList = getAmortizacionList(amortizacionInsumosAssembler.assemble(amortizacionDatosBase));
        for (AmortizacionPorDescuento amortizacionPorDescuento : amortizacionPorDescuentoList) {
            if(amortizacionPorDescuento.getDescuento() != 0){
                amortizacionPorDescuento.setDescuento(amortizacionPorDescuento.getDescuento()- .01);
                amortizacionPorDescuento.setCapital(amortizacionPorDescuento.getCapital()- .01);
                
            }
            
            AmortizacionTabla amortizacionTabla = amortizacionTablaAssembler.assemble(amortizacionPorDescuento);
            amortizacionTabla.setAmortizacionDatosBase(amortizacionDatosBase);
            //log.log(Level.INFO, ">>>CalcularTblAmortizacionService amortizacionTabla= {0}", amortizacionTabla);
            if (request.getPayload().getTest() != null && request.getPayload().getTest() > 0) {
                amortizacionTablaList.add(amortizacionPorDescuentoAssembler.assemble(amortizacionTabla));
            } else {
                amortizacionTablaList.add(amortizacionPorDescuentoAssembler.assemble(tblPersistenceService.saveAmortizacionTabla(amortizacionTabla)));
            }
        }
        return new Message(amortizacionTablaList);

    }

    public LinkedList<AmortizacionPorDescuento> getAmortizacionList(AmortizacionInsumos insumos) {
        if (insumos.getCveTipoSimulacion().compareTo(TipoSimulacionEnum.MONTO.toValue()) == 0) {
            return tblAmortizacionMontoService.getAmortizacionList(
                    insumos.getImporteMontoSolicitado().floatValue(),
                    insumos.getCat().floatValue(),
                    insumos.getPlazo(),
                    insumos.getImporteDescuentoMensual() != null ? insumos.getImporteDescuentoMensual().floatValue() : 0);
        } else {
            return tblAmortizacionDescuentoService.getAmortizacionList(insumos.getImporteDescuentoMensual().floatValue(), insumos.getCat().floatValue(), insumos.getPlazo(), insumos.getImporteMontoSolicitado() != null ? insumos.getImporteMontoSolicitado() : 0);
        }
    }

    public LinkedList<AmortizacionPorDescuento> getAmortizacionTablaList(AmortizacionDatosBase amortizacionDatosBase)
        throws BusinessException {

        try {
            AmortizacionPorDescuentoAssembler amortizacionPorDescuentoAssembler = new AmortizacionPorDescuentoAssembler();
            List<AmortizacionTabla> amortizacionTablaList = tblPersistenceService.getAmortizacionTablaList(amortizacionDatosBase);
            LinkedList<AmortizacionPorDescuento> linKedList = new LinkedList<AmortizacionPorDescuento>();

            for (AmortizacionTabla amortizacionTabla : amortizacionTablaList) {
                linKedList.add(amortizacionPorDescuentoAssembler.assemble(amortizacionTabla));
            }
            return linKedList;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR CalcularTblAmortizacionDupService.getAmortizacionTablaList = [" +
                        amortizacionDatosBase + "]", e);
        }

        throw new PrestamoInsumoTaException(PrestamoInsumoTaException.ERROR_SERVICIO_CALCULO_TABLA_AMORTIZACION);
    }

    public float getCapitalInsoluto(AmortizacionDatosBase amortizacionDatosBase, int numMensualidad) throws BusinessException {
        log.log(Level.INFO, ">>>prestamoBACK AmortizacionEndPoint getTablaAmortizacionByFolioSipre amortizacionDatosBase.getId= {0}", amortizacionDatosBase.getId());
        LinkedList<AmortizacionPorDescuento> amortizacionTablaList = getAmortizacionTablaList(amortizacionDatosBase);
        float saldo = 0;

        for (AmortizacionPorDescuento amortizacionPorDescuento : amortizacionTablaList) {
            if (amortizacionPorDescuento.getPeriodo() == numMensualidad) {
                saldo = amortizacionPorDescuento.getSaldo().floatValue();
            }
        }
        log.log(Level.INFO, ">>>> CalcularTblAmortizacionService saldo= {0}", saldo);
        return saldo;
    }

}
