package be.vinci.pae.api;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.dal.UserDAO;
import jakarta.inject.Inject;
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

  @Inject
  private UserUCC userUCC;


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<UserDTO> getAll() {
    return userUCC.getAll();
  }

}
