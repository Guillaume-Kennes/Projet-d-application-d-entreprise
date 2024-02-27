package be.vinci.pae.dal;

import be.vinci.pae.business.domain.UserDTO;

public interface UserDAO {

  /**
   * @param email the email whose user the method needs to find
   * @return the corresponding user
   */
  UserDTO getUserByEmail(String email);

}

