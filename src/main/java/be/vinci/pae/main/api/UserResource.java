package be.vinci.pae.main.api;

import be.vinci.pae.main.domain.User;
import be.vinci.pae.main.services.UserDataService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;


@Singleton
@Path("/users")
public class UserResource {

  @Inject
  private UserDataService myUserDataService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> getAll() {
    return myUserDataService.getAll();
  }

}
