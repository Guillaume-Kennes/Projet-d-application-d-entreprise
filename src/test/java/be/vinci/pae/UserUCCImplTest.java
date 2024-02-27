import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.UserDAO;
import jakarta.ws.rs.WebApplicationException;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserUCCImpl Tests")
public class UserUCCImplTest {

  private UserUCCImpl userUCC;
  private UserDAO userDAO;

  @BeforeEach
  void setUp() {
    userDAO = mock(UserDAO.class);
    userUCC = new UserUCCImpl();
    userUCC.setUserDAO(userDAO);
  }

  @Test
  @DisplayName("Login with valid user")
  void testLoginValidUser() {
    // Arrange
    String email = "test@example.com";
    String password = "secret";
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);

    // Stub the behavior of getUserByEmail
    when(userDAO.getUserByEmail(email)).thenReturn(user);

    // Act
    UserDTO result = userUCC.login(email, password);

    // Assert
    assertNotNull(result);
    assertEquals(email, result.getEmail());
  }

  @Test
  @DisplayName("Login with invalid user")
  void testLoginInvalidUser() {
    // Arrange
    String email = "test@example.com";
    String password = "wrongpassword";

    // Stub the behavior of getUserByEmail
    when(userDAO.getUserByEmail(email)).thenReturn(null);

    // Act and Assert
    assertThrows(WebApplicationException.class, () -> userUCC.login(email, password));
  }

}
