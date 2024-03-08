package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.sql.SQLException;
import java.util.List;

public interface UserUCC {

  /**
   * Returns the user's data if the login is successful
   *
   * @param email the user's email
   * @param password the user's password
   *
   * @return the user's data if the login is successful
   */
  UserDTO login(String email, String password) ;




}
