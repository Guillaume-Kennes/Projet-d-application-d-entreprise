package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Implementation of the UserDAO interface.
 * Provides methods for retrieving user-related data from the database.
 */
public class UserDAOImpl implements UserDAO {


  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private DALBackServices dalServices;



  /**
   * Retrieves a user by their email address from the database.
   *
   * @param email The email address of the user to retrieve.
   *
   * @return a user with the specified email address, or null if not found.
   *
   * @throws RuntimeException if an SQL exception occurs while accessing the database.
   */
  public UserDTO getUserByEmail(String email) {

    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users u WHERE u.email = ?");
    try {
      preparedStatement.setString(1, email);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    UserDTO user = myDomainFactory.getUser();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {

      if (resultSet.next()) {
        user = userInfos(resultSet);
      } else {
        user = null;
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    } finally {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return user;
  }

  /**
   * Method to retrieve user information from a ResultSet and map it to a UserDTO object.
   *
   * @param resultSet The ResultSet containing user information.
   *
   * @return A UserDTO object populated with user information from the ResultSet.
   */
  public UserDTO userInfos(ResultSet resultSet) {
    UserDTO userDTO = myDomainFactory.getUser();

    try {
      userDTO.setId(resultSet.getInt("id_user"));
      userDTO.setLastName(resultSet.getString("last_name"));
      userDTO.setFirstName(resultSet.getString("first_name"));
      userDTO.setEmail(resultSet.getString("email"));
      userDTO.setPassword(resultSet.getString("password"));
      userDTO.setPhoneNumber(resultSet.getString("phone_number"));
      userDTO.setRegistrationDate(resultSet.getDate("registration_date"));
      userDTO.setRole(resultSet.getString("role"));
    } catch (SQLException e) {
      e.getMessage();
    }

    return userDTO;
  }

  /**
   * Method to retrieve a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   *
   * @return A UserDTO object representing the user with the specified ID, or null if not found.
   *
   * @throws IllegalArgumentException if the user is not found in the database.
   */
  public UserDTO getUserById(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users u WHERE u.id_user = ?");
    try {
      preparedStatement.setInt(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return userInfos(resultSet);
        }
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException("User not found");
    }
    return null;
  }


  /**
   * Registers a new user with the provided information.
   *
   * @return A UserDTO object representing the registered user.
   */
  public UserDTO register(UserDTO userDTO) {

    try {
      String query = "INSERT INTO pae.users (email, password, last_name, first_name, "
          + "phone_number, registration_date, role) "
          + "VALUES(?, ?, ?, ?, ?, NOW(), ?) RETURNING *";

      try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(query)) {
        preparedStatement.setString(1, userDTO.getEmail());
        preparedStatement.setString(2, userDTO.getPassword());
        preparedStatement.setString(3, userDTO.getLastName());
        preparedStatement.setString(4, userDTO.getFirstName());
        preparedStatement.setString(5, userDTO.getPhoneNumber());
        preparedStatement.setString(6, userDTO.getRole());

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          if (resultSet.next()) {
            userDTO = userInfos(resultSet);
          } else {
            userDTO = null;
          }
        }

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return userDTO;

  }
}