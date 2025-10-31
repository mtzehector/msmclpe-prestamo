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
import mx.gob.imss.dpes.common.enums.SexoEnum;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RecursoNoExistenteException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.EntidadFinanciera;
import mx.gob.imss.dpes.prestamoback.model.Promotor;
import mx.gob.imss.dpes.prestamoback.model.PromotorModel;
import mx.gob.imss.dpes.prestamoback.model.ReporteCartaInstruccion;
import mx.gob.imss.dpes.prestamoback.model.ReporteResumenSimulacion;
import mx.gob.imss.dpes.prestamoback.model.Sexo;
import mx.gob.imss.dpes.prestamoback.restclient.PromotorClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class ReadPromotorService extends ServiceDefinition<ReporteResumenSimulacion, ReporteResumenSimulacion> {

    @Inject
    @RestClient
    private PromotorClient service;

    @Override
    public Message<ReporteResumenSimulacion> execute(Message<ReporteResumenSimulacion> request) throws BusinessException {
        
        log.log( Level.INFO, "Request hacia Promotor: {0}", request.getPayload());
        ReporteCartaInstruccion reporte = new ReporteCartaInstruccion();
        reporte.getPromotor().setId(request.getPayload().getPrestamo().getPromotor());
        reporte.getPromotor().setCurp(request.getPayload().getPromotor().getCurp());
        reporte.getPromotor().setNss(request.getPayload().getPromotor().getNss());
           
        Response load = service.obtenerPorCURPYNSSCI(reporte.getPromotor() );
        if (load.getStatus() == 200) {
            PromotorModel promotor = load.readEntity(PromotorModel.class);
//            Promotor promotor = load.readEntity(Promotor.class);
//            PromotorModel p = new PromotorModel();
//            EntidadFinanciera ef = new EntidadFinanciera();
//            ef.setId(promotor.getEntidadFinanciera().getId());
//            ef.setNombreComercial(promotor.getEntidadFinanciera().getNombreComercial());
//            ef.setRazonSocial(promotor.getEntidadFinanciera().getRazonSocial());
//            ef.setNumTelefono(promotor.getEntidadFinanciera().getNumTelefono());
//            ef.setPaginaWeb(promotor.getEntidadFinanciera().getPaginaWeb());
//            ef.setCorreoAdminEF(promotor.getEntidadFinanciera().getCorreoAdminEF());
//            ef.setCorreoElectronico(promotor.getEntidadFinanciera().getCorreoElectronico());
//            ef.setCatPromedio(promotor.getEntidadFinanciera().getCatPromedio());
//            ef.setIdSipre(promotor.getEntidadFinanciera().getIdSipre());
//            
//            p.setEntidadFinanciera(ef);
//            p.setNumEmpleado(promotor.getNumEmpleado());
//            p.setNss(promotor.getNss());
//            p.setCveEntidadFinanciera(promotor.getCveEntidadFinanciera());
//            p.setCurp(promotor.getCurp());
//            p.setNombre(promotor.getNombre());
//            p.setPrimerApellido(promotor.getPrimerApellido());
//            p.setSegundoApellido(promotor.getSegundoApellido());
//            p.setCorreoElectronico(promotor.getCorreoElectronico());
//            p.setTelefono(promotor.getTelefono());
//            p.setFechaNacimiento(promotor.getFechaNacimiento());
//            Sexo s = new Sexo();
//            s.setDescripcion(promotor.getSexo().getDescripcion());
//            s.setIdSexo(promotor.getSexo().getIdSexo());
//            if(promotor.getSexo().getIdSexo() == 1){
//                p.setSexo(SexoEnum.MASCULINO); 
//            }else if (promotor.getSexo().getIdSexo() == 2){
//                p.setSexo(SexoEnum.FEMENINO);
//            }else if(promotor.getSexo().getIdSexo() == 3){
//                p.setSexo(SexoEnum.AMBOS);
//            }
            
            
            
            request.getPayload().setPromotor(promotor);
            // Incluier el assembler
            return new Message<>(request.getPayload());
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new RecursoNoExistenteException(), null);
    }

}
