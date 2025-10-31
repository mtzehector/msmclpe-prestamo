/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.ws.rs.ext.Provider;
//import static jdk.nashorn.internal.objects.NativeMath.round;
import mx.gob.imss.dpes.common.service.BaseService;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionPorDescuento;
import static mx.gob.imss.dpes.prestamoback.service.TblAmortizacionDescuentoMensualService.FINAL_PREC;
import static mx.gob.imss.dpes.prestamoback.service.TblAmortizacionDescuentoMensualService.PREC;
import mx.gob.imss.dpes.support.util.NumUtils;
import org.nevec.rjm.BigDecimalMath;

/**
 *
 * @author gabriel.rios
 */
@Provider
public class TblAmortizacionMontoSolicitadoService extends BaseService{
    
 BigDecimal Monto_Prestamo;

 int Plazo;

 int nPlazo;

 double Cat;

 int nTemporalidad;

 BigDecimal nInteres;

 BigDecimal nDenominador;

 BigDecimal nMonto;

 double nPlazoMonto2;

 BigDecimal nDescuento;

 BigDecimal nInteresIva;

 BigDecimal PagoInteres;

 BigDecimal PagoInteresIva;

 BigDecimal PagoCapital;

 double CatSinIva;

 BigDecimal IntersSinIva;

 BigDecimal InteresConIva;

 BigDecimal Comision;

    
    
    public LinkedList<AmortizacionPorDescuento> getAmortizacionList(float montoSolicitado,float cat,int plazo,float descuentoMensual){
        LinkedList<AmortizacionPorDescuento> amortizacionPorDescuento = setInitVars(montoSolicitado,cat,plazo,descuentoMensual);
        return getRows(amortizacionPorDescuento);
    }

    public LinkedList<AmortizacionPorDescuento> setInitVars(float montoSolicitado,float catConIva,int plazo,float descuentoMensual) {
        BigDecimal exp;
        BigDecimal base;
        
        nTemporalidad = 12;

        Monto_Prestamo = BigDecimal.valueOf(montoSolicitado);

        Plazo = plazo;

        CatSinIva = 28.752695;

        nMonto = Monto_Prestamo;

        Comision = BigDecimal.valueOf(0.00);

        nDenominador = BigDecimal.valueOf(0);

        nDescuento =  BigDecimal.valueOf(0);
        
        //IntersSinIva = Math.pow((1 + (CatSinIva / 100)), (1d / nTemporalidad)) - 1;

        //InteresConIva = IntersSinIva * 1.16;

        //Cat = NumUtils.round((Math.pow((1+(InteresConIva)),(nTemporalidad))-1)*100,2);
        
        //log.log(Level.INFO,">>>TblAmortizacionMontoSolicitadoService  Cat="+Cat+"  InteresConIva="+InteresConIva+" IntersSinIva="+IntersSinIva);
        //InteresConIva =  Math.pow(((catConIva/100)+1), (1d/nTemporalidad)) - 1;
        base = (BigDecimal.valueOf(catConIva).divide(BigDecimal.valueOf(100),PREC,RoundingMode.HALF_UP)).add(BigDecimal.valueOf(1));
        exp = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(nTemporalidad),PREC,RoundingMode.HALF_UP);
        InteresConIva = BigDecimalMath.pow(base, exp ).subtract(BigDecimal.valueOf(1));
        
        //IntersSinIva = InteresConIva/1.16;
        BigDecimal percentage = BigDecimal.valueOf(1.16);
        IntersSinIva = InteresConIva.divide(percentage,PREC,RoundingMode.HALF_UP);
        Cat=catConIva;
        log.log(Level.INFO,">>>TblAmortizacionMontoSolicitadoService CALCULATED Cat="+Cat+"  InteresConIva="+InteresConIva+" IntersSinIva="+IntersSinIva);
        
        //For nPlazoMonto In 1..Plazo Loop
        for (double nPlazoMonto = 1; nPlazoMonto <= Plazo; nPlazoMonto++) {

            nPlazoMonto2 = nPlazoMonto * -1;

            //nDenominador = nDenominador + Math.pow((1+(Cat/100)),((nPlazoMonto2)/nTemporalidad));
            base = BigDecimal.valueOf(1).add(BigDecimal.valueOf(Cat).divide(BigDecimal.valueOf(100),PREC,RoundingMode.HALF_UP));
            exp = BigDecimal.valueOf(nPlazoMonto2).divide(BigDecimal.valueOf(nTemporalidad),PREC,RoundingMode.HALF_UP);
            nDenominador = nDenominador.add(BigDecimalMath.pow(base, exp ) );
        }
        nDescuento = (nMonto.subtract(Comision)).divide(nDenominador,PREC,RoundingMode.HALF_UP);
        

        //nMonto = nMonto-Comision;
        nMonto = nMonto.subtract(Comision);

        System.out.println("Monto Solicitado:|"+Monto_Prestamo);

        System.out.println("Plazo:|"+Plazo);

        System.out.println("Comision:|"+Comision);

        System.out.println("Cat Sin IVA:|"+CatSinIva+"%");

        System.out.println("Cat Con IVA:|"+Cat+"%");

        System.out.println("Descuento Mensual:|"+nDescuento);

        //System.out.println("Interes mensual Con IVA:|"+round(InteresConIva*100,2)+"%");
        System.out.println("Interes mensual Con IVA:|"+NumUtils.round(InteresConIva.multiply(BigDecimal.valueOf(100)), FINAL_PREC) + "%");

