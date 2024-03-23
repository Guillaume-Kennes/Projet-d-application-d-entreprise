package be.vinci.pae.api;

import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.domain.ViewContactDTO;
import be.vinci.pae.business.ucc.InternshipUCC;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.business.ucc.ViewContactUCC;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;

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
  @Inject
  private ViewContactUCC myContactUcc;

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
    ArrayList<ViewContactDTO> contacts = myContactUcc.getTakenContactsByUserId(id);

    ObjectNode response = jsonMapper.createObjectNode();
    response.put("email", user.getEmail());
    response.put("lastName", user.getLastName());
    response.put("firstName", user.getFirstName());
    response.put("phoneNumber", user.getPhoneNumber());

    if (internship != null) {
      response.put("internshipTitle", internship.getProject());
      response.put("internshipCompany", internship.getContact().getCompany().getTradeName()
          + " " + internship.getContact().getCompany().getDesignation());
      response.put("internshipSupervisor", internship.getSupervisor().getFirstName() + " "
          + internship.getSupervisor().getLastName());
      response.put("internshipSubject", internship.getProject());
    }

    if(!contacts.isEmpty()) {
      ArrayList<String> companies = new ArrayList<>();
      for (ViewContactDTO c : contacts) {
        if(c.getCompany().getDesignation() == null){
          companies.add(c.getCompany().getTradeName());
        }else{
          companies.add(c.getCompany().getTradeName() + " " + c.getCompany().getDesignation());
        }
      }
      response.putPOJO("contactCompanies", companies);
    }

    return response;
  }

}
