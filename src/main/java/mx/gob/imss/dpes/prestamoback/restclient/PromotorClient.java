/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.prestamoback.restclient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.prestamoback.model.PromotorModel;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author osiris.hernandez
 */
@RegisterRestClient
@Path("/promotor")
public interface PromotorClient {

    @POST
    @Path("/obtenerPorClave")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorClave(PromotorModel request);
            
    @POST
    @Path("/obtenerPorCurpNssCI")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorCURPYNSSCI(PromotorModel request);

}
