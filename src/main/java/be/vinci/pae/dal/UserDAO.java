package be.vinci.pae.dal;

import be.vinci.pae.business.domain.UserDTO;
import java.sql.ResultSet;

/**
 * Represents a Data Access Object (DAO) for managing user-related data.
 * Provides methods to retrieve user information based on email or ID.
 */
public interface UserDAO {

  /**
   * Returns the user corresponding to the given email.
   *
   * @param email the user's email
   *
   * @return the user corresponding to the given email
   */
  UserDTO getUserByEmail(String email);


  /**
   * Returns the information of a user.
   *
   * @param resultSet the given resultSet
   *
   * @return the user corresponding to that result set
   */
  UserDTO userInfos(ResultSet resultSet);



  /**
   * Returns the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the user corresponding to the id
   */
  UserDTO getUserById(int id);


  /**
   * Registers a new user with the provided information.
   *
   * @return A UserDTO object representing the registered user.
   */
  UserDTO register(UserDTO userDTO);

}

