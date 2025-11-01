package mx.gob.imss.dpes.prestamoback.service;

import java.util.logging.Level;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.serviciosdigitales.model.SelloElectronicoResponse;
import mx.gob.imss.dpes.prestamoback.restclient.SelloElectronicoClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.rules.CadenaOriginalRule;

@Provider
public class CreateSelloElectronicoSimulacionService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion>{

  @Inject
  @RestClient
  private SelloElectronicoClient selloElectronicoClient;
  
  @Inject
  CadenaOriginalRule cadenaOriginalRule;
  
  

  @Override
  public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) {
    
    
    CadenaOriginalRule.Output cadena =
            cadenaOriginalRule.apply( cadenaOriginalRule.new Input(request.getPayload()));
    request.getPayload().getResumenSimulacion().setCadenaOriginal(cadena.request.getCadenaOriginal());
    log.log( Level.INFO, "Request hacia SelloElectronico: {0}", cadena.request );
   Response sello = selloElectronicoClient.create( cadena.request );
   if (sello.getStatus() == 200) {
      SelloElectronicoResponse selloResponse = sello.readEntity(SelloElectronicoResponse.class);
     request.getPayload().setSello(selloResponse);
   /*   SelloElectronicoResponse selloResponse =new SelloElectronicoResponse();
      selloResponse.setCadenaOriginal(cadena.toString());
      selloResponse.setNoSerie(String.valueOf(cadena.hashCode()));
     selloResponse.setId(String.valueOf(cadena.hashCode()));
           selloResponse.setSello(String.valueOf(cadena.hashCode()));
           request.getPayload().setSello(selloResponse);

*/
      return request;
    }
    return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    
  }

}
