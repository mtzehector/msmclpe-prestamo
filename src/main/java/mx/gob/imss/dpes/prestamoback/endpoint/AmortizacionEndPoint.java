/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.endpoint;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.AmortizacionFieldNotExistException;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionInsumos;
import mx.gob.imss.dpes.interfaces.prestamo.model.AmortizacionPorDescuento;
import mx.gob.imss.dpes.prestamoback.entity.AmortizacionDatosBase;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoInsumoTaException;
import mx.gob.imss.dpes.prestamoback.service.AmortizacionDatosBasePersistenceService;
import mx.gob.imss.dpes.prestamoback.service.CalcularTblAmortizacionService;
import mx.gob.imss.dpes.prestamoback.service.CalcularTblAmortizacionDupService;
import mx.gob.imss.dpes.prestamoback.service.SolicitudService;
import mx.gob.imss.dpes.support.util.MontoUtils;


/**
 *
 * @author edgar.arenas
 */

@Path("/amortizacion")
@ApplicationScoped
public class AmortizacionEndPoint extends BaseGUIEndPoint<AmortizacionDatosBase, AmortizacionDatosBase, AmortizacionDatosBase> {

    @Inject
    AmortizacionDatosBasePersistenceService service;

    @Inject
    CalcularTblAmortizacionService tblService;
    
    @Inject
    CalcularTblAmortizacionDupService tblDupService;
    
    @Inject
    SolicitudService solicitudService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(AmortizacionDatosBase prestamo) throws BusinessException {
        log.log(Level.INFO, "Creando prestamo {0}", prestamo);
        Message<AmortizacionDatosBase> execute
                = service.execute(new Message<>(prestamo));
        return toResponse(execute);
    }
    
    @Path("/registrarInsumosBase")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarDatosBaseAmortizacion(AmortizacionDatosBase source) throws BusinessException {
        log.log(Level.INFO, ">>>guardarDatosBaseAmortizacion BACK {0}", source);
        Message<AmortizacionDatosBase> execute
                = service.execute(new Message<>(source));
        Message<LinkedList<AmortizacionPorDescuento>> amortizacionTablaList = tblService.execute(execute);
        return toResponse(execute);
    }
    
    @Path("/registrarInsumosBaseDup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarDatosBaseAmortizacionDup(AmortizacionDatosBase source) throws BusinessException {
        log.log(Level.INFO, ">>>guardarDatosBaseAmortizacion BACK {0}", source);
        Message<AmortizacionDatosBase> execute
                = service.execute(new Message<>(source));
        Message<LinkedList<AmortizacionPorDescuento>> amortizacionTablaList = tblDupService.execute(execute);
        return toResponse(execute);
    }

