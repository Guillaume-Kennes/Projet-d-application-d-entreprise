package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {


  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private DALServices dalServices;


  /**
   * @param email
   * @return
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
        return userInfos(resultSet);
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