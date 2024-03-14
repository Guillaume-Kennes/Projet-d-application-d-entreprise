package be.vinci.pae.business.domain;


/**
 * Represents a user with various properties.
 * Extends the UserDTO interface.
 */
public interface User extends UserDTO {

  /**
   * Checks if the user's password matches the one saved in the database.
   *
   * @param password the password provided by the user when logging in
   *
   * @return true if the password matches, otherwise false
   */
  boolean checkPassword(String password);
}

