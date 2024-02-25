package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

  private static DomainFactory domainFactory = new DomainFactoryImpl();
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  String url = "jdbc:postgresql://coursinfo.vinci.be:5432/dbkawtar_dahman?user=kawtar_dahman";
  @Inject
  private DomainFactory myDomainFactory;

  private Connection connection;
  private PreparedStatement viewAllUsers;
  private PreparedStatement login;

  public UserDAOImpl() {
    try {
      connection = DriverManager.getConnection(url, "kawtar_dahman", "Groupe06");
      viewAllUsers = connection.prepareStatement("SELECT * FROM pae.utilisateurs");
      login = connection.prepareStatement("SELECT id, nom, prenom, email, telephone, date_inscription, role FROM pae.utilisateurs WHERE email = ?");

    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }

  public List<User> getAll(){
    List<User> usersList = new ArrayList<>();
    try (ResultSet resultSet = viewAllUsers.executeQuery()) {
      while (resultSet.next()) {

        User user = domainFactory.getUser();
        user.setEmail(resultSet.getString("email"));
        usersList.add(user);

      }
    } catch (Exception e) {
      System.exit(1);
    }
    return usersList;
  }

}