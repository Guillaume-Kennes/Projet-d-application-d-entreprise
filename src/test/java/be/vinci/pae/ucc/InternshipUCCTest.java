package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.ucc.InternshipUCC;
import be.vinci.pae.dal.InternshipDAO;
import be.vinci.pae.utils.AppBinderTest;
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
  @Mock
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
  public void testGetInternshipByUserId() {
    int userId = 7;

    expectedInternship.setId(3);
    when(internshipDAO.getInternshipByUserId(userId)).thenReturn(expectedInternship);

    InternshipDTO result = internshipUCC.getInternshipByUserId(userId);

    assertNotNull(result);
    assertEquals(expectedInternship.getId(), result.getId());
  }

  /**
   * Test for successful process when the user doesn't have an internship.
   */
  @Test
  public void testGetInternshipByUserIdWhenNoInternship() {
    int userId = 5;

    InternshipDTO result = internshipUCC.getInternshipByUserId(userId);

    assertNull(result);
  }

  @Test
  public void getInternshipByUserIdTest_Success() {
    // Arrange
    int userId = 1;
    InternshipDTO expectedInternship = internshipDAO.getInternshipByUserId(userId);
    when(internshipDAO.getInternshipByUserId(userId)).thenReturn(expectedInternship);

    // Act
    InternshipDTO result = internshipUCC.getInternshipByUserId(userId);

    // Assert
    assertEquals(expectedInternship, result);
  }

  @Test
  public void getInternshipByUserIdTest_Failure() {
    // Arrange
    int userId = 1;
    when(internshipDAO.getInternshipByUserId(userId)).thenThrow(new RuntimeException());

    // Act
    Exception exception = assertThrows(RuntimeException.class, () -> {
      internshipUCC.getInternshipByUserId(userId);
    });

    // Assert
    assertNotNull(exception);
  }
}
