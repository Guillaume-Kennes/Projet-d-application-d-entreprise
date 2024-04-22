package be.vinci.pae.dal;

import be.vinci.pae.business.domain.UserDTO;
import java.sql.ResultSet;
import java.util.List;

/**
 * Represents a Data Access Object (DAO) for managing user-related data. Provides methods to
 * retrieve user information based on email or ID.
 */
public interface UserDAO {

  /**
   * Returns the user corresponding to the given email.
   *
   * @param email the user's email
   * @return the user corresponding to the given email
   */
  UserDTO getUserByEmail(String email);


  /**
   * Returns the information of a user.
   *
   * @param resultSet the given resultSet
   * @return the user corresponding to that result set
   */
  UserDTO userInfos(ResultSet resultSet);


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
   * @return A list containing UserDTO objects representing all users.
   *     If no users are found, the list will be empty.
   */
  List<UserDTO> getAllUsers();


  /**
   * Registers a new user in the database.
   *
   * @param userDTO The UserDTO object containing user information.
   * @return A UserDTO object representing the registered user, or null if registration fails.
   */
  UserDTO register(UserDTO userDTO);

  /**
   * Retrieves the number of students with an internship for a given school year.
   *
   * @param schoolYear The school year for which to retrieve the number of students with an
   *                   internship.
   * @return The number of students with an internship for the specified school year.
   */
  int getStudentsWithInternship(String schoolYear);

  /**
   * Retrieves the number of students without an internship for a given school year.
   *
   * @param schoolYear The school year for which to retrieve the number of students without an
   *                   internship.
   * @return The number of students without an internship for the specified school year.
   */
  int getStudentsWithoutInternship(String schoolYear);

  /**
   * Update a user's phone number.
   *
   * @param user The user whose phone number must be updated.
   * @param phoneNumber The new phone number.
   */
  void updatePhoneNumber(UserDTO user, String phoneNumber);

  /**
   * Update a user's password.
   *
   * @param userDTO The user whose password must be updated.
   * @param password The new password.
   */
  void updatePassword(UserDTO userDTO, String password);
}

