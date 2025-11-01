/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.documento.model.Documento;
import mx.gob.imss.dpes.prestamoback.assembler.ResumenCartaInstruccionAssembler;
import mx.gob.imss.dpes.prestamoback.model.ReporteCartaInstruccion;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.DocumentoClient;


/**
 *
 * @author osiris.hernandez
 */
@Provider
public class CreateReporteCartaInstruccionService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

  @Inject
  @RestClient
  DocumentoClient client;
  
  @Inject
  ResumenCartaInstruccionAssembler assembler;
    
  @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {
        
        log.log( Level.INFO, "Armando el reporte: {0}", request.getPayload());
        
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
