package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of the UserDAO interface. Provides methods for retrieving user-related data from
 * the database.
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
   * @return a user with the specified email address, or null if not found.
   * @throws FatalException if an SQL exception occurs while accessing the database.
   */
  public UserDTO getUserByEmail(String email) {

    // PreparedStatement preparedStatement = dalServices.getPreparedStatement(
    //  "SELECT * FROM pae.users u WHERE u.email = ?");
    //   try {
    //     preparedStatement.setString(1, email);
    //   } catch (SQLException e) {
    //     throw new FatalException(e);
    //   }
    //
    //   UserDTO user = myDomainFactory.getUser();
    //   try (ResultSet resultSet = preparedStatement.executeQuery()) {
    //
    //     if (resultSet.next()) {
    //       user = userInfos(resultSet);
    //     } else {
    //       user = null;
    //     }
    //
    //   } catch (SQLException e) {
    //     throw new FatalException(e);
    //   }
    // return user;
    try {
      String query = """
          SELECT *
          FROM pae.users u
          WHERE u.email = ?
          """;
      try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(query)) {
        preparedStatement.setString(1, email);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          if (resultSet.next()) {
            return userInfos(resultSet);
          }
        }
      }
      return null;
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  /**
   * Method to retrieve user information from a ResultSet and map it to a UserDTO object.
   *
   * @param resultSet The ResultSet containing user information.
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
      throw new FatalException(e);
    }

    return userDTO;
  }

  /**
   * Method to retrieve a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   * @return A UserDTO object representing the user with the specified ID, or null if not found.
   * @throws FatalException if the user is not found in the database.
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
      throw new FatalException(e);
    }
    return null;
  }

  /**
   * Retrieves a list of all users from the database.
   *
   * @return A list of UserDTO objects representing all users.
   */
  public List<UserDTO> getAllUsers() {
    List<UserDTO> usersList = new ArrayList<>();
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users");
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        UserDTO userDTO = myDomainFactory.getUser();
        userDTO.setEmail(resultSet.getString("email"));
        userDTO.setLastName(resultSet.getString("last_name"));
        userDTO.setFirstName(resultSet.getString("first_name"));
        userDTO.setPhoneNumber(resultSet.getString("phone_number"));
        userDTO.setRole(resultSet.getString("role"));
        userDTO.setId(resultSet.getInt("id_user"));
        usersList.add(userDTO);
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return usersList;
  }

  /**
   * Registers a new user in the database.
   *
   * @param userDTO The UserDTO object containing user information.
   * @return A UserDTO object representing the registered user, or null if registration fails.
   */
  public UserDTO register(UserDTO userDTO) {

    try {
      String query = "INSERT INTO pae.users (email, password, last_name, first_name, "
          + "phone_number, registration_date, role, version_users) "
          + "VALUES(?, ?, ?, ?, ?, NOW(), ?, ?) RETURNING *";

      String query2 = "INSERT INTO pae.inscriptions_ue "
          + "(student, school_year, version_inscriptions_ue) "
          + "VALUES(?, '2023-2024', ?)";
      // schoolyear hardcodée mais à changer dans le futur

      try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(query)) {
        preparedStatement.setString(1, userDTO.getEmail());
        preparedStatement.setString(2, userDTO.getPassword());
        preparedStatement.setString(3, userDTO.getLastName());
        preparedStatement.setString(4, userDTO.getFirstName());
        preparedStatement.setString(5, userDTO.getPhoneNumber());
        preparedStatement.setString(6, userDTO.getRole());
        preparedStatement.setInt(7, userDTO.getVersionNumber());

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          if (resultSet.next()) {
            userDTO = userInfos(resultSet);
            userDTO.setRole(userDTO.getRole());
          } else {
            userDTO = null;
          }
        }
        try (PreparedStatement preparedStatement2 = dalServices.getPreparedStatement(query2)) {
          preparedStatement2.setInt(1, userDTO.getId());
          preparedStatement2.setInt(2, userDTO.getVersionNumber());
          preparedStatement2.execute();
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return userDTO;
  }

  /**
   * Retrieves the number of students with an internship for a given school year.
   *
   * @param schoolYear The school year for which to retrieve the number of students with an internship.
   * @return The number of students with an internship for the specified school year.
   */
  public int getStudentsWithInternship(String schoolYear) {
    int studentsWithInternships = 0;
    String query = "SELECT COUNT(iu.student) "
            + "FROM pae.inscriptions_ue iu, pae.contacts c "
            + "WHERE c.inscription_ue = iu.id_inscription_ue "
            + "AND c.state = 'accepté' AND iu.school_year = ?";
    System.out.println("QUERY = " + query);
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(query)) {
      preparedStatement.setString(1, schoolYear);
      System.out.println("PREPARED STATEMENT = " + preparedStatement);
      System.out.println("SCHOOL YEAR = " + schoolYear);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          studentsWithInternships = resultSet.getInt(1);
          System.out.println("STUDENT WITH INTERNSHIPS : " + studentsWithInternships);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return studentsWithInternships;
  }

  @Override
  public int getStudentsWithoutInternship(String schoolYear) {
    int studentsWithoutInternships = 0;
    String query = "SELECT COUNT(iu.student) "
        + "FROM pae.inscriptions_ue iu, pae.contacts c "
        + "WHERE c.inscription_ue = iu.id_inscription_ue "
        + "AND c.state != 'accepté' AND iu.school_year = ?";
    System.out.println("QUERY = " + query);
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(query)) {
      preparedStatement.setString(1, schoolYear);
      System.out.println("PREPARED STATEMENT = " + preparedStatement);
      System.out.println("SCHOOL YEAR = " + schoolYear);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          studentsWithoutInternships = resultSet.getInt(1);
          System.out.println("STUDENT WITHOUT INTERNSHIPS : " + studentsWithoutInternships);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return studentsWithoutInternships;
  }

}