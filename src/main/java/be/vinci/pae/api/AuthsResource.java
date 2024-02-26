package be.vinci.pae.api;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.dal.UserDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/auths")
public class AuthsResource {
  @Inject
  private UserUCC myUserUCC;

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("login") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String login = json.get("login").asText();
    String password = json.get("password").asText();

    // Try to log
    UserDTO publicUser = myUserUCC.login(login, password);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect", Response.Status.UNAUTHORIZED);
    }
    return publicUser;

  }

}
