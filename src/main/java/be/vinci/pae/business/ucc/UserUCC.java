package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.exception.ConflictException;
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
  UserDTO login(String email, String password);


  /**
   * Returns the user corresponding to the id.
   *
   * @param id the user's id
   * @return the user corresponding to the id
   */
  UserDTO getUserById(int id);

  /**
   * Retrieves the list of all users available in the system.
   *
   * @return A list containing UserDTO objects representing all users. If no users are found, the
   *     list will be empty.
   */
  List<UserDTO> getAllUsers();


  /**
   * Registers a new user with the system.
   *
   * @param userDTO The {@code UserDTO} object containing the details of the user to be registered.
   *
   * @return A {@code UserDTO} object representing the registered user.
   *
   * @throws ConflictException If the user already exists in the
   *     system and registration cannot proceed due to conflict.
   */
  UserDTO register(UserDTO userDTO) throws ConflictException;

  /**
   * Checks whether the specified user has teacher privileges.
   *
   * @param userDTO the user data transfer object to check
   * @return true if the user has teacher privileges, false otherwise
   */
  boolean userIsTeacher(UserDTO userDTO);

  /**
   * Checks whether the specified user has administrative privileges.
   *
   * @param userDTO the user data transfer object to check
   * @return true if the user has administrative privileges, false otherwise
   */
  boolean userIsAdmin(UserDTO userDTO);


  /**
   * Returns the number of students who have an internship in the specified school year.
   *
   * @param schoolYear the school year to check
   * @return the number of students who have an internship in the specified school year
   */
  int getStudentsWithInternship(String schoolYear);
}
