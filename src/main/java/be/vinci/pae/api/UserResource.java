package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.business.ucc.InternshipUCC;
import be.vinci.pae.business.ucc.UserUCC;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource class for handling user-related endpoints. This class provides endpoints for retrieving
 * user information.
 */
@Singleton
@Path("/users")
public class UserResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private UserUCC myUserUcc;
  @Inject
  private InternshipUCC myInternshipUcc;
  @Inject
  private ContactUCC myContactUcc;

  /**
   * Retrieves user information by their ID.
   *
   * @param id The ID of the user to retrieve information for.
   * @return An ObjectNode containing the user information formatted as JSON.
   * @throws SQLException             if an SQL exception occurs during the retrieval process.
   * @throws IllegalArgumentException if the user with the specified ID is not found.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur", "Administratif", "Etudiant"})
  public ObjectNode getUserById(@PathParam("id") int id) throws SQLException {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }
    ObjectNode response = jsonMapper.createObjectNode();
    response.put("email", user.getEmail());
    response.put("lastName", user.getLastName());
    response.put("firstName", user.getFirstName());
    response.put("phoneNumber", user.getPhoneNumber());

    InternshipDTO internship = myInternshipUcc.getInternshipByUserId(id);
    if (internship != null) {
      response.put("internshipTitle", internship.getProject());
      response.put("internshipCompany", internship.getContact().getCompany().getTradeName()
          + " " + internship.getContact().getCompany().getDesignation());
      response.put("internshipSupervisor", internship.getSupervisor().getFirstName() + " "
          + internship.getSupervisor().getLastName());
      response.put("internshipSubject", internship.getProject());
    }

    ArrayList<ContactDTO> contacts = myContactUcc.getTakenContactsByUserId(id);

    if (!contacts.isEmpty()) {
      ArrayList<String> companies = new ArrayList<>();
      for (ContactDTO c : contacts) {
        if (c.getCompany().getDesignation() == null) {
          companies.add(c.getCompany().getTradeName());
        } else {
          companies.add(c.getCompany().getTradeName() + " " + c.getCompany().getDesignation());
        }
      }
      response.putPOJO("contactCompanies", companies);
    }

    return response;
  }

  /**
   * This method sends a request to retrieve the list of all users and returns it as a
   * JSON-formatted list of UserDTO objects. It logs an informational message indicating the request
   * to view the list of users.
   *
   * @param requestContext The request context containing authentication information.
   * @return A list of UserDTO objects representing all users.
   */
  @GET
  @Path("getAllUsers")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur", "Administratif"})
  public List<UserDTO> getAllUsers(@Context ContainerRequestContext requestContext) {
    UserDTO authentificatedUser = (UserDTO) requestContext.getProperty("user");
    System.out.println(authentificatedUser); //juste pour Jenkins
    return myUserUcc.getAllUsers();
  }

  /**
   * Retrieves the number of students with an internship for a given school year.
   *
   * @param schoolYear The school year for which to retrieve the number of students with an
   *                   internship.
   * @return The number of students with an internship for the specified school year.
   */
  @GET
  @Path("/getStudentsWithInternship/{school_year}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur"})
  public int getStudentsWithInternship(@PathParam("school_year") String schoolYear) {
    System.out.println("SCHOOL YEAR : " + schoolYear);
    return myUserUcc.getStudentsWithInternship(schoolYear);
  }

  @GET
  @Path("/getStudentsWithoutInternship/{school_year}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur"})
  public int getStudentsWithoutInternship(@PathParam("school_year") String schoolYear) {
    System.out.println("SCHOOL YEAR : " + schoolYear);
    return myUserUcc.getStudentsWithoutInternship(schoolYear);
  }

  /**
   * Endpoint for when a user changes their password.
   *
   * @param id The ID of the user.
   * @param json      The JSON object containing the new password.
   * @return The updated user.
   */
  @POST
  @Path("/editPassword/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public UserDTO updatePassword(@PathParam("id") int id, JsonNode json) {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    if (json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String newPassword = json.get("password").asText();

    myUserUcc.updatePassword(user, newPassword);
    return user;
  }

  /**
   * Endpoint for when a user changes their phone number.
   *
   * @param id The ID of the user.
   * @param json      The JSON object containing the new number.
   * @return The updated user.
   */
  @POST
  @Path("/editPhoneNumber/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public UserDTO updatePhoneNumber(@PathParam("id") int id, JsonNode json) {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    if (json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String newPhone = json.get("phone").asText();

    myUserUcc.updatePhoneNumber(user, newPhone);
    return user;
  }
}
