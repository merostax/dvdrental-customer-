import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import services.AddressService;
import services.CustomerService;
import services.PaymentService;

import java.util.ArrayList;
import java.util.List;

@Path("/")
public class RootResource {

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listEndpoints() {
        List<String> endpoints = new ArrayList<>();

        if (AddressService.class.isAnnotationPresent(Path.class)) {
            Path pathAnnotation = AddressService.class.getAnnotation(Path.class);
            endpoints.add(uriInfo.getBaseUri() + pathAnnotation.value());
        }

        if (CustomerService.class.isAnnotationPresent(Path.class)) {
            Path pathAnnotation = CustomerService.class.getAnnotation(Path.class);
            endpoints.add(uriInfo.getBaseUri() + pathAnnotation.value());
        }

        if (PaymentService.class.isAnnotationPresent(Path.class)) {
            Path pathAnnotation = PaymentService.class.getAnnotation(Path.class);
            endpoints.add(uriInfo.getBaseUri() + pathAnnotation.value());
        }
        Jsonb jsonb = JsonbBuilder.create();
        String jsonEndpoints = jsonb.toJson(endpoints);

        return Response.ok(jsonEndpoints).build();
    }
}
