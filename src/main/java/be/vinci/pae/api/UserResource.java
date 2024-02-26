package be.vinci.pae.api;

import be.vinci.pae.business.domain.User;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

                        //JWT TOKENS ICI

@Singleton
@Path("/users")
public class UserResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> getAll() {
    return null;
  }

}
