package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.dal.CompanyDAO;
import be.vinci.pae.utils.AppBinderTest;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompanyUCCTest {

  private CompanyUCC companyUCC;
  private CompanyDAO companyDAO;

  /**
   * Method executed before each test.
   */
  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());

    companyUCC = locator.getService(CompanyUCC.class);
    // DomainFactory domainFactory = locator.getService(DomainFactory.class);
    companyDAO = locator.getService(CompanyDAO.class);
    // ArrayList<CompanyDTO> expectedCompany = new ArrayList<>();
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
}
