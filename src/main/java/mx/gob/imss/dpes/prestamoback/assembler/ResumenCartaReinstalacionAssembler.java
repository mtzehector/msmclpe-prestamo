package mx.gob.imss.dpes.prestamoback.assembler;

import mx.gob.imss.dpes.baseback.assembler.BaseAssembler;
import mx.gob.imss.dpes.common.enums.TipoDocumentoEnum;
import mx.gob.imss.dpes.interfaces.documento.model.Documento;
import mx.gob.imss.dpes.interfaces.prestamo.model.ResumenCartaInstruccion;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;

import javax.ws.rs.ext.Provider;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;

@Provider
public class ResumenCartaReinstalacionAssembler
 extends BaseAssembler<ReporteResumenSimulacion, Documento, Long, Long>{

  @Override
  public ReporteResumenSimulacion toEntity(Documento source) {
    return new ReporteResumenSimulacion();
  }

  @Override
  public Long toPKEntity(Long source) {
    return source;
  }

  @Override
  public Documento assemble(ReporteResumenSimulacion source) {
    
    //log.log( Level.INFO, "ASSEMBLER 0.1 S source: \n {0} \n", source);
      
    ResumenCartaInstruccion resumen = source.getResumenCartaInstruccion();
    //log.log( Level.INFO, "ASSEMBLER 1S: {0}", resumen);
    if(resumen.getCiudad() == null) resumen.setCiudad("");
    resumen.setFecha(new SimpleDateFormat("dd' de 'MMMM' de 'yyyy", Locale.forLanguageTag("es-ES"))
        .format(source.getSolicitud().getAltaRegistro()));
    resumen.setCiudad(source.getResumenSimulacion().getCiudad());
    resumen.setFolio(source.getSolicitud().getNumFolioSolicitud());
    resumen.setNombre(source.getPersona().getNombre());
    resumen.setPrimerApe(source.getPersona().getPrimerApellido());
    resumen.setSegundoApe(source.getPersona().getSegundoApellido());
    resumen.setCurp(source.getSolicitud().getCurp());
    resumen.setNss(source.getSolicitud().getNss());
    //log.log( Level.INFO, "ASSEMBLER 2S: {0}", resumen);
    resumen.setDelegacion(source.getSolicitud().getDelegacion());
    resumen.setEmail(source.getPersona().getCorreoElectronico());
    resumen.setTelefono(source.getPersona().getTelefono());    
    resumen.setTipoCredito( source.getPrestamo().getTipoCredito()!=null?source.getPrestamo().getTipoCredito().getDescripcion():"" );
    resumen.setTipoTrabajador(source.getSolicitud().getRefTrabajador().substring(0,1).toUpperCase()
      + source.getSolicitud().getRefTrabajador().substring(1));
    resumen.setNombreComercial(source.getOferta().getEntidadFinanciera().getNombreComercial());
    resumen.setRazonSocial(source.getOferta().getEntidadFinanciera().getRazonSocial());
    //log.log( Level.INFO, "ASSEMBLER 3S: {0}", resumen);
    resumen.setTelefonoBanco(source.getOferta().getEntidadFinanciera().getNumTelefono());
    resumen.setWebBanco(source.getOferta().getEntidadFinanciera().getPaginaWeb());
    resumen.setCorreoElectronico(source.getPromotor().getCorreoElectronico());
    resumen.setTasaAnual(source.getOferta().getTasaAnual().toString());
    resumen.setCat(source.getCatPrestamoPromotor() != null ? source.getCatPrestamoPromotor(): source.getOferta().getCat().toString());
    resumen.setMontoSolicitado(source.getPrestamo().getMonto().toString());
    resumen.setImporteDescNomina(source.getPrestamo().getImpDescNomina().toString());
    resumen.setTotalDescAplicar(source.getOferta().getPlazo().getDescripcion());
    resumen.setPlazo(source.getOferta().getPlazo().getDescripcion());
    //log.log( Level.INFO, "ASSEMBLER 4S: {0}", resumen);
    resumen.setTotalCredPagarInt(source.getPrestamo().getImpTotalPagar().toString());
    resumen.setTipoPension(source.getResumenSimulacion().getTipoPension());
    resumen.setNominaPrimerDesc(new SimpleDateFormat("dd/MM/yyyy")
      .format(source.getPrestamo().getPrimerDescuento()));
    resumen.setFechaVigFolio(new SimpleDateFormat("dd/MM/yyyy")
      .format(source.getSolicitud().getFecVigenciaFolio()));
    //log.log( Level.INFO, "ASSEMBLER 5S: {0}", resumen);
    resumen.setCodigoQR( source.getSolicitud().getNumFolioSolicitud() );
    resumen.setSelloDigital( source.getSello().getSello() );
    resumen.setNumEmpleado("");
    resumen.setNombreP("");
    resumen.setPrimerApellido("");
    resumen.setSegundoApellido("");
    resumen.setCurpPromotor("");
    resumen.setImporteARecibir(source.getPrestamo().getImporteARecibir().toString());
    resumen.setOrigen("Simulacion");
    //log.log( Level.INFO, "ASSEMBLER 6S: {0}", resumen);
    resumen.setFolioPL("");
    resumen.setMontoSolPL("");
    resumen.setDescMensPL("");
    resumen.setMontoLiquidarPL("");
    resumen.setPlazoPL("");
    resumen.setCatPL("");
    resumen.setRazonSocialPL("");
    resumen.setPeriodoNominaDelPrestamo(source.getPeriodoNominaDelPrestamo());
    resumen.setPeriodoNominaPosteriorAlPrestamo(source.getPeriodoNominaPosteriorAlPrestamo());
    resumen.setNumMesesConsecutivo(source.getPrestamo().getNumMesesConsecutivos());

    source.getSello().setCadenaOriginal(resumen.getCadenaOriginal());
    //log.log( Level.INFO, "ASSEMBLER 7S: {0}", resumen);
    Documento documento = new Documento();
    documento.setCveSolicitud( source.getSolicitud().getId() );
    documento.setTipoDocumento(TipoDocumentoEnum.CARTA_REINSTALACION);
    documento.setRefSello(source.getSello().toString());
    documento.setRefDocumento( source.getResumenCartaInstruccion().toString() );
    //log.log( Level.INFO, "ASSEMBLER 8S: {0}", documento);
    return documento;
  }

  @Override
  public Long assemblePK(Long source) {
    return source;
  }
}
