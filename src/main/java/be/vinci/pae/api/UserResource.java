package be.vinci.pae.api;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Resource class for handling user-related endpoints.
 * This class provides endpoints for retrieving user information.
 */
@Singleton
@Path("/users")
public class UserResource {

  private ObjectMapper jsonMapper = new ObjectMapper();
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
  public ObjectNode getUserById(@PathParam("id") int id) {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }
    return jsonMapper.createObjectNode().put("userInfo", user.toString())
        .put("email", user.getEmail())
        .put("lastName", user.getLastName())
        .put("firstName", user.getFirstName())
        .put("phoneNumber", user.getPhoneNumber());
  }

}
