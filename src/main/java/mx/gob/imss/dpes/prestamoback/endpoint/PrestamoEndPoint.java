/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.endpoint;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.serviciosdigitales.model.Persona;
import mx.gob.imss.dpes.prestamoback.assembler.PrestamoAssembler;
import mx.gob.imss.dpes.prestamoback.entity.Prestamo;
import mx.gob.imss.dpes.prestamoback.entity.PrestamoRecuperacion;
import mx.gob.imss.dpes.prestamoback.exception.PrestamoException;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.model.RequestCreacionCartaInstruccion;
import mx.gob.imss.dpes.prestamoback.service.*;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;

/**
 * @author antonio
 */
@ApplicationScoped
@Path("/prestamo")
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class PrestamoEndPoint extends BaseGUIEndPoint<Prestamo, Prestamo, Prestamo> {

    @Inject
    private PrestamoPersistenceService service;

    @Inject
    private PrestamoRecuperacionPersistenceService prestamoRecuperacionService;

    @Inject
    private PrestamoRecuperacionPersistenceJpaService prestamoRecuperacionServiceJpa;

    @Inject
    private ReadSolicitudService readSolicitudService;
    
    @Inject
    private ReadPrestamoService readPrestamoService;
    
    @Inject
    private ReadOfertaService readOfertaService;
    
    @Inject
    private ReadPersonaBdtuService readPersonaService;
    
    @Inject
    private ReadPensionadoService readPensionadoService;
    
    @Inject
    private CreateSelloElectronicoCartaInstruccionService createSelloElectronicoCartaInstruccionService;
    
    @Inject
    private CreateReporteCartaInstruccionService createReporteCartaInstruccionService;
    
    @Inject
    private ReadPromotorService readPromotorService;
    
    @Inject
    private CreateEventoService createEventoService;
    
    @Inject
    private ReadPrestamoRecuperacionService readPrestamosRecuperacion;
    
    //@Inject
    //private ConsultarDocumentosService consultarDocumentosService;
    
    @Inject
    private ConsultarOfertaService consultarOfertaService;
    
    @Inject
    private ConsultaDelegacionService readDelegacionService;

    @Inject
    private PrestamoService prestamoService;
    @Inject
    private PrestamoAssembler prestamoAssembler;

    @Inject
    private CreateReporteCartaReinstalacionService createReporteCartaReinstalacionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(Prestamo prestamo) {
        try {
            //log.log(Level.INFO, "Creando prestamo {0}", prestamo);
            //GUARDA EN LA TABLA MCLT_PRESTAMO
            Message<Prestamo> execute = service.execute(new Message<>(prestamo));
            return toResponse(execute);
        }  catch(BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch(Exception e) {
            log.log(Level.SEVERE, "ERROR PrestamoEndPoint.create - [" + prestamo + "]", e);
        }

        return toResponse(new Message(
            null,
            ServiceStatusEnum.EXCEPCION,
            new PrestamoException(PrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
            null
        ));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFolioNumero(Prestamo prestamo) {
        try {
            if (prestamo.getId() != null) {

                Prestamo sEntity = service.findOne(prestamo.getId());

                Message<Prestamo> execute
                        = service.execute(new Message<>(sEntity));
                return toResponse(execute);
            }
            return Response.notModified().build();
        } catch(BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch(Exception e) {
            log.log(Level.SEVERE, "ERROR PrestamoEndPoint.updateFolioNumero - [" + prestamo + "]", e);
        }

        return toResponse(new Message(
            null,
            ServiceStatusEnum.EXCEPCION,
            new PrestamoException(PrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
            null
        ));
    }

    @GET
    @Path("/{solicitud}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrestamo(@PathParam("solicitud") Long solicitud) throws BusinessException {
        log.log(Level.INFO, "Creando solicitud {0}", solicitud);

        Prestamo prestamo = new Prestamo();
        prestamo.setSolicitud(solicitud);
        Message<Prestamo> execute = service.consultaPrestamoPorSolicitud(new Message<>(prestamo));
        log.log(Level.INFO, "Creando Prestamo {0}", execute.getPayload());

        return toResponse(execute);
    }

    @POST
    @Path("/crear/cartainstruccion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCartaInstruccion(RequestCreacionCartaInstruccion request) throws BusinessException {

        ServiceDefinition[] steps = {
            readSolicitudService,
            readDelegacionService,
            readPrestamoService,
            //readOfertaService,
            //consultarDocumentosService,
            consultarOfertaService,
            //readPersonaService,
            readPensionadoService,
            readPromotorService,
            readPrestamosRecuperacion,
            createSelloElectronicoCartaInstruccionService,
            createReporteCartaInstruccionService
        };

        try {

            ReporteResumenSimulacion reporteResumen = new ReporteResumenSimulacion();
            reporteResumen.setCatPrestamoPromotor(request.getPrestamo().getCatPrestamoPromotor());
            reporteResumen.getSolicitud().setId(request.getPrestamo().getSolicitud());
            reporteResumen.getPromotor().setNss(request.getPersonaEf().getNss());
            reporteResumen.getPromotor().setCurp(request.getPersonaEf().getCurp());
            reporteResumen.setFlatPrestamoPromotor(request.getFlatPrestamoPromotor());
            if (reporteResumen.getFlatPrestamoPromotor() == 1) {
                Persona p = new Persona();
                p.setCorreoElectronico(request.getPensionado().getCorreoElectronico());
                p.setTelefono(request.getPensionado().getTelefono());
                reporteResumen.setPersona(p);
            }
            log.log(Level.INFO, "BACK CREANDO CARTA Libranza {0}", reporteResumen);

            Message<ReporteResumenSimulacion> response
                    = readSolicitudService.executeSteps(steps, new Message<>(reporteResumen));

            log.log(Level.INFO, "Se termino de atender el evento de creación de carta libranza {0}");

            return toResponse(response);

        } catch(BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch(Exception e) {
            log.log(Level.SEVERE, "ERROR PrestamoEndPoint.createCartaInstruccion - [" + request + "]", e);
        }

        return toResponse(new Message(
            null,
            ServiceStatusEnum.EXCEPCION,
            new PrestamoException(PrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
            null
        ));
    }

    @Path("/prestamoEnRecuperacion")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPrestamoRecuperacion(PrestamoRecuperacion prestamo) {
        try {
            //log.log(Level.INFO, "Creando prestamo {0}", prestamo);
            Message<PrestamoRecuperacion> execute = prestamoRecuperacionService.execute(new Message<>(prestamo));
            //GUARDA LOS PRESTAMOS EN LA TABLA 'MCLT_PRESTAMO_RECUPERACION'
            return toResponse(execute);
        } catch(BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch(Exception e) {
            log.log(Level.SEVERE, "ERROR PrestamoEndPoint.createPrestamoRecuperacion - [" + prestamo + "]", e);
        }
        return toResponse(new Message(
            null,
            ServiceStatusEnum.EXCEPCION,
            new PrestamoException(PrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
            null
        ));
    }

    @Path("/recuperacion")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamoPorSolicitud(PrestamoRecuperacion prestamo) throws BusinessException {
        log.log(Level.INFO, "Obteniendo prestamo recuperacion {0}", prestamo);
        Message<PrestamoRecuperacion> execute
                = prestamoRecuperacionService.consultaPrestamoPorSolicitud(new Message<>(prestamo));
        return toResponse(execute);
    }

    @Path("/prestamosEnRecuperacion/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosPorSolicitud(@PathParam("id") Long id) throws BusinessException {
        log.log(Level.INFO, "Obteniendo prestamos recuperacion {0}", id);
        return Response.ok(prestamoRecuperacionServiceJpa.consultaPrestamosPorSolicitud(id)).build();
    }

    @Path("/prestamosEnRecuperacion/{id}/{idEntidadF}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosPorSolicitudYEntidad(@PathParam("id") Long id, @PathParam("idEntidadF") Long idEntidadF) throws BusinessException {
        log.log(Level.INFO, "Obteniendo prestamos recuperacion {0}", id);
        return Response.ok(prestamoRecuperacionServiceJpa.consultaPrestamosPorSolicitud(id)).build();
    }

    @Path("/prestamosMontoLiquidar/{idSolicitud}/{idEntidadFSipre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosMontoLiquidar(@PathParam("idSolicitud") Long idSolicitud, @PathParam("idEntidadFSipre") String idEntidadFSipre) throws BusinessException {
        log.log(Level.INFO, ">>>prestamoBack|PrestamoEndPoint|consultaPrestamosMontoLiquidar {0}", idSolicitud + " " + idEntidadFSipre);
        return Response.ok(prestamoRecuperacionServiceJpa.consultaPrestamosMontoLiquidar(idSolicitud, idEntidadFSipre)).build();
    }

    @Path("/prestamoEnRecuperacion/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultaPrestamosPorId(@PathParam("id") Long id) throws BusinessException {
        log.log(Level.INFO, "Obteniendo prestamos recuperacion {0}", id);
        return Response.ok(prestamoRecuperacionService.findOne(id)).build();
    }

    @Path("/obtenerListaPrestamos")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerListaPrestamosPorIdSolicitud(List<Long> idSolicitudList) throws BusinessException{
        if (idSolicitudList == null || idSolicitudList.isEmpty())
            return toResponse(
                    new Message(
                            null,
                            ServiceStatusEnum.EXCEPCION,
                            new PrestamoException(PrestamoException.MENSAJE_DE_SOLICITUD_INCORRECTO),
                            null
                    ));

        try {
            List<Prestamo> prestamosList = prestamoService.obtenerListaPrestamosPorIdSolicitud(idSolicitudList);
            if (prestamosList == null || prestamosList.isEmpty()){
                return Response.noContent().build();
            }
            return toResponse(new Message(
                (Serializable) prestamoAssembler.assembleList(prestamosList)
            ));
        } catch (BusinessException e){
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e){
            log.log(Level.SEVERE, "ERROR PrestamoEndPoint.obtenerListaPrestamosPorIdSolicitud - [" +
                idSolicitudList + "]", e);
            return toResponse(new Message(
                null,
                ServiceStatusEnum.EXCEPCION,
                new PrestamoException(PrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                null
            ));
        }
    }

    @POST
    @Path("/crear/cartaReinstalacion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCartaIReinstalacion(RequestCreacionCartaInstruccion request) {
        try {
            ServiceDefinition[] steps = {
                readSolicitudService,
                readDelegacionService,
                readPrestamoService,
                consultarOfertaService,
                readPensionadoService,
                createSelloElectronicoCartaInstruccionService,
                createReporteCartaReinstalacionService
            };

            ReporteResumenSimulacion reporteResumen = new ReporteResumenSimulacion();
            reporteResumen.setPrestamo(request.getPrestamo());
            reporteResumen.setCatPrestamoPromotor(request.getPrestamo().getCatPrestamoPromotor());
            reporteResumen.getSolicitud().setId(request.getPrestamo().getSolicitud());
            reporteResumen.getPromotor().setNss(request.getPersonaEf().getNss());
            reporteResumen.getPromotor().setCorreoElectronico(request.getPersonaEf().getCorreoElectronico());
            reporteResumen.setFlatPrestamoPromotor(request.getFlatPrestamoPromotor());
            if (reporteResumen.getFlatPrestamoPromotor() == 1) {
                Persona p = new Persona();
                p.setCorreoElectronico(request.getPensionado().getCorreoElectronico());
                p.setTelefono(request.getPensionado().getTelefono());
                reporteResumen.setPersona(p);
            }

            Message<ReporteResumenSimulacion> response
                    = readSolicitudService.executeSteps(steps, new Message<>(reporteResumen));

            return toResponse(response);

        } catch(BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch(Exception e) {
            log.log(Level.SEVERE, "ERROR PrestamoEndPoint.createCartaIReinstalacion - [" + request + "]", e);
        }

        return toResponse(
            new Message(
                null,
                ServiceStatusEnum.EXCEPCION,
                new PrestamoException(PrestamoException.ERROR_DESCONOCIDO_EN_EL_SERVICIO),
                null
            )
        );
    }

}
