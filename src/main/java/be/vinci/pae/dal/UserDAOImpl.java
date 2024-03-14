package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {


  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private DALBackServices dalServices;


  /**
   * @param email the email of the user researched
   * @return the user researched
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

  public UserDTO userInfos(ResultSet resultSet){
    UserDTO userDTO = myDomainFactory.getUser();

    try {
      userDTO.setId(resultSet.getInt("id_user"));
      userDTO.setLastName(resultSet.getString("last_name"));
      userDTO.setFirstName(resultSet.getString("first_name"));
      userDTO.setEmail(resultSet.getString("email"));
      userDTO.setPassword(resultSet.getString("password"));
      userDTO.setPhoneNumber(resultSet.getString("phone_number"));
      userDTO.setRegistrationDate(resultSet.getString("registration_date"));
      userDTO.setRole(resultSet.getString("role"));
    }catch(SQLException e){ //DEMANDER AU PROF quelle exception
      e.getMessage();
    }

    return userDTO;
  }

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
}