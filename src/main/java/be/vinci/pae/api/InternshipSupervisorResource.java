package be.vinci.pae.api;

import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import be.vinci.pae.business.ucc.InternshipSupervisorUCC;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * Resource class for handling internship supervisor-related endpoints.
 * This class provides endpoints
 * for creating and retrieving internship supervisors.
 */
@Singleton
@Path("/internshipSupervisor")
public class InternshipSupervisorResource {

  @Inject
  private InternshipSupervisorUCC internshipSupervisorUCC;

  /**
   * Creates a new internship supervisor with the provided information.
   *
   * @param json A JsonNode object containing the information of the internship supervisor to
   *             create.
   * @return An InternshipSupervisorDTO object representing the newly created internship supervisor.
   */
  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public InternshipSupervisorDTO createAnInternshipSupervisor(JsonNode json) {

    InternshipSupervisorDTO supervisor;

    String firstName = json.get("firstname").asText();
    System.out.println("prénom" + firstName);

    String lastName = json.get("name").asText();
    System.out.println("nom" + lastName);

    String phoneNumber = json.get("phone").asText();
    System.out.println("numéro de téléphone" + phoneNumber);

    int company = json.get("companyId").asInt();
    System.out.println("entreprise" + company);

    JsonNode emailNode = json.get("email");
    if (emailNode != null) {
      String email = emailNode.asText();
      supervisor =
          internshipSupervisorUCC.createAnInternshipSupervisor(firstName,
              lastName, phoneNumber, email, company);
    } else {
      supervisor =
          internshipSupervisorUCC.createAnInternshipSupervisor(firstName,
              lastName, phoneNumber, null, company);
    }

    return supervisor;
  }

  /**
   * Retrieves all internship supervisors.
   *
   * @return A list of all internship supervisors.
   */
  @GET
  @Path("/viewAll")
  @Produces(MediaType.APPLICATION_JSON)
  public List<InternshipSupervisorDTO> getAllInternships() {
    return internshipSupervisorUCC.getAllInternshipSupervisors();
  }
}

