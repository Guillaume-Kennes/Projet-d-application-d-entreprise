package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.utils.AppBinderTest;
import be.vinci.pae.utils.exception.BusinessException;
import be.vinci.pae.utils.exception.ConflictException;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for UserUCC.
 */
public class UserUCCTest {

  private UserUCC userUCC;
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
    DomainFactory myDomainFactory = locator.getService(DomainFactory.class);
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

    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(userDTO.getEmail(), result.getEmail()),
        () -> assertEquals(userDTO.getPassword(), result.getPassword())
    );
  }

  /**
   * Test for login failure due to incorrect password.
   */
  @Test
  public void testLoginFailureForPassword() {
    userDTO.setEmail("chuqi.chups@student.vinci.be");
    userDTO.setPassword("$2a$10$3an9aQhFzbmHXVAqS4/o4OYicVWoR/OJBVOZ0052Fhm3T5ycz1MKu");

    when(userDAO.getUserByEmail("chuqi.chups@student.vinci.be")).thenReturn(userDTO);

    assertThrows(BusinessException.class,
        () -> userUCC.login("chuqi.chups@student.vinci.be", "12ksdjkglkjglkjwlkmjgmj3"));
  }

  /**
   * Test for login failure due to incorrect email.
   */
  @Test
  public void testLoginFailureForEmail() {
    assertAll(
        () -> assertNull(userDAO.getUserByEmail("kawtar.d@student.vinci.be")),
        () -> assertThrows(BusinessException.class,
            () -> userUCC.login("kawtar.d@student.vinci.be", "ghkfguezgfezbfouezf"))
    );
  }

  /**
   * Test for retrieving a user by their ID.
   */
  @Test
  public void getUserByIdTest_Success() {
    // Arrange
    int userId = 1;
    UserDTO expectedUser = mock(UserDTO.class);
    when(userDAO.getUserById(userId)).thenReturn(expectedUser);

    // Act
    UserDTO result = userUCC.getUserById(userId);

    // Assert
    assertEquals(expectedUser, result);
  }

  /**
   * Test for retrieving a user by their ID. Failure expected.
   */
  @Test
  public void getUserByIdTest_Failure() {
    // Arrange
    int userId = 1;
    when(userDAO.getUserById(userId)).thenThrow(new RuntimeException());

    // Act
    Exception exception = assertThrows(RuntimeException.class, () ->
        userUCC.getUserById(userId));

    // Assert
    assertNotNull(exception);
  }

  /**
   * Test for registering a new user. Success expected.
   */
  @Test
  public void testRegisterSuccess() {
    userDTO.setEmail("kawtar.dahman@student.vinci.be");
    userDTO.setPassword("$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa");

    when(userDAO.getUserByEmail("kawtar.dahman@student.vinci.be")).thenReturn(null);
    when(userDAO.register(userDTO)).thenReturn(userDTO);

    UserDTO registeredUser = userDAO.register(userDTO);

    assertAll(
        () -> assertEquals(userDTO.getEmail(), registeredUser.getEmail()),
        () -> assertEquals(userDTO.getPassword(), registeredUser.getPassword())
    );
  }

  /**
   * Test for registering a new user. Failure expected because the email address is already in
   * used.
   */
  @Test
  public void registerTest_existedEmail_failure() {
    userDTO.setEmail("laurent.leleux@vinci.be");
    userDTO.setPassword("$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa");

    when(userDAO.getUserByEmail("laurent.leleux@vinci.be")).thenReturn(userDTO);

    assertThrows(ConflictException.class, () -> userUCC.register(userDTO));
  }

  /**
   * Test for registering a new user. Failure expected because the email address is invalid.
   */
  @Test
  public void registerTest_invalidEmail_failure() {
    // Arrange
    userDTO.setEmail("laurent.leleux@gmail.be");
    userDTO.setPassword("$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa");

    when(userDAO.getUserByEmail(userDTO.getEmail())).thenReturn(null);

    // Act and Assert
    BusinessException exception = assertThrows(BusinessException.class, () -> userUCC.register(userDTO));
    assertEquals("HTTP 400 Bad Request", exception.getMessage());
  }

  /**
   * Test for registering a new user. Failure expected because the email address is invalid.
   */
  @Test
  public void registerTest_studentRole_success() {
    // Arrange
    userDTO.setEmail("kawtar.dahman@student.vinci.be");
    userDTO.setPassword("$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa");

    when(userDAO.getUserByEmail(userDTO.getEmail())).thenReturn(null);
    when(userDAO.register(userDTO)).thenReturn(userDTO);

    // Act
    UserDTO result = userUCC.register(userDTO);

    // Assert
    assertAll(
        () -> assertEquals(userDTO.getEmail(), result.getEmail()),
        () -> assertEquals(userDTO.getPassword(), result.getPassword()),
        () -> assertEquals("Etudiant", result.getRole())
    );
  }

  /**
  @Test
  public void registerTest_teacherRole_success() {
    // Arrange
    userDTO.setEmail("laurent.leleux@vinci.be");
    userDTO.setPassword("$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa");

    when(userDAO.getUserByEmail(userDTO.getEmail())).thenReturn(null);
    when(userDAO.register(userDTO)).thenReturn(userDTO);

    // Act
    UserDTO result = userUCC.register(userDTO);

    System.out.println("voici le role de l'utilisateur : ");
    System.out.println(userDTO.getRole());

    // Assert
    assertAll(
        () -> assertEquals(userDTO.getEmail(), result.getEmail()),
        () -> assertEquals(userDTO.getPassword(), result.getPassword()),
        () -> assertNull(result.getRole())
    );
  }*/

  /**
   * Test for registering a new user. Failure expected because the email address is invalid.
   */
  @Test
  public void registerTest_hashedPassword_success() {
    // Arrange
    userDTO.setEmail("kawtar.dahman@student.vinci.be");
    userDTO.setPassword("$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa");

    when(userDAO.getUserByEmail(userDTO.getEmail())).thenReturn(null);
    when(userDAO.register(userDTO)).thenReturn(userDTO);

    // Act
    UserDTO result = userUCC.register(userDTO);

    // Assert
    assertEquals(userDTO.getPassword(), result.getPassword());
  }

  /**
   * Test for registering a new user. Failure expected because the email address is invalid.
   */
  @Test
  public void getAllUsersTest_Succes() {
    // Arrange
    List<UserDTO> expecetedUsers = userDAO.getAllUsers();
    when(userDAO.getAllUsers()).thenReturn(expecetedUsers);

    // Act
    List<UserDTO> result = userUCC.getAllUsers();

    // Assert
    assertEquals(expecetedUsers, result);
  }


  /**
   * Test for getting all users. Failure expected.
   */
  @Test
  public void getAllUsersTest_Failure() {
    // Arrange
    when(userDAO.getAllUsers()).thenThrow(new RuntimeException());

    //Act
    Exception exception = assertThrows(RuntimeException.class, () ->
        userUCC.getAllUsers());

    // Assert
    assertNotNull(exception);
    reset(userDAO);
  }


  /**
   * Test for getting all students. Success expected.
   */
  @Test
  public void getStudentWithInternshipTest_Success() {
    // Arrange
    String schoolYear = "2023-2024";
    int expectedNumber = 0;
    when(userDAO.getStudentsWithInternship(schoolYear)).thenReturn(expectedNumber);

    // Act
    int result = userUCC.getStudentsWithInternship(schoolYear);

    // Assert
    assertEquals(expectedNumber, result);
  }

  /**
   * Test for getting all students. Failure expected.
   */
  @Test
  public void getStudentWithInternshipTest_Failed() {
    // Arrange
    String schoolYear = "2023-2024";
    when(userDAO.getStudentsWithInternship(schoolYear)).thenThrow(new RuntimeException());

    // Assert
    assertThrows(RuntimeException.class, () -> {
      userUCC.getStudentsWithInternship(schoolYear);
    });
    reset(userDAO);
  }

  /**
   * Test for updating a user's password. Success expected.
   */
  @Test
  public void getStudentWithoutInternshipTest_Success() {
    // Arrange
    String schoolYear = "2023-2024";
    int expectedNumber = 0;
    when(userDAO.getStudentsWithoutInternship(schoolYear)).thenReturn(expectedNumber);

    // Act
    int result = userUCC.getStudentsWithoutInternship(schoolYear);

    // Assert
    assertEquals(expectedNumber, result);
  }

  /**
   * Test for updating a user's password. Failure expected.
   */
  @Test
  public void getStudentWithoutInternshipTest_Failed() {
    // Arrange
    String schoolYear = "2023-2024";
    when(userDAO.getStudentsWithoutInternship(schoolYear)).thenThrow(new RuntimeException());

    // Assert
    assertThrows(RuntimeException.class, () -> {
      userUCC.getStudentsWithoutInternship(schoolYear);
    });
    reset(userDAO);
  }

  /**
   * Test for updating a user's password. Success expected.
   */
  @Test
  public void updatePasswordTest_Success() {
    // Arrange
    UserDTO user = userDTO;
    String password = "newPassword";
    doAnswer(invocation -> {
      user.setPassword(password); // Met à jour le mot de passe de userDTO
      return null;
    }).when(userDAO).updatePassword(user, password);

    // Act
    userUCC.updatePassword(user, password);

    // Assert
    assertAll(
        () -> verify(userDAO, times(1)).updatePassword(user, password),
        () -> assertEquals(password, user.getPassword())
    );
  }

  /**
   * Test for updating a user's password. Failure expected.
   */
  @Test
  public void updatePasswordTest_Failure() {
    // Arrange
    UserDTO user = userDTO;
    String password = "newPassword";
    doThrow(new RuntimeException()).when(userDAO).updatePassword(user, password);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> userUCC.updatePassword(user, password));
  }


  /**
   * Test for updating a user's phone number. Success expected.
   */
  @Test
  public void updatePhoneNumberTest_Success() {
    // Arrange
    UserDTO user = userDTO;
    String phoneNumber = "1234567890";
    doAnswer(invocation -> {
      user.setPhoneNumber(phoneNumber);
      return null;
    }).when(userDAO).updatePhoneNumber(user, phoneNumber);

    // Act
    userUCC.updatePhoneNumber(user, phoneNumber);

    // Assert
    assertAll(
        () -> verify(userDAO, times(1)).updatePhoneNumber(user, phoneNumber),
        () -> assertEquals(phoneNumber, user.getPhoneNumber())
    );
  }

  /**
   * Test for updating a user's phone number. Failure expected.
   */
  @Test
  public void updatePhoneNumberTest_Failure() {
    // Arrange
    UserDTO user = userDTO;
    String phoneNumber = "1234567890";
    doThrow(new RuntimeException()).when(userDAO).updatePhoneNumber(user, phoneNumber);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> userUCC.updatePhoneNumber(user, phoneNumber));
  }


  /**
   * Test for getting all students. Success expected.
   */
  @Test
  public void userIsTeacherTest_Success() {
    // Arrange
    UserDTO user = userDTO;
    user.setRole("Professeur");

    // Act
    boolean result = userUCC.userIsTeacher(user);

    // Assert
    assertEquals(true, result);
  }


  /**
   * Test for getting all students. Failure expected.
   */
  @Test
  public void userIsTeacherTest_Failure() {
    // Arrange
    UserDTO user = userDTO;
    user.setRole("Etudiant");

    // Act
    boolean result = userUCC.userIsTeacher(user);

    // Assert
    assertEquals(false, result);
  }


  /**
   * Test for getting all students. Success expected.
   */
  @Test
  public void userIsAdminTest_Success() {
    // Arrange
    UserDTO user = userDTO;
    user.setRole("Administratif");

    // Act
    boolean result = userUCC.userIsAdmin(user);

    // Assert
    assertEquals(true, result);
  }


  /**
   * Test for getting all students. Failure expected.
   */
  @Test
  public void userIsAdminTest_Failure() {
    // Arrange
    UserDTO user = userDTO;
    user.setRole("Etudiant");

    // Act
    boolean result = userUCC.userIsAdmin(user);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void getStudentByAcademicYearTest_Success() {
    // Arrange
    String academicYear = "2023-2024";
    List<UserDTO> expectedUsers = userDAO.getStudentsByAcademicYear(academicYear);
    when(userDAO.getStudentsByAcademicYear(academicYear)).thenReturn(expectedUsers);

    // Act
    List<UserDTO> result = userUCC.getStudentsByAcademicYear(academicYear);

    // Assert
    assertEquals(expectedUsers, result);
  }

  @Test
  public void getStudentByAcademicYearTest_Failure() {
    // Arrange
    String academicYear = "2023-2024";
    when(userDAO.getStudentsByAcademicYear(academicYear)).thenThrow(new RuntimeException());

    // Act
    assertThrows(RuntimeException.class, () ->
        userUCC.getStudentsByAcademicYear(academicYear));

    reset(userDAO);
  }

}
