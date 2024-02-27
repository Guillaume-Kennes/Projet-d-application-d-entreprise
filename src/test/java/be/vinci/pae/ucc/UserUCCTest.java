package be.vinci.pae.ucc;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.utils.AppBinderTest;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserUCCTest {
  private UserUCC userUCC;
  private DomainFactory myDomainFactory;
  private UserDAO userDAO;
  private UserDTO userDTO;


  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());

    userUCC = locator.getService(UserUCC.class);
    userDAO = locator.getService(UserDAO.class);
    myDomainFactory = locator.getService(DomainFactory.class);
    userDTO = myDomainFactory.getUser();
  }

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

  @Test
  public void testLoginFailure() {
    userDTO.setEmail("chuqi.chups@student.vinci.be");
    userDTO.setPassword("$2a$10$jTSImXQiYMuPdgtrfA9t1u0lln65JDLUyzvir9t21uENvF0yIX.na");

    when(userDAO.getUserByEmail("chuqi.chups@student.vinci.be")).thenReturn(userDTO);
    UserDTO result = userUCC.login("chuqi.chups@student.vinci.be", "123");

    assertNotNull(result);
    assertEquals(userDTO.getEmail(), result.getEmail());
    assertEquals(userDTO.getPassword(), result.getPassword());
  }
}
