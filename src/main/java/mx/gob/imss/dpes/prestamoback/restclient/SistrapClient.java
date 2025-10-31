package mx.gob.imss.dpes.prestamoback.restclient;

import mx.gob.imss.dpes.interfaces.sipre.model.ConsultaDatosTitularRequest;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reinstalacionPrestamo")
@RegisterRestClient
public interface SistrapClient {

    @POST
    @Path("/consultaDatosPersonalesTitular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response obtenerDatosTituar(ConsultaDatosTitularRequest request);

}
