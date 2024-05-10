package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.dal.CompanyDAO;
import be.vinci.pae.utils.AppBinderTest;
import be.vinci.pae.utils.exception.BusinessException;
import be.vinci.pae.utils.exception.NotFoundException;
import java.sql.SQLException;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The type Company ucc test.
 */
public class CompanyUCCTest {

  private CompanyUCC companyUCC;
  private DomainFactory domainFactory;
  private CompanyDAO companyDAO;
  private CompanyDTO companyDTO;

  /**
   * Method executed before each test.
   */
  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());
    companyUCC = locator.getService(CompanyUCC.class);
    companyDAO = locator.getService(CompanyDAO.class);
    domainFactory = locator.getService(DomainFactory.class);

    companyDTO = domainFactory.getCompany();
  }


  /**
   * Test for successfully adding a contact.
   */
  @Test
  public void addContactTest_Success() {
    CompanyDTO companyDTO = mock(CompanyDTO.class);
    when(companyDAO.insert(companyDTO)).thenReturn(companyDTO);

    CompanyDTO result = companyUCC.addCompany(companyDTO);

    assertEquals(companyDTO, result);
  }


  /**
   * Test for adding a contact. Failure expected.
   */
  @Test
  public void addContactTest_Failure() {
    CompanyDTO companyDTO = mock(CompanyDTO.class);
    when(companyDAO.insert(companyDTO)).thenThrow(new RuntimeException());

    Exception exception = assertThrows(RuntimeException.class, () ->
        companyUCC.addCompany(companyDTO));

    assertNotNull(exception);
  }

  /**
   * Test for blacklist a company.
   */
  @Test
  public void companyBlackListTest_Success() {
    CompanyDTO realCompany = domainFactory.getCompany();
    CompanyDTO companyDTO = spy(realCompany);

    when(companyDTO.getIsBlackListed()).thenReturn(false);
    String reason = "raison de ouf";

    CompanyDTO result = companyUCC.blackList(companyDTO, reason);

    // Assert
    assertAll(
        () -> verify(companyDTO).setIsBlackListed(true),
        () -> verify(companyDTO).setMotivationBlackList(reason),
        () -> verify(companyDAO).update(companyDTO),
        () -> assertEquals(companyDTO, result)
    );
  }

  /**
   * Test for refusing a contact. Failure expected because the contact is null.
   */
  @Test
  public void companyBlackListTest_Null() {
    // Act and Assert
    assertThrows(NotFoundException.class, () -> {
      companyUCC.blackList(null, "hello");
    });
  }

  /**
   * Test for refusing a contact. Failure expected because the reason for refusal is null.
   */
  @Test
  public void companyBlackListTest_Reason_Null() {
    // arrange
    CompanyDTO realCompany = domainFactory.getCompany();
    CompanyDTO companyDTO = spy(realCompany);
    when(companyDTO.getIsBlackListed()).thenReturn(false);

    assertThrows(BusinessException.class, () ->
        companyUCC.blackList(companyDTO, null));
  }


  /**
   * Test for refusing a contact. Failure expected because the reason for refusal is null.
   */
  @Test
  public void companyBlackListTest_isblacklisted_true() {
    // arrange
    CompanyDTO realCompany = domainFactory.getCompany();
    CompanyDTO companyDTO = spy(realCompany);
    when(companyDTO.getIsBlackListed()).thenReturn(true);
    String reason = "raison de ouf";

    assertThrows(BusinessException.class, () ->
        companyUCC.blackList(companyDTO, reason));
  }

  /**
   * Test for refusing a contact. Failure expected because the reason for refusal is null.
   */
  @Test
  public void getAllEnterprisesTest_Success() {
    // Arrange
    List<CompanyDTO> expectedCompanies = companyDAO.getAllEnterprises();
    when(companyDAO.getAllEnterprises()).thenReturn(expectedCompanies);

    // Act
    List<CompanyDTO> result = companyUCC.getAllEnterprises();

    // Assert
    assertEquals(expectedCompanies, result);
  }

  /**
   * Test for refusing a contact. Failure expected because the reason for refusal is null.
   */
  @Test
  public void getAllEnterprisesTest_Failure() {
    // Arrange
    when(companyDAO.getAllEnterprises()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> {
      companyUCC.getAllEnterprises();
    });
  }

  /**
   * Test for refusing a contact. Failure expected because the reason for refusal is null.
   */
  @Test
  public void getCompanyByIdTest_Success() throws SQLException {
    // Arrange
    int id = 1;
    CompanyDTO expectedCompany = companyDAO.getCompanyById(id);
    when(companyDAO.getCompanyById(id)).thenReturn(expectedCompany);

    // Act
    CompanyDTO result = companyUCC.getCompanyById(id);

    // Assert
    assertEquals(expectedCompany, result);
  }


  /**
   * Test for refusing a contact. Failure expected because the reason for refusal is null.
   */
  @Test
  public void numberStudentsTakenByCompanyTest_Success() {
    // Arrange
    int id = 1;
    int expectedNumber = 5;
    when(companyDAO.numberOfStudentsTaken(id)).thenReturn(expectedNumber);

    // Act
    int result = companyUCC.numberOfStudentsTaken(id);

    // Assert
    assertEquals(expectedNumber, result);
  }

  /**
   * Test for refusing a contact. Failure expected because the reason for refusal is null.
   */
  @Test
  public void numberStudentsTakenByCompanyTest_Failure() {
    // Arrange
    int id = 1;
    when(companyDAO.numberOfStudentsTaken(id)).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> {
      companyUCC.numberOfStudentsTaken(id);
    });
  }

}