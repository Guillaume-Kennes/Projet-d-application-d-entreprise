package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.utils.AppLogger;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resource class for managing contacts.
 */
@Singleton
@Path("/contacts")
public class ContactResource {

  @Inject
  private ContactUCC myContactUcc;
  @Inject
  private UserUCC myUserUcc;
  private Logger log;

  /**
   * Endpoint for meeting a company.
   *
   * @param idContact The ID of the contact.
   * @param json      The JSON object containing the meeting location.
   * @return The updated contact.
   */
  @POST
  @Path("/meet/{id_con}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Etudiant"})
  public ContactDTO meetCompany(@PathParam("id_con") int idContact, JsonNode json) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    if (json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String meetLocation = json.get("meetLocation").asText();

    myContactUcc.meetCompany(contact, meetLocation);

    log = AppLogger.getLogger("Indication d'une rencontre");
    log.log(Level.FINE, "Rencontre avec l'entreprise " + contact.getCompany().getTradeName()
        + ", lieu de rencontre : " + meetLocation);

    return contact;
  }

  /**
   * Endpoint for stopping following a contact.
   *
   * @param idContact The ID of the contact.
   * @return The updated contact.
   */
  @POST
  @Path("/stop/{id_con}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Etudiant"})
  public ContactDTO stopFollowing(@PathParam("id_con") int idContact) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    myContactUcc.stopFollowing(contact);

    log = AppLogger.getLogger("Abandon d'un contact");
    log.log(Level.FINE, "Abandon du contact entre "
        + contact.getInscriptionUE().getStudent().getFirstName()
        + " " + contact.getInscriptionUE().getStudent().getLastName()
        + " et l'entreprise "
        + contact.getCompany().getTradeName());

    return contact;
  }

  /**
   * Endpoint for when a company refuses an internship.
   *
   * @param idContact The ID of the contact.
   * @param json      The JSON object containing the reason for refusal.
   * @return The updated contact.
   */
  @POST
  @Path("/companyrefused/{id_con}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Etudiant"})
  public ContactDTO companyRefusedInternship(@PathParam("id_con") int idContact, JsonNode json) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    if (json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String reasonForRefusal = json.get("reasonRefusal").asText();
    myContactUcc.companyRefusedInternship(contact, reasonForRefusal);

    log = AppLogger.getLogger("Refus d'un contact");
    log.log(Level.FINE, "Refus d'un contact par l'entreprise " + contact.getCompany().getTradeName()
        + " pour la raison suivante : " + reasonForRefusal);

    return contact;
  }

  /**
   * Retrieves contacts associated with a user by their ID.
   *
   * @param id The ID of the user whose contacts are to be retrieved.
   * @return An ObjectNode containing the contacts associated with the user, formatted as JSON.
   * @throws SQLException             if an SQL exception occurs during the retrieval process.
   * @throws IllegalArgumentException if the user with the specified ID is not found.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Etudiant"})
  public ArrayList<ContactDTO> getContactsByUserId(@PathParam("id") int id) throws SQLException {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    ArrayList<ContactDTO> contacts = myContactUcc.getContactsByUserId(user.getId());
    if (contacts.isEmpty()) {
      return null;
    }

    log = AppLogger.getLogger("Récupération des contacts");
    log.log(Level.FINE, "Récupération des contacts de l'étudiant "
        + user.getFirstName() + " " + user.getLastName());

    return contacts;
  }

  /**
   * Adds a new contact.
   *
   * @param newContactDTO The contact data to be added.
   * @return The added contact data.
   */
  @POST
  @Path("/add")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Etudiant"})
  public ContactDTO addContact(ContactDTO newContactDTO) {
    try {
      if (newContactDTO == null) {
        throw new WebApplicationException("Invalid contact data", Status.BAD_REQUEST);
      }
      ContactDTO addedContactDTO = myContactUcc.addContact(newContactDTO);

      if (addedContactDTO == null) {
        throw new WebApplicationException("Contact could not be added",
            Status.INTERNAL_SERVER_ERROR);
      }

      log = AppLogger.getLogger("Ajout d'un contact");
      log.log(Level.FINE, "Création d'un contact entre "
          + addedContactDTO.getEnterprise()
          + " et "
          + addedContactDTO.getUserId());

      return addedContactDTO;

    } catch (Exception e) {
      throw new WebApplicationException("Failed to add contact", Status.INTERNAL_SERVER_ERROR);
    }
  }
}
