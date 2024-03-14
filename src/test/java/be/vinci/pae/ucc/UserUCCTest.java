package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.utils.AppBinderTest;
import be.vinci.pae.utils.exception.UnauthorizedException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for UserUCC.
 */
public class UserUCCTest {

  private UserUCC userUCC;
  private DomainFactory myDomainFactory;
  private UserDAO userDAO;
  private UserDTO userDTO;


  /**
   * Method executed before each test.
   */
  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());

    userUCC = locator.getService(UserUCC.class);
    userDAO = locator.getService(UserDAO.class);
    myDomainFactory = locator.getService(DomainFactory.class);
    userDTO = myDomainFactory.getUser();
  }

  /**
   * Test for successful login.
   */
  @Test
  public void testLoginSuccess() {
    userDTO.setEmail("chuqi.chups@student.vinci.be");
    userDTO.setPassword("$2a$10$3an9aQhFzbmHXVAqS4/o4OYicVWoR/OJBVOZ0052Fhm3T5ycz1MKu");

    when(userDAO.getUserByEmail("chuqi.chups@student.vinci.be")).thenReturn(userDTO);

    UserDTO result = userUCC.login("chuqi.chups@student.vinci.be", "Azertyui1_");

    assertNotNull(result);
    assertEquals(userDTO.getEmail(), result.getEmail());
    assertEquals(userDTO.getPassword(), result.getPassword());

  }

  /**
   * Test for login failure due to incorrect password.
   */
  @Test
  public void testLoginFailureForPassword() {
    userDTO.setEmail("chuqi.chups@student.vinci.be");
    userDTO.setPassword("$2a$10$jTSImXQiYMuPdgtrfA9t1u0lln65JDLUyzvir9t21uENvF0yIX.na");

    when(userDAO.getUserByEmail("chuqi.chups@student.vinci.be")).thenReturn(userDTO);

    assertThrows(UnauthorizedException.class,
        () -> userUCC.login("chuqi.chups@student.vinci.be", "12ksdjkglkjglkjwlkmjgmj3"));
  }

  /**
   * Test for login failure due to incorrect email.
   */
  @Test
  public void testLoginFailureForEmail() {
    assertNull(userDAO.getUserByEmail("kawtar.d@student.vinci.be"));
    assertThrows(UnauthorizedException.class,
        () -> userUCC.login("kawtar.d@student.vinci.be", "ghkfguezgfezbfouezf"));
  }

  /**
   * Test for retrieving a user by their ID.
   */
  @Test
  public void testGetUserById() {
    userDTO.setId(1);

    when(userDAO.getUserById(1)).thenReturn(userDTO);
    UserDTO result = userUCC.getUserById(1);
    assertNotNull(result);
    assertEquals(userDTO.getId(), result.getId());
  }
}
