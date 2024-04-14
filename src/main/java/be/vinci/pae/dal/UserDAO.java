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
   * @return A list containing UserDTO objects representing all users. If no users are found, the
   *     list will be empty.
   */
  List<UserDTO> getAllUsers();


  /**
   * Registers a new user in the database.
   *
   * @param userDTO The UserDTO object containing user information.
   * @return A UserDTO object representing the registered user, or null if registration fails.
   */
  UserDTO register(UserDTO userDTO);

  int getStudentsWithInternship(String schoolYear);
}

