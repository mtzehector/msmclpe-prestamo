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
import mx.gob.imss.dpes.common.service.BaseService;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionPorDescuento;
import mx.gob.imss.dpes.support.util.NumUtils;
import org.nevec.rjm.BigDecimalMath;


/**
 *
 * @author gabriel.rios
 */
@Provider
public class TblAmortizacionDescuentoMensualService extends BaseService{
    
    protected static int PREC = 20;
    protected static int FINAL_PREC = 2;
    

    BigDecimal Descuento_Solicitado;
    //float Descuento_Solicitado;
    int Plazo;
    int nPlazo;
    float Cat;
    int nTemporalidad;
    BigDecimal nInteres;
    BigDecimal nIva;
    BigDecimal nDenominador;
    BigDecimal nMonto;
    float nCat;
    double nPlazoMonto2;
    double nDescuento;
    BigDecimal nInteresIva;
    BigDecimal PagoInteres;
    BigDecimal PagoInteresIva;
    BigDecimal PagoCapital;
    BigDecimal nMonto_A_Prestar;
    double CatSinIva;
    BigDecimal IntersSinIva;
    BigDecimal InteresConIva;
    BigDecimal Comision;

        
    public LinkedList<AmortizacionPorDescuento> getAmortizacionList(float descuentoMensual,float cat,int plazo, double montoSolicitado){
        LinkedList<AmortizacionPorDescuento> amortizacionPorDescuentoList = setInitVars(descuentoMensual,cat,plazo,montoSolicitado);
        return getRows(amortizacionPorDescuentoList);
    }

    public LinkedList<AmortizacionPorDescuento> setInitVars(float descuentoMensual,float catConIva,int plazo, double montoSolicitado) {
        
        BigDecimal exp;
        BigDecimal base;
        nTemporalidad = 12;
        
        Descuento_Solicitado = BigDecimal.valueOf(descuentoMensual);
        //Descuento_Solicitado = descuentoMensual;
        
        Plazo = plazo;
        
        Comision = BigDecimal.valueOf(0.0);
        
        CatSinIva = 28.752695;
        
        nDenominador = BigDecimal.valueOf(0d);
        //nDenominador = 0d;
        
        nDescuento = 0;
        
        //InteresConIva =  Math.pow(((catConIva/100)+1), (1d/nTemporalidad)) - 1;
        base = (BigDecimal.valueOf(catConIva).divide(BigDecimal.valueOf(100),PREC,RoundingMode.HALF_UP)).add(BigDecimal.valueOf(1));
        exp = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(nTemporalidad),PREC,RoundingMode.HALF_UP);
        InteresConIva = BigDecimalMath.pow(base, exp ).subtract(BigDecimal.valueOf(1));
            
        //IntersSinIva = InteresConIva/1.16;
        BigDecimal percentage = BigDecimal.valueOf(1.16);
        //log.log(Level.INFO,"    >>><<<percentage="+percentage);
        //log.log(Level.INFO,"    >>><<<InteresConIva="+InteresConIva);
        IntersSinIva = InteresConIva.divide(percentage,PREC,RoundingMode.HALF_UP);
        Cat=catConIva;
        log.log(Level.INFO,">>>TblAmortizacionDescuentoMensualService CALCULATED Cat="+Cat+"  InteresConIva="+InteresConIva+" IntersSinIva="+IntersSinIva);
        for (double nPlazoMonto = 1; nPlazoMonto <= Plazo; nPlazoMonto++) {
            nPlazoMonto2 = nPlazoMonto * -1;
            //nDenominador = nDenominador + (Descuento_Solicitado) * Math.pow((1 + (Cat / 100)), (nPlazoMonto2 / nTemporalidad));
            base = BigDecimal.valueOf(1).add(BigDecimal.valueOf(Cat).divide(BigDecimal.valueOf(100),PREC,RoundingMode.HALF_UP));
            exp = BigDecimal.valueOf(nPlazoMonto2).divide(BigDecimal.valueOf(nTemporalidad),PREC,RoundingMode.HALF_UP);
            nDenominador = nDenominador.add(Descuento_Solicitado.multiply(BigDecimalMath.pow(base, exp ) )  );
            BigDecimal pow = BigDecimalMath.pow(base, exp).setScale(PREC,RoundingMode.HALF_UP);
            log.log(Level.INFO,"BASE(1 + (Cat / 100)) ="+base);
            log.log(Level.INFO,"EXP((nPlazoMonto2) / nTemporalidad) ="+exp);
            log.log(Level.INFO,"pow="+pow);
            log.log(Level.INFO,"nDenominador="+nDenominador+"  nPlazoMonto2="+nPlazoMonto2);
            log.log(Level.INFO,"");
            log.log(Level.INFO,">>>>    nDenominador="+nDenominador+"  Comision="+Comision);
            log.log(Level.INFO,">>>>    round(nDenominador, FINAL_PREC)="+(NumUtils.round(nDenominador, FINAL_PREC))+" (1 - Comision)="+(BigDecimal.valueOf(1).subtract(Comision)));
        
        }
        
        
        if(montoSolicitado>0){
            nMonto_A_Prestar = BigDecimal.valueOf(montoSolicitado);
        }
        else{
            //nMonto_A_Prestar = nDenominador / (1 - Comision);
            //TODO: Fix, FINAL_PREC here loses precision.
            //nMonto_A_Prestar = (nDenominador.divide(BigDecimal.valueOf(1),PREC,RoundingMode.HALF_UP)).subtract(Comision);
            nMonto_A_Prestar = (nDenominador.divide(BigDecimal.valueOf(1),FINAL_PREC,RoundingMode.HALF_UP)).subtract(Comision);
            
        }
        
        

