package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.UserDTO;

/**
 * Represents a User Use Case Controller (UCC) with methods related to user operations.
 */
public interface UserUCC {

  /**
   * Returns the user's data if the login is successful.
   *
   * @param email    the user's email
   * @param password the user's password
   * @return the user's data if the login is successful
   */
  UserDTO login(String email, String password);


  /**
   * Returns the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the user corresponding to the id
   */
  UserDTO getUserById(int id);

  /**
   * Registers a new user.
   * This method takes a UserDTO object representing the user to be registered.
   * It performs the registration process and returns a UserDTO object representing the registered user.
   *
   * @param userDTO The UserDTO object containing user information.
   *
   * @return A UserDTO object representing the registered user.
   */
  UserDTO register(UserDTO userDTO);
}
