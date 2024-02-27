package be.vinci.pae;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.ucc.UserUCCImpl;
import be.vinci.pae.dal.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserUCCImplTest {
  private UserUCCImpl userUCC;
  private UserDAO userDAO;
  private User user;

  @BeforeEach
  public void setUp() {
    userDAO = Mockito.mock(UserDAO.class);
    user = Mockito.mock(User.class);
    userUCC = new UserUCCImpl();
  }

  @Test
  public void testLoginSuccess() {
    when(userDAO.getUserByEmail("chuqi.chups@student.vinci.be")).thenReturn(user);
    when(user.checkPassword("Azertyui1_")).thenReturn(true);

    User result = (User) userUCC.login("chuqi.chups@student.vinci.be", "Azertyui1_");

    assertEquals(user, result);
  }

  @Test
  public void testLoginFailure() {
    when(userDAO.getUserByEmail("chuqi.chups@student.vinci.be")).thenReturn(user);
    when(user.checkPassword("wrongpassword")).thenReturn(false);

    assertThrows(IllegalArgumentException.class, () -> {
      userUCC.login("chuqi.chups@student.vinci.be", "wrongpassword");
    });
  }

}
