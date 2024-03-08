package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/auths")
public class AuthsResource {

  @Inject
  private UserUCC myUserUCC;
  private TokenServices token;

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {
    System.out.println("LOGIN");
    // Get and check credentials
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String login = json.get("email").asText();
    String password = json.get("password").asText();

    // Try to log in
    UserDTO publicUser = myUserUCC.login(login, password);

    return token.createToken(publicUser);
  }

  @GET
  @Path("user")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public int getUser(@Context ContainerRequestContext requestContext) {
    UserDTO user = (UserDTO) requestContext.getProperty("user");

    return token.createToken(user).get("id").asInt();
  }

}
