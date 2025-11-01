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
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.prestamo.model.CapitalInsolutoRQ;
import mx.gob.imss.dpes.interfaces.prestamo.model.CapitalInsolutoRS;
import mx.gob.imss.dpes.prestamoback.entity.PrestamoRecuperacion;
import mx.gob.imss.dpes.prestamoback.model.PrestamosEnRecuperacionResponse;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.restclient.SaldoCapitalClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author edgar.arenas
 */
@Provider
public class ReadPrestamoRecuperacionService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    PrestamoRecuperacionPersistenceJpaService service;

    @Inject
    @RestClient
    private SaldoCapitalClient client;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {

        log.log(Level.INFO, ">>>prestamoBack|ReadPrestamoRecuperacionService|execute {0}", request.getPayload());

        double importeARecibir = 0d;
        double saldos = 0d;
        switch (request.getPayload().getPrestamo().getTipoCredito().getId().intValue()) {

            case 2://Renovación
            case 3://Compra cartera
            case 6://Mixto
                PrestamosEnRecuperacionResponse response = service.consultaPrestamosPorSolicitud(request.getPayload().getSolicitud().getId());

                for (PrestamoRecuperacion p : response.getPrestamosEnRecuperacion()) {
                    if (p.getCanMontoSol() != null) {
                        saldos += p.getCanMontoSol().doubleValue();
                    } else {
                        CapitalInsolutoRQ caprq = new CapitalInsolutoRQ();
                        caprq.setFolioSipre(p.getNumSolicitudSipre());
                        caprq.setNumMensualidad(p.getNumMesRecuperado().intValue());
                        caprq.setNumFolioSolicitud(p.getNumFolioSolicitud());
                        try {
                            Response loadSalCap = client.capitalinsoluto(caprq);

                            if (loadSalCap.getStatus() == 200) {
                                CapitalInsolutoRS rs = loadSalCap.readEntity(CapitalInsolutoRS.class);
                                saldos += rs.getSaldoCapital();// VALIDAR SI ESTE DATO ES EL CORRECTO O ES EL SALDO INSOLUTO 
                            }
                        } catch (Exception e) {
                            return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
                        }
                    }
                }
                importeARecibir = request.getPayload().getPrestamo().getMonto() - saldos;
                importeARecibir = Math.round(importeARecibir * 100.0)/100.0;
                request.getPayload().getPrestamo().setImporteARecibir(importeARecibir);
                log.log(Level.INFO, ">>>prestamoBack|ReadPrestamoRecuperacionService|importeARecibir {0}", importeARecibir);
                break;
            case 1://Nuevo
                request.getPayload().getPrestamo().setImporteARecibir(request.getPayload().getPrestamo().getMonto());
                log.log(Level.INFO, ">>>prestamoBack|ReadPrestamoRecuperacionService|importeARecibir {0}", request.getPayload().getPrestamo().getImporteARecibir());
                break;
            default:
                break;
                
        }
    
        return new Message<> (request.getPayload());

    }
}
