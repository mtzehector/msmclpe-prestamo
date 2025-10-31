/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.prestamoback.entity.MclcCorreo;
import mx.gob.imss.dpes.prestamoback.entity.McltReporte;
import mx.gob.imss.dpes.prestamoback.entity.McltReporteDocumento;

/**
 *
 * @author juan.garfias
 */
public class ReporteRs extends BaseModel {

    @Getter
    @Setter
    McltReporte reporte;
    @Getter
    @Setter
    List<McltReporteDocumento> reporteDocumentos;
    @Getter
    @Setter
    List<MclcCorreo> correos;

    public ReporteRs() {
    }

    public ReporteRs(McltReporte reporte, List<McltReporteDocumento> reporteDocumentos) {
        this.reporte = reporte;
        this.reporteDocumentos = reporteDocumentos;
    }

}
