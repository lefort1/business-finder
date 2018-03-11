package jest.jon;

import jest.jon.util.IO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
    private static Client client = ClientBuilder.newClient();
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    
    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
        String key = IO.streamLines("src/test/resources/api-key.txt")
                       .findFirst()
                       .orElseThrow(() -> new RuntimeException("Can't read api key."));
        return client.target("https://api.yelp.com/v3/businesses/gary-danko-san-francisco")
                .request(MediaType.APPLICATION_JSON)
                .header("authorization","Bearer " + key)
                .get(String.class);
    }
}
