package be.vinci.pae.api;

import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.business.ucc.InternshipUCC;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Resource class for handling internship-related endpoints. This class provides endpoints for
 * creating internships.
 */
@Singleton
@Path("/internship")
public class InternshipResource {

  @Inject
  private InternshipUCC myinternshipUCC;
  @Inject
  private ContactUCC myContactUcc;

  /**
   * Create an internship.
   *
   * @param json The JSON object containing the internship information.
   * @return The created internship.
   */
  @POST
  @Path("/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public InternshipDTO createAnInternship(JsonNode json) {
    InternshipDTO internship;

    int contact = json.get("contactId").asInt();
    System.out.println("contact : " + contact);

    int supervisor = json.get("responsable").asInt();
    System.out.println("supervisor : " + supervisor);

    String signatureDateStr = json.get("date").asText();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.sql.Date signatureDate = null;

    try {
      java.util.Date parsed = format.parse(signatureDateStr);
      signatureDate = new java.sql.Date(parsed.getTime());
    } catch (ParseException e) {
      System.out.println("La date fournie ne correspond pas au format yyyy-MM-dd");
    }

    System.out.println("signatureDate : " + signatureDate);

    // ContactDTO contactDTO = myContactUcc.getContactById(contact);
    JsonNode projetNode = json.get("sujet");
    if (projetNode != null) {
      String projet = projetNode.asText();
      internship = myinternshipUCC.createAnInternship(contact, supervisor, projet, signatureDate);
    } else {
      internship =
          myinternshipUCC.createAnInternship(contact, supervisor, null, signatureDate);
    }

    // myContactUcc.acceptInternship(contactDTO);
    return internship;
  }

  /**
   * Create or modify an internship.
   *
   * @param id The ID of the internship to create or modify.
   * @param json The JSON object containing the internship information.
   * @return The created or modified internship.
   */
  @POST
  @Path("/createOrModify/{id_int}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public InternshipDTO createOrModifyAnInternship(@PathParam("id_int") int id, JsonNode json) {
    InternshipDTO internship = myinternshipUCC.getInternshipById(id);

    if (internship == null) {
      throw new IllegalArgumentException("Internship not found");
    }

    if (json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String subject = json.get("projet").asText();

    return myinternshipUCC.createOrModifyAnInternship(internship, subject);
  }

  /**
   * Get all the school years.
   *
   * @return A list of String containing the school years.
   */
  @GET
  @Path("/schoolYears")
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getSchoolYears() {
    return myinternshipUCC.getSchoolYears();
  }
}
