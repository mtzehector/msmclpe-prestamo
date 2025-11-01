/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.enums.TipoDocumentoEnum;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.documento.model.Documento;
import mx.gob.imss.dpes.prestamoback.exception.ConsultarDocumentosException;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.DocumentoClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author salvador.pocteco
 */
@Provider
public class ConsultarDocumentosService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    @RestClient
    private DocumentoClient documentoClient;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {
        try{
            Documento documentoRequest = new Documento();
            documentoRequest.setCveSolicitud(request.getPayload().getSolicitud().getId());
            documentoRequest.setTipoDocumento(TipoDocumentoEnum.RESUMEN_SIMULACION);
            Response respuesta = documentoClient.bySolicitudAndTipo(documentoRequest);
            if (respuesta != null && respuesta.getStatus() == 200) {
                List<Documento> documentos = respuesta.readEntity(new GenericType<List<Documento>>() {});
                for (Documento documento : documentos) {
                    request.getPayload().setDocumento(documento);
                }
                return request;
            }
        }catch(Exception e){
            log.log(Level.SEVERE, "Ocurrio un error al intentar obtener el tipo documento", e);
        }

        return response(null, ServiceStatusEnum.EXCEPCION, new ConsultarDocumentosException(), null);
    }

}
