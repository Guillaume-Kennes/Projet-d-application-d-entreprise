package be.vinci.pae.api;

import be.vinci.pae.business.domain.DomainFactory;
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


/**
 * Resource class for handling authentication-related requests.
 * This class provides endpoints for user authentication such as login.
 */
@Singleton
@Path("/auths")
public class AuthsResource {
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private UserUCC myUserUCC;
  //private TokenServices token;

  @Inject
  private DomainFactory domainFactory;


  /**
   * Endpoint for user login.
   *
   * @param json The JSON object containing the login credentials.
   *
   * @return An ObjectNode containing a JWT token and user information upon successful login.
   *
   * @throws WebApplicationException if login credentials are missing or incorrect.
   */
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
        .put("registrationDate", String.valueOf(publicUser.getRegistrationDate()))
        .put("role", publicUser.getRole());
  }


  /**
   * Registers a new user with the provided information.
   *
   * @return A UserDTO object representing the registered user.
   */
  @POST
  @Path("register")
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO register(UserDTO userDTO){
    if(userDTO.getEmail().isBlank() || userDTO.getPassword().isBlank() || userDTO.getLastName().isBlank() || userDTO.getFirstName().isBlank() || userDTO.getPhoneNumber().isBlank() || userDTO.getPhoneNumber().isBlank())
      throw new WebApplicationException("Missing information(s)");
    // Vérification blank & null de users.http

    UserDTO user = domainFactory.getUser();

    user.setEmail(userDTO.getEmail());
    user.setPassword(userDTO.getPassword());
    user.setLastName(userDTO.getLastName());
    user.setFirstName(userDTO.getFirstName());
    user.setPhoneNumber(userDTO.getPhoneNumber());


    user.setRole(userDTO.getRole());

    return myUserUCC.register(user);

  }


  /**
   * Creates a JWT token for the given user.
   *
   * @param userDTO The UserDTO object representing the user for whom the token is to be created.
   *
   * @return A JWT token string.
   */
  public String createToken(UserDTO userDTO) {
    Date dateOfExpiration = new Date(
        System.currentTimeMillis() + TimeUnit.HOURS.toMillis(48)
    );
    return JWT.create().withIssuer("auth0")
        .withClaim("id", userDTO.getId())
        .withExpiresAt(dateOfExpiration)
        .sign(jwtAlgorithm);
  }
}
