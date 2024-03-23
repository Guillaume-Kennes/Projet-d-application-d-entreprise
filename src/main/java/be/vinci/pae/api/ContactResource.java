package be.vinci.pae.api;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.domain.ViewContactDTO;
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
 * Resource class for handling contact-related endpoints.
 * This class provides endpoints for retrieving contact information.
 */
@Singleton
@Path("/contacts")
public class ContactResource {

  private ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private UserUCC myUserUcc;
  @Inject
  private ViewContactUCC myContactUcc;

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
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getContactsByUserId(@PathParam("id") int id) {
    UserDTO user = myUserUcc.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    ObjectNode response = jsonMapper.createObjectNode();
    ArrayList<ViewContactDTO> contacts = myContactUcc.getContactsByUserId(user.getId());
    if(contacts.isEmpty()){
      return null;
    }
    
    ArrayList<String> contactList = new ArrayList<>();

    for (ViewContactDTO c : contacts) {
      if(c.getCompany().getDesignation() == null){
        contactList.add(c.getCompany().getTradeName() + "\t" + c.getState());
      }else{
        contactList.add(c.getCompany().getTradeName() + " " + c.getCompany().getDesignation() + "\t" + c.getState());
      }
    }

    response.putPOJO("contacts", contactList);
    return response;
  }
}
