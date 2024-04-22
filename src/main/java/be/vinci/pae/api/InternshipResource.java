package be.vinci.pae.api;

import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.ucc.InternshipUCC;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;

  @Singleton
  @Path("/internship")
  public class InternshipResource {

    @Inject
  private InternshipUCC myinternshipUCC;

  @POST
  @Path("/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public InternshipDTO createAnInternship(JsonNode json) {
    InternshipDTO internship;

    int contact = json.get("contact").asInt();
    System.out.println("contact" + contact);

    int supervisor = json.get("supervisor").asInt();
    System.out.println("supervisor" + supervisor);

    String signatureDateStr = json.get("signatureDate").asText();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.sql.Date signatureDate = null;

    try {
      java.util.Date parsed = format.parse(signatureDateStr);
      signatureDate = new java.sql.Date(parsed.getTime());
    } catch (ParseException e) {
      System.out.println("La date fournie ne correspond pas au format yyyy-MM-dd");
      // Gérer l'erreur comme vous le souhaitez, par exemple en renvoyant une réponse d'erreur à l'utilisateur
    }

    System.out.println("signatureDate" + signatureDate);

    JsonNode projetNode = json.get("projet");
    if (projetNode != null) {
      String projet = projetNode.asText();
      internship = myinternshipUCC.createAnInternship(contact, supervisor, projet, signatureDate);
    } else {
      internship =
          myinternshipUCC.createAnInternship(contact, supervisor, null, signatureDate);
    }

    return internship;
  }

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
}
