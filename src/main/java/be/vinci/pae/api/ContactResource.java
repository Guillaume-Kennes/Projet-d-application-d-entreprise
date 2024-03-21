package be.vinci.pae.api.filters;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ContainerRequest;

@Singleton
@Path("/contacts")
public class ContactResource {

  @Inject
  private ContactUCC myContactUcc;

  @POST
  @Path("/meet")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ContactDTO meetCompany(@Context ContainerRequest request, JsonNode json) {
    int id = json.get("id").asInt();


  }

}