        //Comision = Comision * nMonto_A_Prestar;
        Comision = Comision.multiply(nMonto_A_Prestar);

        //nMonto= nMonto_A_Prestar - Comision;
        nMonto= nMonto_A_Prestar.subtract(Comision);

        System.out.println("Descuento Solicitado:|"+Descuento_Solicitado);

        System.out.println("Plazo:|"+Plazo);

        System.out.println("Comision:|"+Comision);

        System.out.println("Cat Sin IVA:|"+CatSinIva + "%");

        System.out.println("Cat Con IVA:|"+Cat + "%");

        System.out.println("Monto a Prestar:|"+nMonto_A_Prestar);

        //System.out.println("Interes mensual Con IVA:|"+NumUtils.round(InteresConIva * 100, 2) + "%");
        System.out.println("Interes mensual Con IVA:|"+NumUtils.round(InteresConIva.multiply(BigDecimal.valueOf(100)), FINAL_PREC) + "%");

        //System.out.println("Interes mensual Sin IVA:|"+NumUtils.round((InteresConIva * 100) / 1.16, 2) + "%");
        System.out.println("Interes mensual Sin IVA:|"+NumUtils.round((InteresConIva.multiply(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(1.16), FINAL_PREC,RoundingMode.HALF_UP), FINAL_PREC) + "%");

        System.out.println("Periodo|Saldo|Capital|Intereses|IVA|Descuento");

        //log.log(Level.INFO,0 + "\t" + nMonto + "\t" + 0.00 + "\t" + 0.00 + "\t" + 0.00 + "\t" + 0.00);
        System.out.println(0 + "|" + nMonto + "|" + 0.00 + "|" + 0.00 + "|" + 0.00 + "|" + 0.00);
        
        LinkedList<AmortizacionPorDescuento> rowList = new LinkedList();
        AmortizacionPorDescuento amortizacionRow = new AmortizacionPorDescuento();
            amortizacionRow.setPeriodo(0);
            amortizacionRow.setSaldo(NumUtils.round(nMonto,FINAL_PREC));
            amortizacionRow.setCapital(0.00);
            amortizacionRow.setIntereses(0.00);
            amortizacionRow.setIva(0.00);
            amortizacionRow.setDescuento(0.00);
            rowList.add(amortizacionRow);
        
        return rowList;
    }
    
