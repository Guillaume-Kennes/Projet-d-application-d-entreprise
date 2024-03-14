package be.vinci.pae.api;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * implementation of TokenServices.
 */
public class TokenServicesImpl implements TokenServices {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier =
      JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
  private final ObjectMapper jsonMapper = new ObjectMapper();


  /**
   * create token method.
   *
   * @param user the user
   *
   * @return the object node
   */
  @Override
  public ObjectNode createToken(UserDTO user) {
    if (user == null) {
      return null;
    }
    String token;
    try {
      token =
          JWT.create().withIssuer("auth0").withClaim("user", user.getId()).sign(this.jwtAlgorithm);
      ObjectNode publicUser =
          jsonMapper
              .createObjectNode()
              .put("token", token)
              .put("id", user.getId())
              .put("login", user.getEmail());
      return publicUser;
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }

  /**
   * Retrieves the user ID from a JWT token.
   *
   * @param token The JWT token from which to retrieve the user ID.
   *
   * @return The user ID extracted from the token, or -1 if the token is null or invalid.
   */
  public int getUserId(String token) {
    if (token == null) {
      return -1;
    }
    DecodedJWT decodedToken;
    try {
      decodedToken = this.jwtVerifier.verify(token);
    } catch (Exception e) {
      return -1;
    }
    int id = -1;
    try {
      id = decodedToken.getClaim("user").asInt();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return id;
  }
}
