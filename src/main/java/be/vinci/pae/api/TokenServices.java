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
   * @return the object node
   */
  ObjectNode createToken(UserDTO user);

  int getUserId(String token);
}
