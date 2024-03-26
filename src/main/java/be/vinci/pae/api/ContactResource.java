package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.ucc.ContactUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/contact")
public class ContactResource {

  @Inject
  private ContactUCC contactUCC;


  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO addCompany(ContactDTO newContactDTO) {
    int userId = newContactDTO.getUserId();
    System.out.println("CompanyResource -------> newContactDTO : " + newContactDTO);
    System.out.println("CompanyResource -------> userId : " + userId);
    // Validate the new item
    try {
      if (newContactDTO == null) {
        throw new WebApplicationException("Invalid company data", Status.BAD_REQUEST);
      }
      newContactDTO.setUserId(userId);
      // Add the new item
      ContactDTO addedContactDTO = contactUCC.addContact(newContactDTO);
      System.out.println("CompanyRessource -------> addedContactDTO" + addedContactDTO);
      if (addedContactDTO == null) {
        throw new WebApplicationException("Company could not be added",
            Status.INTERNAL_SERVER_ERROR);
      }
      System.out.println("ContactResource ---> addedContactDTO : " + addedContactDTO);
      return addedContactDTO;
    } catch (Exception e) {
      // Handle exceptions appropriately
      System.out.println("ContactResource exception");
      throw new WebApplicationException("Failed to add contact", Status.INTERNAL_SERVER_ERROR);
    }
  }
}
