package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.ContactUCC;
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
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Resource class for managing contacts.
 */
@Singleton
@Path("/contacts")
public class ContactResource {

  private ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private ContactUCC myContactUcc;
  @Inject
  private UserUCC myUserUcc;

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

  /**
   * Retrieves contacts corresponding to a user's ID.
   *
   * @param id The ID of the user.
   *
   * @return An ObjectNode object containing all the data to be displayed on the user's contact page
   *
   * @throws IllegalArgumentException if the user with the specified ID is not found.
   */
  @GET
  @Authorize
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getContactsByUserId(@PathParam("id") int id) {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    ObjectNode response = jsonMapper.createObjectNode();
    ArrayList<ContactDTO> contacts = myContactUcc.getContactsByUserId(user.getId());
    if (contacts.isEmpty()) {
      return null;
    }

    ArrayList<String> contactList = new ArrayList<>();

    for (ContactDTO c : contacts) {
      if (c.getCompany().getDesignation() == null) {
        contactList.add(c.getCompany().getTradeName() + " : dans l'état " + c.getState());
      } else {
        contactList.add(c.getCompany().getTradeName() + " " + c.getCompany().getDesignation()
            + " : dans l'état " + c.getState());
      }
    }

    response.putPOJO("contacts", contactList);
    return response;
  }
}