    @Path("/cveSolicitud")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTablaAmortizacionByCveSolicitud(AmortizacionDatosBase source) {
        try {
            //log.log(Level.INFO, ">>>prestamoBACK AmortizacionEndPoint getTablaAmortizacionByCveSolicitud source= {0}", source);
            if (source.getCveSolicitud() == null)
                throw new AmortizacionFieldNotExistException(
                    AmortizacionFieldNotExistException.CVE_SOLICITUD_NOT_GIVEN);

            AmortizacionDatosBase amortizacionDatosBase =
                service.getAmortizacionDatosBaseByCveId(source.getCveSolicitud());

            if (amortizacionDatosBase == null)
                throw new AmortizacionFieldNotExistException(AmortizacionFieldNotExistException.CVE_SOLICITUD);

            //log.log(Level.INFO, ">>>prestamoBACK AmortizacionEndPoint getTablaAmortizacionByCveSolicitud amortizacionDatosBase.getId= {0}", amortizacionDatosBase.getId());
            LinkedList<AmortizacionPorDescuento> amortizacionTablaList =
                tblService.getAmortizacionTablaList(amortizacionDatosBase);

            return toResponse(new Message<>(amortizacionTablaList));
        } catch (BusinessException e){
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e){
            log.log(Level.SEVERE, "ERROR AmortizacionEndPoint.getTablaAmortizacionByCveSolicitud - [" + source
                + "]", e);

            return toResponse(new Message(
                    null,
                    ServiceStatusEnum.EXCEPCION,
                    new PrestamoInsumoTaException(PrestamoInsumoTaException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                    null
            ));
        }
    }

    @Path("/folioSipre")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTablaAmortizacionByFolioSipre(AmortizacionDatosBase source) throws BusinessException {
        if(source.getFolioSipre()==null){
            throw new AmortizacionFieldNotExistException(AmortizacionFieldNotExistException.FOLIO_SIPRE_NOT_GIVEN);
        }
        log.log(Level.INFO, ">>>prestamoBACK AmortizacionEndPoint getTablaAmortizacionByFolioSipre source= {0}", source);
        AmortizacionDatosBase amortizacionDatosBase = service.getAmortizacionDatosBaseByFolioSipre(source.getFolioSipre());
        if(amortizacionDatosBase==null){
            throw new AmortizacionFieldNotExistException(AmortizacionFieldNotExistException.FOLIO_SIPRE);
        }
        log.log(Level.INFO, ">>>prestamoBACK AmortizacionEndPoint getTablaAmortizacionByFolioSipre amortizacionDatosBase.getId= {0}", amortizacionDatosBase.getId());
        LinkedList<AmortizacionPorDescuento> amortizacionTablaList = tblService.getAmortizacionTablaList(amortizacionDatosBase);

        return toResponse(new Message<>(amortizacionTablaList));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/capitalinsoluto")
    public Response getCapitalInsoluto(AmortizacionInsumos request) throws BusinessException{
       if(request.getFolioSipre()==null){
            throw new AmortizacionFieldNotExistException(AmortizacionFieldNotExistException.FOLIO_SIPRE_NOT_GIVEN);
        }
       
       AmortizacionDatosBase amortizacionDatosBase;
       
       if(request.getNumFolioSolicitud() != null && !request.getNumFolioSolicitud().isEmpty()){
          log.log(Level.INFO,"Busqueda por folio SIPRE 2.0");
          Long solicitud =  solicitudService.getSolicitudbyFolio(request.getNumFolioSolicitud());
          amortizacionDatosBase = service.getAmortizacionDatosBaseByCveId(solicitud);
       }else{
           log.log(Level.INFO,"Busqueda por folio SIPRE 1");
           amortizacionDatosBase = service.getAmortizacionDatosBaseByFolioSipre(request.getFolioSipre());
       }
       
       
       log.log(Level.INFO,">>>>/tblamortizacion/capitalinsoluto CalculoAmortizacionEndPoint numFolioSolicitud= {0}", request.getNumFolioSolicitud());
       log.log(Level.INFO,">>>>/tblamortizacion/capitalinsoluto CalculoAmortizacionEndPoint folioSipre= {0}", request.getFolioSipre());
       log.log(Level.INFO,">>>>/tblamortizacion/capitalinsoluto CalculoAmortizacionEndPoint numMensualidad= {0}", request.getNumMensualidad());
       
       AmortizacionInsumos sc = new AmortizacionInsumos();
       
        if (amortizacionDatosBase == null)
            sc.setSaldoCapital(0.0);
        else
            sc.setSaldoCapital(
                tblService.getCapitalInsoluto(amortizacionDatosBase,request.getNumMensualidad()));

        log.log(Level.INFO,">>>>saldo capital Back {0}", sc.getSaldoCapital());
        return toResponse(new Message(sc)); 
    } 
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cat")
    public Response obtenerCatPromotor(AmortizacionDatosBase source) throws BusinessException {
        log.log(Level.INFO, ">>>prestamoBack|AmortizacionEndPoint|obtenerCatPromotor {0}", source);
        if(source.getCveSolicitud()==null){
            throw new AmortizacionFieldNotExistException(AmortizacionFieldNotExistException.CVE_SOLICITUD_NOT_GIVEN);
        }
        AmortizacionDatosBase amortizacionDatosBase = service.getAmortizacionDatosBaseByCveId(source.getCveSolicitud());
        if(amortizacionDatosBase==null){
            throw new AmortizacionFieldNotExistException(AmortizacionFieldNotExistException.CVE_SOLICITUD);
        }
        log.log(Level.INFO, ">>>prestamoBack|AmortizacionEndPoint|obtenerCatPromotor {0}", amortizacionDatosBase.getCat());
        return toResponse(new Message<>(amortizacionDatosBase));
    }
    

}
