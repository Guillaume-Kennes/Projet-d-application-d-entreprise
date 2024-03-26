package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
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
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Resource class for handling authentication-related requests. This class provides endpoints for
 * user authentication such as login.
 */
@Singleton
@Path("/auths")
public class AuthsResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private UserUCC myUserUCC;

  /**
   * Endpoint for user login.
   *
   * @param json The JSON object containing the login credentials.
   * @return An ObjectNode containing a JWT token and user information upon successful login.
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
    ObjectNode responseObject = jsonMapper.createObjectNode();
    // Add token and user data to the response
    responseObject.put("token", token);
    responseObject.putPOJO("user", publicUser);
    return responseObject;
  }

  /**
   * Registers a new user.
   * This method is annotated with @POST and @Path("register")
   *     for RESTful API endpoint configuration.
   * It accepts a UserDTO object representing the user to be registered.
   * Validates the required fields of the user and throws
   *     a WebApplicationException if any required field is missing.
   * Calls the register method of the MyUserUCC instance to perform the registration.
   *
   * @param userDTO The UserDTO object containing user information.
   *
   * @return A UserDTO object representing the registered user.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO register(UserDTO userDTO) {
    if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()
        || userDTO.getPassword() == null || userDTO.getPassword().isBlank()
        || userDTO.getLastName() == null || userDTO.getLastName().isBlank()
        || userDTO.getFirstName() == null || userDTO.getFirstName().isBlank()
        || userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().isBlank()
        || userDTO.getRole() == null || userDTO.getRole().isBlank()) {
      throw new WebApplicationException("Missing information(s)");
    }

    return myUserUCC.register(userDTO);
  }

  /**
   * Retrieves the user information from the request context.
   * This method is accessed via HTTP GET request to the specified path "refresh".
   *
   * @param requestContext The context of the container request.
   * @return The user data transfer object containing user information.
   * @throws WebApplicationException If the user data is not found in the request context,
   *                                 it throws an exception with status code 401 (UNAUTHORIZED).
   */
  @GET
  @Path("refresh")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public UserDTO getUser(@Context ContainerRequestContext requestContext) {
    UserDTO userDTO = (UserDTO) requestContext.getProperty("user");

    if (userDTO == null) {
      throw new WebApplicationException("user", Status.UNAUTHORIZED);
    }
    return jsonMapper.convertValue(userDTO, UserDTO.class);
  }

  /**
   * Creates a JWT token for the given user.
   *
   * @param userDTO The UserDTO object representing the user for whom the token is to be created.
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
