package mx.gob.imss.dpes.prestamoback.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/delegaciones")
@RegisterRestClient
public interface DelegacionCatalogoClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{numDelegacion}")
    public Response getDelegacion(@PathParam("numDelegacion") String numDelegacion);
}
