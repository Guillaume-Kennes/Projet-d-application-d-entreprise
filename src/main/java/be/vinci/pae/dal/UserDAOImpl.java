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

  public UserDTO userInfos(ResultSet resultSet) throws SQLException {
    UserDTO userDTO = myDomainFactory.getUser();

    userDTO.setId(resultSet.getInt("id_user"));
    userDTO.setLastName(resultSet.getString("last_name"));
    userDTO.setFirstName(resultSet.getString("first_name"));
    userDTO.setEmail(resultSet.getString("email"));
    userDTO.setPassword(resultSet.getString("password"));

    return userDTO;
  }

}