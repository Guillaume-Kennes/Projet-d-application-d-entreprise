package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import be.vinci.pae.business.ucc.InternshipSupervisorUCC;
import be.vinci.pae.dal.InternshipSupervisorDAO;
import be.vinci.pae.utils.AppBinderTest;
import jakarta.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for InternshipSupervisorUCC.
 */
public class InternshipSupervisorUCCTest {

  private InternshipSupervisorUCC internshipSupervisorUCC;
  private InternshipSupervisorDAO internshipSupervisorDAO;
  private InternshipSupervisorDTO internshipSupervisorDTO;
  private DomainFactory domainFactory;

  /**
   * Method executed before each test.
   */
  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());

    internshipSupervisorUCC = locator.getService(InternshipSupervisorUCC.class);
    domainFactory = locator.getService(DomainFactory.class);
    internshipSupervisorDAO = locator.getService(InternshipSupervisorDAO.class);
    internshipSupervisorDTO = domainFactory.getInternshipSupervisor();
  }

  /**
   * Test for creating an internship supervisor successfully.
   */
  @Test
  public void testCreateAnInternshipSupervisor_success() {
    internshipSupervisorDTO.setFirstName("John");
    internshipSupervisorDTO.setLastName("Doe");
    internshipSupervisorDTO.setPhoneNumber("1234567890");
    internshipSupervisorDTO.setEmail("ezagzrtb");
    internshipSupervisorDTO.setCompany(1);

    when(internshipSupervisorDAO.insertSupervisor("John",
        "Doe", "1234567890", "ezagzrtb", 1))
        .thenReturn(internshipSupervisorDTO);

    InternshipSupervisorDTO result = internshipSupervisorUCC.createAnInternshipSupervisor(
        internshipSupervisorDTO.getFirstName(), internshipSupervisorDTO.getLastName(),
        internshipSupervisorDTO.getPhoneNumber(), internshipSupervisorDTO.getEmail(),
        internshipSupervisorDTO.getCompany());

    assertEquals(internshipSupervisorDTO, result);
  }

  /**
   * Test for creating an internship supervisor with missing information.
   */
  @Test
  public void testCreatestCreateAnInternshipSupervisor_nullFirstName_failed() {
    assertThrows(WebApplicationException.class, () -> {
      internshipSupervisorUCC.createAnInternshipSupervisor(null,
          "Doe", "1234567890", "ezagzrtb", 1);
    });
  }

  /**
   * Test for creating an internship supervisor with missing information.
   */
  @Test
  public void testCreatestCreateAnInternshipSupervisor_nullLastName_failed() {
    assertThrows(WebApplicationException.class, () -> {
      internshipSupervisorUCC.createAnInternshipSupervisor("John",
          null, "1234567890", "ezagzrtb", 1);
    });
  }

  /**
   * Test for creating an internship supervisor with missing information.
   */
  @Test
  public void testCreatestCreateAnInternshipSupervisor_nullPhoneNumber_failed() {
    assertThrows(WebApplicationException.class, () -> {
      internshipSupervisorUCC.createAnInternshipSupervisor("John",
          "Doe", null, "ezagzrtb", 1);
    });
  }

  /**
   * Test for creating an internship supervisor with missing information.
   */
  @Test
  public void testCreatestCreateAnInternshipSupervisor_nullCompany_failed() {
    assertThrows(WebApplicationException.class, () -> {
      internshipSupervisorUCC.createAnInternshipSupervisor("John",
          "Doe", "1234567890", "ezagzrtb", 0);
    });
  }

  @Test
  public void testGetAllInternshipSupervisors_success() {
    // Arrange
    List<InternshipSupervisorDTO> expectedSupervisors = new ArrayList<>();
    expectedSupervisors.add(internshipSupervisorDTO);
    when(internshipSupervisorDAO.getAllInternshipSupervisors()).thenReturn(expectedSupervisors);

    // Act
    List<InternshipSupervisorDTO> result = internshipSupervisorUCC.getAllInternshipSupervisors();

    // Assert
    assertEquals(expectedSupervisors, result);
  }

  @Test
  public void testGetAllInternshipSupervisors_failure() {
    // Arrange
    when(internshipSupervisorDAO.getAllInternshipSupervisors()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(WebApplicationException.class,
        () -> internshipSupervisorUCC.getAllInternshipSupervisors());
  }


}
