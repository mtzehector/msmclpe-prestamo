/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.model;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.interfaces.prestamo.model.ResumenCartaInstruccion;

/**
 *
 * @author osiris.hernandez
 */
public class ReporteCartaInstruccion extends ReporteResumenSimulacion{
    @Getter @Setter PromotorModel promotor = new PromotorModel();
    @Getter @Setter ResumenCartaInstruccion resumenCartaInstruccion = new ResumenCartaInstruccion();
}
