package be.vinci.pae.api;

import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.InternshipUCC;
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
  @Inject
  private InternshipUCC myInternshipUcc;

  /**
   * Retrieves a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   *
   * @return An ObjectNode object containing all the data to be displayed on the user profile
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
    InternshipDTO internship = myInternshipUcc.getInternshipByUserId(id);
    return jsonMapper.createObjectNode()
        .put("email", user.getEmail())
        .put("lastName", user.getLastName())
        .put("firstName", user.getFirstName())
        .put("phoneNumber", user.getPhoneNumber())
        .put("internshipTitle", internship.getProject())
        .put("internshipCompany", internship.getContact().getCompany().getTradeName()
        + " " + internship.getContact().getCompany().getDesignation())
        .put("internshipSupervisor", internship.getSupervisor().getFirstName() + " "
        + internship.getSupervisor().getLastName())
        .put("internshipSubject", internship.getProject());
  }

}
