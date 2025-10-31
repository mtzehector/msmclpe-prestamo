/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.service;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.prestamo.model.Prestamo;
import mx.gob.imss.dpes.prestamoback.model.DiaInhabilRequest;
import mx.gob.imss.dpes.prestamoback.model.FechasDocumentacionRS;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.PrestamoClient;
import mx.gob.imss.dpes.prestamoback.restclient.SolicitudBackClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author antonio
 */
@Provider
public class ReadPrestamoService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    @RestClient
    private PrestamoClient service;

    @Inject
    @RestClient
    private SolicitudBackClient solicitudClient;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {

        log.log(Level.INFO, "Request hacia PrestamoBack: {0}", request.getPayload());
        Response load = service.load(request.getPayload().getSolicitud().getId());
        if (load.getStatus() == 200) {
            Prestamo prestamo = load.readEntity(Prestamo.class);
            if (request.getPayload().getPrestamo() != null) {
                prestamo.setNumMesesConsecutivos(request.getPayload().getPrestamo().getNumMesesConsecutivos() == null? 0 : request.getPayload().getPrestamo().getNumMesesConsecutivos());
            }
            prestamo.setImporteARecibir(0d);
            request.getPayload().setPrestamo(prestamo);
            // Incluier el assembler

            DiaInhabilRequest dhr = new DiaInhabilRequest();
            dhr.setFecInicio(prestamo.getAltaRegistro());
            log.log(Level.INFO, "Response hacia PrestamoBack DiaInhabilRequest: {0}", dhr);
            Response fechasR = solicitudClient.fechasParaDocumento(dhr);

            FechasDocumentacionRS fechasDocumentacionRS = fechasR.readEntity(FechasDocumentacionRS.class);
            log.log(Level.INFO, "Request JGV fechasDocumentacionRS getPeriodoNominaDelPrestamo: {0}", fechasDocumentacionRS.getPeriodoNominaDelPrestamo());
            log.log(Level.INFO, "Request JGV fechasDocumentacionRS getPeriodoNominaPosteriorAlPrestamo: {0}", fechasDocumentacionRS.getPeriodoNominaPosteriorAlPrestamo());

            request.getPayload().setPeriodoNominaDelPrestamo(
                    new SimpleDateFormat("dd/MM/yyyy")
                            .format(fechasDocumentacionRS.getPeriodoNominaDelPrestamo().getFecFin())
            );
            request.getPayload().setPeriodoNominaPosteriorAlPrestamo(
                    new SimpleDateFormat("dd/MM/yyyy")
                            .format(fechasDocumentacionRS.getPeriodoNominaPosteriorAlPrestamo().getFecDescuento())
            );

            return new Message<>(request.getPayload());
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }

}
