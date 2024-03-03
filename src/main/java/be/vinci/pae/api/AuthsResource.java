package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
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
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/auths")
public class AuthsResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));

  @Inject
  private UserUCC myUserUCC;

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO login(JsonNode json) {
    System.out.println("LOGIN");
    // Get and check credentials
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String login = json.get("email").asText();
    String password = json.get("password").asText();

    // Try to log in
    UserDTO publicUser = myUserUCC.login(login, password);
    if (publicUser == null) {
      throw new WebApplicationException("Login/password incorrect", Response.Status.UNAUTHORIZED);
    }

    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("user", publicUser.getEmail()).sign(this.jwtAlgorithm);
      System.out.println("Token: " + token);

    } catch (Exception e) {
      System.out.println("Failed to create a token");
      return null;
    }
    return publicUser;
  }

  @GET
  @Path("user")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public UserDTO getUser(@Context ContainerRequestContext requestContext) {
    UserDTO user = (UserDTO) requestContext.getProperty("user");

    if (user == null) {
      throw new WebApplicationException("user", Status.UNAUTHORIZED);
    }
    return user;
  }

}
