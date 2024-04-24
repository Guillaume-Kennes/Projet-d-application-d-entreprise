package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.ucc.InternshipUCC;
import be.vinci.pae.dal.InternshipDAO;
import be.vinci.pae.utils.AppBinderTest;
import be.vinci.pae.utils.exception.NotFoundException;
import java.sql.SQLException;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * Test class for InternshipUCC.
 */
public class InternshipUCCTest {

  private InternshipUCC internshipUCC;
  private DomainFactory domainFactory;
  private InternshipDAO internshipDAO;
  private InternshipDTO expectedInternship;

  /**
   * Method executed before each test.
   */
  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());

    internshipUCC = locator.getService(InternshipUCC.class);
    internshipDAO = locator.getService(InternshipDAO.class);
    domainFactory = locator.getService(DomainFactory.class);
    expectedInternship = domainFactory.getInternship();
  }

  /**
   * Test for successful retrieving of the internship.
   */
  @Test
  public void testGetInternshipByUserId() throws SQLException {
    int userId = 7;

    expectedInternship.setId(3);
    when(internshipDAO.getInternshipByUserId(userId)).thenReturn(expectedInternship);

    InternshipDTO result = internshipUCC.getInternshipByUserId(userId);

    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(expectedInternship.getId(), result.getId())
    );
  }

  /**
   * Test for successful process when the user doesn't have an internship.
   */
  @Test
  public void testGetInternshipByUserIdWhenNoInternship() throws SQLException {
    int userId = 5;

    InternshipDTO result = internshipUCC.getInternshipByUserId(userId);

    assertNull(result);
  }

  /**
   * Test for successful retrieving of the internship.
   */
  @Test
  public void testGetInternshipByUserId_Success() throws SQLException {
    // Arrange
    int userId = 1;
    InternshipDTO expectedInternship = internshipDAO.getInternshipByUserId(userId);
    when(internshipDAO.getInternshipByUserId(userId)).thenReturn(expectedInternship);

    // Act
    InternshipDTO result = internshipUCC.getInternshipByUserId(userId);

    // Assert
    assertEquals(expectedInternship, result);
  }

  /**
   * Test for retrieving an internship. Failure expected.
   */
  @Test
  public void testGetInternshipByUserId_Failure() throws SQLException {
    // Arrange
    int userId = 1;
    when(internshipDAO.getInternshipByUserId(userId)).thenThrow(new RuntimeException());

    // Act
    Exception exception = assertThrows(RuntimeException.class, () ->
        internshipUCC.getInternshipByUserId(userId));

    // Assert
    assertNotNull(exception);
  }

  @Test
  public void testCreateAnIntership_Success() {
    // Arrange
    int contact = 1;
    int supervisor = 2;
    String project = "Project";
    java.sql.Date signatureDate = new java.sql.Date(2021, 1, 1);

    InternshipDTO expectedInternship = domainFactory.getInternship();
    expectedInternship.setContact(contact);
    expectedInternship.setSupervisor(supervisor);
    expectedInternship.setProject(project);
    expectedInternship.setSignatureDate(signatureDate);

    when(internshipDAO.createAnInternship(contact, supervisor, project, signatureDate))
        .thenReturn(expectedInternship);

    // Act
    InternshipDTO result = internshipUCC.createAnInternship(contact, supervisor, project, signatureDate);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(expectedInternship, result)
    );
  }

  @Test
  public void testCreateAnIntership_nullContact_Failure() {
    // Arrange
    int contact = 0;
    int supervisor = 2;
    String project = "Project";
    java.sql.Date signatureDate = new java.sql.Date(2021, 1, 1);

    when(internshipDAO.createAnInternship(contact, supervisor, project, signatureDate))
        .thenThrow(new RuntimeException());

    // Act
    Exception exception = assertThrows(RuntimeException.class, () ->
        internshipUCC.createAnInternship(contact, supervisor, project, signatureDate));

    // Assert
    assertNotNull(exception);
  }

  @Test
  public void testCreateAnIntership_nullSupervisor_Failure() {
    // Arrange
    int contact = 1;
    int supervisor = 0;
    String project = "Project";
    java.sql.Date signatureDate = new java.sql.Date(2021, 1, 1);

    when(internshipDAO.createAnInternship(contact, supervisor, project, signatureDate))
        .thenThrow(new RuntimeException());

    // Act
    Exception exception = assertThrows(RuntimeException.class, () ->
        internshipUCC.createAnInternship(contact, supervisor, project, signatureDate));

    // Assert
    assertNotNull(exception);
  }

  @Test
  public void testCreateAnIntership_nullSignatureDate_Failure() {
    // Arrange
    int contact = 1;
    int supervisor = 2;
    String project = "Project";
    java.sql.Date signatureDate = null;

    when(internshipDAO.createAnInternship(contact, supervisor, project, signatureDate))
        .thenThrow(new RuntimeException());

    // Act
    Exception exception = assertThrows(RuntimeException.class, () ->
        internshipUCC.createAnInternship(contact, supervisor, project, signatureDate));

    // Assert
    assertNotNull(exception);
  }

  @Test
  public void testCreateOrModifyAnIntership_Success() {
    // Arrange
    InternshipDTO realInternship = domainFactory.getInternship();
    InternshipDTO internship = spy(realInternship);

    String subject = "Subject";

    InternshipDTO result =
        internshipUCC.createOrModifyAnInternship(internship, subject);

    //Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(internship, result),
        () -> verify(internship).setProject(subject),
        () -> verify(internshipDAO).update(internship)
    );
  }

  @Test
  public void testCreateOrModifyAnInternship_nullInternship_Failure() {
    //Arrange
    InternshipDTO internship = null;
    String subject = "Subject";

    assertThrows(NotFoundException.class, () ->
        internshipUCC.createOrModifyAnInternship(internship, subject)
    );
  }

  @Test
  public void testCreateOrModifyAnInternship_nullSubject_Failure() {
    //Arrange
    InternshipDTO internship = domainFactory.getInternship();
    String subject = null;

    assertThrows(NotFoundException.class, () ->
        internshipUCC.createOrModifyAnInternship(internship, subject)
    );
  }

  @Test
  public void testGetInternshipById() {
    // Arrange
    int internshipId = 1;
    InternshipDTO expectedInternship = internshipDAO.getInternshipById(internshipId);
    when(internshipDAO.getInternshipById(internshipId)).thenReturn(expectedInternship);

    // Act
    InternshipDTO result = internshipUCC.getInternshipById(internshipId);

    // Assert
    assertEquals(expectedInternship, result);
  }


  @Test
  public void testAllInternships() {
    // Arrange
    List<InternshipDTO> expectedInternships = internshipDAO.getAllInternships();
    when(internshipDAO.getAllInternships()).thenReturn(expectedInternships);

    // Act
    List<InternshipDTO> result = internshipUCC.getAllInternships();

    // Assert
    assertEquals(expectedInternships, result);
  }

}
