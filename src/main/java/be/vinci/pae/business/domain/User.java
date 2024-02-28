package be.vinci.pae.business.domain;


public interface User extends UserDTO {

  /**
   * Checks if the user's password matches the one saved in the database
   *
   * @param password the password provided by the user when logging in
   */
  boolean checkPassword(String password);

  /**
   * Hashes the password provided by the user when they register
   *
   * @param password the password provided by the user when signing up
   */
  String hashPassword(String password);
}

