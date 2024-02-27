package be.vinci.pae.dal;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDAOImpl implements UserDAO {

  //private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  //private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;

  public UserDAOImpl() {
  }


  /**
   * @param email
   * @return
   */
  public UserDTO getUserByEmail(String email) {

    String sql_query =
    """
    SELECT u.id_user,
    u.email,
    u.password,
    u.last_name,
    u.first_name,
    u.phone_number,
    u.registration_date,
    u.role
    FROM pae.users u WHERE u.email = ?
    """;
    UserDTO user = myDomainFactory.getUser();
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(sql_query)) {
      preparedStatement.setString(1, email);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id_user");
        String emailUser = rs.getString("email");
        String password = rs.getString("password");
        String lastName = rs.getString("last_name");
        String firstName = rs.getString("first_name");
        String phone = rs.getString("phone_number");
        String registration_date = rs.getString("registration_date");
        String role = rs.getString("role");
        user.setId(id);
        user.setEmail(emailUser);
        user.setPassword(password);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPhoneNumber(phone);
        user.setRegistration_date(registration_date);
        user.setRole(role);
        rs.close();
        preparedStatement.close();
      } else {
        System.out.println("L'info n'a pas été trouvé");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }

}