        //System.out.println("Interes mensual Sin IVA:|"+round(IntersSinIva*100,2)+"%");
        System.out.println("Interes mensual Sin IVA:|"+NumUtils.round((InteresConIva.multiply(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(1.16), FINAL_PREC,RoundingMode.HALF_UP), FINAL_PREC) + "%");


        System.out.println("Periodo|Saldo|Capital|Intereses|IVA|Descuento"); 

        System.out.println(0+"|"+nMonto+"|"+0.00+"|"+0.00+"|"+0.00+"|"+0.00);
        
        LinkedList<AmortizacionPorDescuento> rowList = new LinkedList();
        AmortizacionPorDescuento amortizacionRow = new AmortizacionPorDescuento();
            amortizacionRow.setPeriodo(0);
            //amortizacionRow.setSaldo(nMonto);
            amortizacionRow.setSaldo(NumUtils.round(nMonto,FINAL_PREC));
            amortizacionRow.setCapital(0.00);
            amortizacionRow.setIntereses(0.00);
            amortizacionRow.setIva(0.00);
            amortizacionRow.setDescuento(0.00);
            rowList.add(amortizacionRow);
        
        return rowList;
    }
    
    protected LinkedList<AmortizacionPorDescuento> getRows(LinkedList<AmortizacionPorDescuento> rowList){
        double PagoCapitalTotal=0L;
        double PagoInteresTotal=0L;
        double PagoInteresIvaTotal=0L;
        double nDescuentoTotal=0L;
        
         for (double nPlazo = 1; nPlazo <= Plazo; nPlazo++) {

            //PagoInteres = nMonto * IntersSinIva;
            PagoInteres = nMonto.multiply(IntersSinIva);

            //PagoInteresIva = PagoInteres*0.16;
            PagoInteresIva = PagoInteres.multiply(BigDecimal.valueOf(0.16));

            //PagoCapital = nDescuento-PagoInteres-PagoInteresIva;
            PagoCapital = nDescuento.subtract(PagoInteres).subtract(PagoInteresIva);

            //nMonto = nMonto - PagoCapital;
            nMonto = nMonto.subtract(PagoCapital);

            if(nPlazo == Plazo){

                //PagoCapital = PagoCapital + nMonto;
                PagoCapital = PagoCapital.add(nMonto);

                //PagoInteres = PagoInteres - nMonto;
                PagoInteres = PagoInteres.subtract(nMonto);

                //nMonto = 0;
                nMonto = BigDecimal.valueOf(0);

            }

            System.out.println((int)nPlazo+"|"+NumUtils.round(nMonto,PREC)+"|"+NumUtils.round(PagoCapital,PREC)+"|"+NumUtils.round(PagoInteres,PREC)+"|"+NumUtils.round(PagoInteresIva,PREC)+"|"+(double)NumUtils.round(nDescuento,PREC));
            AmortizacionPorDescuento amortizacionRow = new AmortizacionPorDescuento();
            amortizacionRow.setPeriodo((int)nPlazo);
            amortizacionRow.setSaldo(NumUtils.round(nMonto,FINAL_PREC));
            amortizacionRow.setCapital(NumUtils.round(PagoCapital,FINAL_PREC));PagoCapitalTotal+=NumUtils.round(PagoCapital,FINAL_PREC);
            amortizacionRow.setIntereses(NumUtils.round(PagoInteres,FINAL_PREC));PagoInteresTotal+=NumUtils.round(PagoInteres,FINAL_PREC);
            amortizacionRow.setIva(NumUtils.round(PagoInteresIva,FINAL_PREC));PagoInteresIvaTotal+=NumUtils.round(PagoInteresIva,FINAL_PREC);
            amortizacionRow.setDescuento((double)NumUtils.round(nDescuento,FINAL_PREC)); nDescuentoTotal+=(double)NumUtils.round(nDescuento,FINAL_PREC);
            rowList.add(amortizacionRow);
        }
        PagoCapitalTotal = NumUtils.round(PagoCapitalTotal,FINAL_PREC);
        PagoInteresTotal = NumUtils.round(PagoInteresTotal,FINAL_PREC);
        PagoInteresIvaTotal = NumUtils.round(PagoInteresIvaTotal,FINAL_PREC);
        nDescuentoTotal = NumUtils.round(nDescuentoTotal,FINAL_PREC);
        double compensacion=nDescuentoTotal-(PagoCapitalTotal+PagoInteresTotal+PagoInteresIvaTotal);
        double nInteres=rowList.getLast().getIntereses();
        compensacion = NumUtils.round(compensacion,FINAL_PREC);
        nInteres = NumUtils.round(nInteres,FINAL_PREC);
        log.log(Level.INFO,">>><<<compensacion="+compensacion+" nDescuentoTotal="+nDescuentoTotal+" PagoCapitalTotal="+PagoCapitalTotal+" PagoInteresTotal="+PagoInteresTotal+" PagoInteresIvaTotal="+PagoInteresIvaTotal);
        double compensatedInteres = nInteres+compensacion;
        compensatedInteres = NumUtils.round(compensatedInteres,FINAL_PREC);
        
        rowList.getLast().setIntereses(compensatedInteres);
        log.log(Level.INFO,">>><<<++++++++++++  nInteres="+nInteres+" nInteres+compensacion="+(compensatedInteres));
        for(AmortizacionPorDescuento amortizacionRow:rowList){
            System.out.println(amortizacionRow.getPeriodo()+"|"+amortizacionRow.getSaldo()+"|"+amortizacionRow.getCapital()+"|"+amortizacionRow.getIntereses()+"|"+amortizacionRow.getIva()+"|"+amortizacionRow.getDescuento());
        }
        return rowList;

        
    }
    
    
    
    
    
    
    
    

}