    public LinkedList<AmortizacionPorDescuento> getRows(LinkedList<AmortizacionPorDescuento> rowList){
        double PagoCapitalTotal=0L;
        double PagoInteresTotal=0L;
        double PagoInteresIvaTotal=0L;
        double Descuento_SolicitadoTotal=0L;
        for (double nPlazo = 1; nPlazo <= Plazo; nPlazo++) {

            //PagoInteres = nMonto * IntersSinIva;
            PagoInteres = nMonto.multiply(IntersSinIva);

            PagoInteresIva = PagoInteres.multiply(BigDecimal.valueOf(0.16));

            //PagoCapital = Descuento_Solicitado-PagoInteres-PagoInteresIva;
            PagoCapital = Descuento_Solicitado.subtract(PagoInteres).subtract(PagoInteresIva);

            nMonto = nMonto.subtract(PagoCapital);

            if(nPlazo == Plazo) {

                
               // PagoCapital = PagoCapital + nMonto;
                PagoCapital = PagoCapital.add(nMonto);

                //PagoInteres = PagoInteres - nMonto;
                PagoInteres = PagoInteres.subtract(nMonto);

                nMonto = BigDecimal.valueOf(0);

            }
            

            //log.log(Level.INFO,(int)nPlazo+"\t"+NumUtils.round(nMonto,FINAL_PREC)+"\t"+NumUtils.round(PagoCapital,FINAL_PREC)+"\t"+NumUtils.round(PagoInteres,FINAL_PREC)+"\t"+NumUtils.round(PagoInteresIva,FINAL_PREC)+"\t"+(double)NumUtils.round(Descuento_Solicitado,FINAL_PREC));
            System.out.println((int)nPlazo+"|"+NumUtils.round(nMonto,PREC)+"|"+NumUtils.round(PagoCapital,PREC)+"|"+NumUtils.round(PagoInteres,PREC)+"|"+NumUtils.round(PagoInteresIva,PREC)+"|"+(double)NumUtils.round(Descuento_Solicitado,PREC));
            AmortizacionPorDescuento amortizacionRow = new AmortizacionPorDescuento();
            amortizacionRow.setPeriodo((int)nPlazo);
            amortizacionRow.setSaldo(NumUtils.round(nMonto,FINAL_PREC));
            amortizacionRow.setCapital(NumUtils.round(PagoCapital,FINAL_PREC));PagoCapitalTotal+=NumUtils.round(PagoCapital,FINAL_PREC);
            amortizacionRow.setIntereses(NumUtils.round(PagoInteres,FINAL_PREC));PagoInteresTotal+=NumUtils.round(PagoInteres,FINAL_PREC);
            amortizacionRow.setIva(NumUtils.round(PagoInteresIva,FINAL_PREC));PagoInteresIvaTotal+=NumUtils.round(PagoInteresIva,FINAL_PREC);
            amortizacionRow.setDescuento((double)NumUtils.round(Descuento_Solicitado,FINAL_PREC));Descuento_SolicitadoTotal+=(double)NumUtils.round(Descuento_Solicitado,FINAL_PREC);
            rowList.add(amortizacionRow);
        }
        PagoCapitalTotal = NumUtils.round(PagoCapitalTotal,FINAL_PREC);
        PagoInteresTotal = NumUtils.round(PagoInteresTotal,FINAL_PREC);
        PagoInteresIvaTotal = NumUtils.round(PagoInteresIvaTotal,FINAL_PREC);
        Descuento_SolicitadoTotal = NumUtils.round(Descuento_SolicitadoTotal,FINAL_PREC);
        double compensacion=Descuento_SolicitadoTotal-(PagoCapitalTotal+PagoInteresTotal+PagoInteresIvaTotal);
        compensacion = NumUtils.round(compensacion,FINAL_PREC);
        double nInteres=rowList.getLast().getIntereses();
        nInteres = NumUtils.round(nInteres,FINAL_PREC);
        double compensatedInteres = nInteres+compensacion;
        compensatedInteres = NumUtils.round(compensatedInteres,FINAL_PREC);
        log.log(Level.INFO,">>>===>Descuento_SolicitadoTotal="+Descuento_SolicitadoTotal+"       PagoCapitalTotal="+PagoCapitalTotal+" PagoInteresTotal="+PagoInteresTotal+" PagoInteresIvaTotal="+PagoInteresIvaTotal+" ===> PagoCapitalTotal+PagoInteresTotal+PagoInteresIvaTotal="+(PagoCapitalTotal+PagoInteresTotal+PagoInteresIvaTotal));
        log.log(Level.INFO,">>>compensacion="+compensacion+" nDescuentoTotal="+Descuento_SolicitadoTotal+" PagoCapitalTotal="+PagoCapitalTotal+" PagoInteresTotal="+PagoInteresTotal+" PagoInteresIvaTotal="+PagoInteresIvaTotal);
        rowList.getLast().setIntereses(compensatedInteres);
        log.log(Level.INFO,">>>===>nInteres="+nInteres+" nInteres+compensacion="+(compensatedInteres));
        for(AmortizacionPorDescuento amortizacionRow:rowList){
            System.out.println(amortizacionRow.getPeriodo()+"|"+amortizacionRow.getSaldo()+"|"+amortizacionRow.getCapital()+"|"+amortizacionRow.getIntereses()+"|"+amortizacionRow.getIva()+"|"+amortizacionRow.getDescuento());
        }
        return rowList;
    }

    
 
    
    

}
