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
import java.util.List;

/**
 * Resource class for handling user-related endpoints.
 * This class provides endpoints for retrieving user information.
 */
@Singleton
@Path("/users")
public class UserResource {
  @Inject
  private UserUCC myUserUcc;

  /**
   * Retrieves a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   *
   * @return A UserDTO object representing the user with the specified ID.
   *
   * @throws IllegalArgumentException if the user with the specified ID is not found.
   */
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

  @GET
  @Path("getAllUsers")
  @Produces(MediaType.APPLICATION_JSON)
  public List<UserDTO> getAllUsers() {
    return myUserUcc.getAllUsers();
  }

}
