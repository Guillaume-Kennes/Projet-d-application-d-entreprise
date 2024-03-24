package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.ContactUCC;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

@Singleton
@Path("/contacts")
public class ContactResource {

  @Inject
  private ContactUCC myContactUcc;

  @POST
  @Path("/meet/{id_contact}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO meetCompany(@PathParam("id_contact") int id_contact,JsonNode json) {
    ContactDTO contact = myContactUcc.getContactById(id_contact);
    if(contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    if(json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String meetLocation = json.get("meetLocation").asText();
    System.out.println("Meet Location: " + meetLocation);

    myContactUcc.meetCompany(contact, meetLocation);

    return contact;
  }


  @POST
  @Path("/stop/{id_contact}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO stopFollowing(@PathParam("id_contact") int id_contact) {
    ContactDTO contact = myContactUcc.getContactById(id_contact);
    if(contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    myContactUcc.stopFollowing(contact);

    return contact;
  }

  @POST
  @Path("/companyrefused/{id_contact}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public ContactDTO companyRefusedInternship(@PathParam("id_contact") int id_contact, JsonNode json) {
    ContactDTO contact = myContactUcc.getContactById(id_contact);
    if(contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }

    if(json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String reasonForRefusal = json.get("reason_for_refusal").asText();
    System.out.println("reason_for_refusal : " + reasonForRefusal);

    myContactUcc.companyRefusedInternship(contact, reasonForRefusal);

    return contact;
  }
}
