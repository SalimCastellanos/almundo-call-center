package co.com.almundo.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("mytestresource")
public class ResourceService {

    /**
     * Metodo inicial para probar el correcto despliegue de los servicios con jersey
     *
     * @return String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Call Center Almundo.com!";
    }
}