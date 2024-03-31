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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * Resource class for managing contacts.
 */
@Singleton
@Path("/contacts")
public class ContactResource {

  private static final Logger logger = Logger.getLogger(ContactResource.class);
  private ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private ContactUCC myContactUcc;
  @Inject
  private UserUCC myUserUcc;

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
  @Authorize
  public ContactDTO meetCompany(@PathParam("id_con") int idContact, JsonNode json) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      logger.error("Le contact n'a pas été trouvé");
      throw new IllegalArgumentException("Contact not found");
    }

    if (json == null) {
      logger.error("Les infos nécessaires ne sont pas présentes");
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String meetLocation = json.get("meetLocation").asText();

    myContactUcc.meetCompany(contact, meetLocation);
    logger.info("Le contact numéro " + idContact + " est passé à l'état pris");

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
  @Authorize
  public ContactDTO stopFollowing(@PathParam("id_con") int idContact) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      logger.error("Impossible de trouver le contact");
      throw new IllegalArgumentException("Contact not found");
    }

    myContactUcc.stopFollowing(contact);
    logger.info("Le contact numéro " + idContact + " n'est plus suivi");
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
  @Authorize
  public ContactDTO companyRefusedInternship(@PathParam("id_con") int idContact, JsonNode json) {
    ContactDTO contact = myContactUcc.getContactById(idContact);
    if (contact == null) {
      logger.error("Contact non trouvé");
      throw new IllegalArgumentException("Contact not found");
    }

    if (json == null) {
      logger.error("Pas de raison fournie pour le refus");
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String reasonForRefusal = json.get("reasonRefusal").asText();
    System.out.println("reason_for_refusal : " + reasonForRefusal);

    myContactUcc.companyRefusedInternship(contact, reasonForRefusal);
    logger.info("Le contact numéro " + idContact + " est refusé pour la raison suivante : "
        + reasonForRefusal);
    return contact;
  }

  /**
   * Retrieves contacts corresponding to a user's ID.
   *
   * @param id The ID of the user.
   * @return An ObjectNode object containing all the data to be displayed
   * @throws IllegalArgumentException if the user with the specified ID is not found.
   */
  @GET
  @Authorize
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getContactsByUserId(@PathParam("id") int id) {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      logger.error("Cet utilisateur n'est pas présent");
      throw new IllegalArgumentException("User not found");
    }

    ObjectNode response = jsonMapper.createObjectNode();
    ArrayList<ContactDTO> contacts = myContactUcc.getContactsByUserId(user.getId());
    if (contacts.isEmpty()) {
      return null;
    }
    HashMap<Integer, String> contactList = new HashMap<>();

    for (ContactDTO c : contacts) {
      if (c.getCompany().getDesignation() == null) {
        contactList.put(c.getId(), c.getCompany().getTradeName()
            + " : dans l'état " + c.getState());
      } else {
        contactList.put(c.getId(), c.getCompany().getTradeName()
            + " " + c.getCompany().getDesignation()
            + " : dans l'état " + c.getState());
      }
    }

    response.putPOJO("contacts", contactList);
    logger.info("Récupération de tous les contacts de l'utilisateur " + id);
    return response;
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
  @Authorize
  public ContactDTO addContact(ContactDTO newContactDTO) {
    int userId = newContactDTO.getUserId();
    System.out.println("ContactResource -------> newContactDTO : " + newContactDTO);
    System.out.println("ContactResource -------> userId : " + userId);
    // Validate the new item
    try {
      if (newContactDTO == null) {
        logger.error("Les données présentes ne permettent pas l'ajout d'un contact");
        throw new WebApplicationException("Invalid contact data", Status.BAD_REQUEST);
      }
      // newContactDTO.setUserId(userId);
      // Add the new item
      ContactDTO addedContactDTO = myContactUcc.addContact(newContactDTO);
      System.out.println("ContactRessource -------> addedContactDTO" + addedContactDTO);
      if (addedContactDTO == null) {
        logger.error("Impossible d'ajouter le contact");
        throw new WebApplicationException("Contact could not be added",
            Status.INTERNAL_SERVER_ERROR);
      }
      System.out.println("ContactResource ---> addedContactDTO : " + addedContactDTO);
      logger.info("Contact " + newContactDTO.getId() + " ajouté");
      return addedContactDTO;
    } catch (Exception e) {
      logger.error("Impossible d'ajouter le contact");
      System.out.println("ContactResource exception");
      throw new WebApplicationException("Failed to add contact", Status.INTERNAL_SERVER_ERROR);
    }
  }
}
