///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package mx.gob.imss.dpes.prestamoback.consumer;
//
//import java.util.logging.Level;
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.MessageDriven;
//import javax.inject.Inject;
//import mx.gob.imss.dpes.common.consumer.BaseConsumer;
//import mx.gob.imss.dpes.common.enums.EventEnum;
//import mx.gob.imss.dpes.common.exception.BusinessException;
//import mx.gob.imss.dpes.common.model.Message;
//import mx.gob.imss.dpes.common.service.ServiceDefinition;
//import mx.gob.imss.dpes.interfaces.evento.model.Evento;
//import mx.gob.imss.dpes.prestamoback.model.ReporteCartaInstruccion;
//import mx.gob.imss.dpes.prestamoback.service.CreateEventoService;
//import mx.gob.imss.dpes.prestamoback.service.CreateReporteCartaInstruccionService;
//import mx.gob.imss.dpes.prestamoback.service.CreateSelloElectronicoCartaInstruccionService;
//import mx.gob.imss.dpes.prestamoback.service.ReadOfertaService;
//import mx.gob.imss.dpes.prestamoback.service.ReadPensionadoService;
//import mx.gob.imss.dpes.prestamoback.service.ReadPersonaService;
//import mx.gob.imss.dpes.prestamoback.service.ReadPrestamoService;
//import mx.gob.imss.dpes.prestamoback.service.ReadPromotorService;
//import mx.gob.imss.dpes.prestamoback.service.ReadSolicitudService;
//
///**
// *
// * @author osiris.hernandez
// */
//@MessageDriven(name = "ReporteCartaInstruccionConsumer", activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "topic/MCLPETopic"),
//        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
//        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
//
//public class ReporteCartaInstruccionConsumer extends BaseConsumer {
//  
//  @Inject ReadSolicitudService readSolicitudService;
//  @Inject ReadPrestamoService readPrestamoService;
//  @Inject ReadOfertaService readOfertaService;
//  @Inject ReadPersonaService readPersonaService;
//  @Inject ReadPensionadoService readPensionadoService;
//  @Inject CreateSelloElectronicoCartaInstruccionService createSelloElectronicoCartaInstruccionService;
//  @Inject CreateReporteCartaInstruccionService createReporteCartaInstruccionService;
//  @Inject ReadPromotorService readPromotorService;
//  @Inject CreateEventoService createEventoService;
//
//
//  
//
//  @Override
//  protected void proccess(Message message) {
//    ServiceDefinition[] steps = { 
//      readSolicitudService, 
//      readPrestamoService,
//      readOfertaService,
//      readPersonaService,
//      readPensionadoService,
//      readPromotorService,
//      //readPrestamoLiquidar
//      createSelloElectronicoCartaInstruccionService,
//      createReporteCartaInstruccionService
//    };
//    
//    //if( EventEnum.CREAR_CARTA_INSTRUCCION.equals( message.getHeader().getEvent()) ){
//          
//      try {
//        log.log(Level.INFO, "Consumir evento de creación de solicitud {0}", message );
//        ReporteCartaInstruccion reporteCartaInstruccion = new ReporteCartaInstruccion();
//        reporteCartaInstruccion.getSolicitud().setId( (Long) message.getPayload() );
//        Message<ReporteCartaInstruccion> response =
//                createReporteCartaInstruccionService.executeSteps(steps, new Message<>( reporteCartaInstruccion) );
//        
//        if( !Message.isException(response) ){
//          
//          Evento evento = new Evento();
//          evento.setId(reporteCartaInstruccion.getSolicitud().getId());
//          evento.setEvent(EventEnum.CREAR_REPORTE_CARTA_INSTRUCCION);
//          createEventoService.execute( new Message<>(evento));
//        }
//        
//        log.log(Level.INFO, "Se termino de atender el evento de creación de solicitud {0}", message );
//      } catch (BusinessException ex) {
//        log.log(Level.SEVERE, null, ex);
//      }
//        //}
//  }
//}
//
