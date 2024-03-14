package be.vinci.pae.api;

import be.vinci.pae.business.domain.UserDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * class TokenServices.
 */
public interface TokenServices {

  /**
   * create token method.
   *
   * @param user the user
   *
   * @return the object node
   */
  ObjectNode createToken(UserDTO user);

  /**
   * Retrieves the user ID associated with the provided token.
   *
   * @param token the authentication token
   *
   * @return the user ID corresponding to the token
   */
  int getUserId(String token);
}
