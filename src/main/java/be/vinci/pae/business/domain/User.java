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

  /**
   * Hashes the provided password using a hashing algorithm.
   *
   * @param password The password to be hashed.
   *
   * @return A string representing the hashed password.
   */
  String hashPassword(String password);

  /**
   * Checks whether the current user has teacher privileges.
   *
   * @return true if the current user has teacher
   *     privileges, false otherwise
   */
  boolean isTeacher();

  /**
   * Checks whether the current user has administrative privileges.
   *
   * @return true if the current user has administrative
   *     privileges, false otherwise
   */
  boolean isAdmin();
}

