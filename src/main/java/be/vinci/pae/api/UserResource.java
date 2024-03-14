package be.vinci.pae.api;


import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


//JWT TOKENS ICI

@Singleton
@Path("/users")
public class UserResource {
  @Inject
  private UserUCC myUserUcc;

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO getUserById(@PathParam("id") int id) {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }
    return user;
  }

}
