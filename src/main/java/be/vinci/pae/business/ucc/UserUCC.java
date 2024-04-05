package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.sql.SQLException;
import java.util.List;

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
  UserDTO login(String email, String password) throws SQLException;


  /**
   * Returns the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the user corresponding to the id
   */
  UserDTO getUserById(int id);

  /**
   * Retrieves the list of all users available in the system.
   *
   * @return A list containing UserDTO objects representing all users.
   *     If no users are found, the list will be empty.
   */
  List<UserDTO> getAllUsers();


  /**
   * Registers a new user in the database.
   *
   * @param userDTO The UserDTO object containing user information.
   *
   * @return A UserDTO object representing the registered user, or null if registration fails.
   */
  UserDTO register(UserDTO userDTO) throws SQLException;

  /**
   * Checks whether the specified user has teacher privileges.
   *
   * @param userDTO the user data transfer object to check
   *
   * @return true if the user has teacher privileges, false otherwise
   */
  boolean isTeacher(UserDTO userDTO);

  /**
   * Checks whether the specified user has administrative privileges.
   *
   * @param userDTO the user data transfer object to check
   *
   * @return true if the user has administrative privileges, false otherwise
   */
  boolean isAdmin(UserDTO userDTO);

}
