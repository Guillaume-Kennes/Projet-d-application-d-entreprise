package be.vinci.pae.api;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Singleton
@Path("/auths")
public class AuthsResource {
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private UserUCC myUserUCC;
  //private TokenServices token;


  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String login = json.get("email").asText();
    String password = json.get("password").asText();

    UserDTO publicUser = myUserUCC.login(login, password);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    String token = createToken(publicUser);
    return jsonMapper.createObjectNode().put("token", token)
        .put("id", publicUser.getId())
        .put("email", publicUser.getEmail())
        .put("lastName", publicUser.getLastName())
        .put("firstName", publicUser.getFirstName())
        .put("phoneNumber", publicUser.getPhoneNumber())
        .put("registrationDate", publicUser.getRegistrationDate())
        .put("role", publicUser.getRole());
  }

  public String createToken(UserDTO userDTO) {
    Date dateOfExpiration = new Date(
        System.currentTimeMillis() + TimeUnit.HOURS.toMillis(48)
    );
    return JWT.create().withIssuer("auth0")
        .withClaim("id", userDTO.getId())
        .withExpiresAt(dateOfExpiration)
        .sign(jwtAlgorithm);
  }

//  @GET
//  @Path("user")
//  @Produces(MediaType.APPLICATION_JSON)
//  @Authorize
//  public int getUser(@Context ContainerRequestContext requestContext) {
//    UserDTO user = (UserDTO) requestContext.getProperty("user");
//    return token.createToken(user).get("id").asInt();
//    }




}
