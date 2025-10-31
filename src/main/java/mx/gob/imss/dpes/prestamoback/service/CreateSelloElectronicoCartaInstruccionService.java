/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.interfaces.serviciosdigitales.model.SelloElectronicoResponse;
import mx.gob.imss.dpes.prestamoback.model.ReporteCartaInstruccion;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.SelloElectronicoClient;
import mx.gob.imss.dpes.prestamoback.rules.CadenaOriginalCartaInstruccionRule;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class CreateSelloElectronicoCartaInstruccionService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion>{

  @Inject
  @RestClient
  private SelloElectronicoClient selloElectronicoClient;
  
  @Inject
  CadenaOriginalCartaInstruccionRule cadenaOriginalCartaInstruccionRule;
  
  

  @Override
  public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) {
    log.log( Level.INFO, ">>>>INICIA CREACION DE  SelloElectronico: {0}" );
   
    CadenaOriginalCartaInstruccionRule.Output cadena =
            cadenaOriginalCartaInstruccionRule.apply( cadenaOriginalCartaInstruccionRule.new Input(request.getPayload()));
    request.getPayload().getResumenCartaInstruccion().setCadenaOriginal(cadena.getRequest().getCadenaOriginal());
    log.log( Level.INFO, ">>>>CADENA SelloElectronico: {0}", cadena.getRequest() );
    Response sello = selloElectronicoClient.create( cadena.getRequest() );
    if (sello.getStatus() == 200) {
      SelloElectronicoResponse selloResponse = sello.readEntity(SelloElectronicoResponse.class);
      request.getPayload().setSello(selloResponse);
      return request;
    }
    return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    
  }

}
