package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.ucc.ContactUCC;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Resource class for managing contacts.
 */
@Singleton
@Path("/contacts")
public class ContactResource {

  @Inject
  private ContactUCC myContactUcc;

  /**
   * Endpoint for meeting a company.
   *
   * @param idContact The ID of the contact.
   *
   * @param json The JSON object containing the meeting location.
   *
   * @return The updated contact.
   */
  @POST
  @Path("/meet/{id_con}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO meetCompany(@PathParam("id_con") int idContact, JsonNode json) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    if (json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String meetLocation = json.get("meetLocation").asText();
    System.out.println("Meet Location: " + meetLocation);

    myContactUcc.meetCompany(contact, meetLocation);

    return contact;
  }


  /**
   * Endpoint for stopping following a contact.
   *
   * @param idContact The ID of the contact.
   *
   * @return The updated contact.
   */
  @POST
  @Path("/stop/{id_con}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO stopFollowing(@PathParam("id_con") int idContact) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    myContactUcc.stopFollowing(contact);

    return contact;
  }

  /**
   * Endpoint for when a company refuses an internship.
   *
   * @param idContact The ID of the contact.
   *
   * @param json The JSON object containing the reason for refusal.
   *
   * @return The updated contact.
   */
  @POST
  @Path("/companyrefused/{id_con}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO companyRefusedInternship(@PathParam("id_con") int idContact, JsonNode json) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    if (json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String reasonForRefusal = json.get("reason_for_refusal").asText();
    System.out.println("reason_for_refusal : " + reasonForRefusal);

    myContactUcc.companyRefusedInternship(contact, reasonForRefusal);

    return contact;
  }
}
