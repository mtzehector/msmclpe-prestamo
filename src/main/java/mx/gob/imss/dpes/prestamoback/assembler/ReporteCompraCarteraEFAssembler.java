/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.assembler;

import java.io.Serializable;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.assembler.BaseAssembler;
import mx.gob.imss.dpes.common.enums.TipoDocumentoEnum;
import mx.gob.imss.dpes.interfaces.documento.model.Documento;
import mx.gob.imss.dpes.interfaces.prestamo.model.ResumenCartaInstruccion;
import mx.gob.imss.dpes.prestamoback.model.ReporteCartaInstruccion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.reporteCompraCartera.model.ReporteCompraCartera;
import mx.gob.imss.dpes.interfaces.reporteCompraCartera.model.ReporteCompraCarteraResponse;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class ReporteCompraCarteraEFAssembler
 extends BaseAssembler{

 

  
  public List<ReporteCompraCarteraResponse> assemble(List<ReporteCompraCartera> source) {
  List<ReporteCompraCarteraResponse> respuesta = new ArrayList<>();
  for (ReporteCompraCartera rep : source) {
            ReporteCompraCarteraResponse reporte = new ReporteCompraCarteraResponse();
            reporte.setCat(rep.getCAT().doubleValue());
            reporte.setCurp(rep.getCURP());
            reporte.setDescuento(rep.getDESCUENTO().doubleValue());
            reporte.setEmision(rep.getEMISION());
            reporte.setFolio(rep.getFOLIO());
            reporte.setImporte(rep.getIMPORTE().doubleValue());
            reporte.setImporteLiquidado(rep.getIMPORTE_LIQUIDADO().doubleValue());
            reporte.setNombreComercial(rep.getNOMBRE_COMERCIAL());
            reporte.setNombreCompleto(rep.getNOMBRE_COMPLETO());
            reporte.setNss(rep.getNSS());
            reporte.setNumeroDeProveedor(rep.getNUMERO_DE_PROVEEDOR().toString());
            reporte.setNumDescuento(rep.getNUM_DESCUENTO().intValue());
            respuesta.add(reporte);
        }
    return respuesta;
  }

    @Override
    public Serializable toEntity(BaseModel source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable toPKEntity(Serializable source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseModel assemble(Serializable source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable assemblePK(Serializable source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
