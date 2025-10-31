/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.rules;

import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.enums.SelloElectronicoEnum;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.rule.BaseRule;
import mx.gob.imss.dpes.interfaces.serviciosdigitales.model.SelloElectronicoRequest;
import mx.gob.imss.dpes.prestamoback.model.ReporteCartaInstruccion;

import java.text.SimpleDateFormat;
import lombok.Getter;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class CadenaOriginalCartaInstruccionRule extends BaseRule<CadenaOriginalCartaInstruccionRule.Input, CadenaOriginalCartaInstruccionRule.Output> {

    public static final String SEPARATOR = "|";
    public static final String TIPOCREDITO = "Nuevo";
    public static final String COLON = ":";

    @Override
    public Output apply(Input input) {
      StringBuilder sb = new StringBuilder();
      
      sb.append(SEPARATOR);
      sb.append(SEPARATOR);
      sb.append(SelloElectronicoEnum.NSS);
      sb.append(COLON);
      sb.append(input.reporteCartaInstruccion.getSolicitud().getNss());
      sb.append(SEPARATOR);
      sb.append(SelloElectronicoEnum.FOLIOPRESTAMO);
      sb.append(COLON);
      sb.append(input.reporteCartaInstruccion.getSolicitud().getNumFolioSolicitud());
      sb.append(SEPARATOR);
      sb.append(SelloElectronicoEnum.FECSOLICITUDPRESTAMO);
      sb.append(COLON);
      sb.append(new SimpleDateFormat("dd/MM/yyyy")
          .format(input.reporteCartaInstruccion.getSolicitud().getAltaRegistro()));
      sb.append(SEPARATOR);
      sb.append(SelloElectronicoEnum.TIPOCREDITO);
      sb.append(COLON);
      sb.append(input.reporteCartaInstruccion.getPrestamo().getTipoCredito()!=null?input.reporteCartaInstruccion.getPrestamo().getTipoCredito():"Nuevo");
      sb.append(SEPARATOR);
      sb.append(SEPARATOR);
      
      SelloElectronicoRequest request = new SelloElectronicoRequest();
      request.setCadenaOriginal(sb.toString());        
      return new Output(request);
    }

    

//    public class Input extends BaseModel {
//
//        @Getter private final transient ReporteCartaInstruccion reporteCartaInstruccion;
//
//        public Input(ReporteCartaInstruccion reporteCartaInstruccion) {
//            this.reporteCartaInstruccion = reporteCartaInstruccion;
//        }
//    }
//
//    public class Output extends BaseModel {
//
//        @Getter private final SelloElectronicoRequest request;
//
//        public Output(SelloElectronicoRequest request) {
//            this.request = request;
//        }
//    }
    
     public class Input extends BaseModel {

        @Getter private final transient ReporteResumenSimulacion reporteCartaInstruccion;

        public Input(ReporteResumenSimulacion reporteCartaInstruccion) {
            this.reporteCartaInstruccion = reporteCartaInstruccion;
        }
    }

    public class Output extends BaseModel {

        @Getter private final SelloElectronicoRequest request;

        public Output(SelloElectronicoRequest request) {
            this.request = request;
        }
    }

}

