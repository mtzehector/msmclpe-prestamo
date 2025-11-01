package mx.gob.imss.dpes.prestamoback.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.documento.model.Documento;
import mx.gob.imss.dpes.prestamoback.assembler.ResumenCartaReinstalacionAssembler;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.DocumentoClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class CreateReporteCartaReinstalacionService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

  @Inject
  @RestClient
  DocumentoClient client;
  
  @Inject
  ResumenCartaReinstalacionAssembler assembler;
    
  @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {
        Documento documento = assembler.assemble(request.getPayload());
        
        Response createResponse = client.create(documento);
        if( createResponse.getStatus() == 200 ){
          Documento persisted = createResponse.readEntity(Documento.class);
          request.getPayload().setDocumento(persisted);
          return new Message<>(request.getPayload());
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }

}
