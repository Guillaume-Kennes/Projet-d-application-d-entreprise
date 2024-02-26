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

  //private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  //private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private DomainFactory myDomainFactory;

  private Connection connection;
  @Inject
  private DALServices dalServices;

  public UserDAOImpl() {
  }


  /**
   * @param email
   * @return
   */
  public UserDTO getUserByEmail(String email) {

    String sql_query = "SELECT * FROM pae.utilisateurs u WHERE u.email = ?";
    UserDTO user = myDomainFactory.getUser();
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(sql_query)) {
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        rs.close();
        preparedStatement.close();
      } else {
        System.out.println("Linfo n'a pas étét trouvé");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }

}