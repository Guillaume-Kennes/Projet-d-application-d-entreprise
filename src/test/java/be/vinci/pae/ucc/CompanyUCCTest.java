package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.dal.CompanyDAO;
import be.vinci.pae.utils.AppBinderTest;
import be.vinci.pae.utils.exception.BusinessException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class CompanyUCCTest.
 */
public class CompanyUCCTest {
  private CompanyUCC companyUCC;
  private CompanyDAO companyDAO;

  /**
   * Method executed before each test.
   */
  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());
    companyUCC = locator.getService(CompanyUCC.class);
    companyDAO = locator.getService(CompanyDAO.class);
  }


  /**
   * Test method for retrieving all enterprises when the operation fails.
   */
  @Test
  void getALlEnterprisesTest_Failure() {
    when(companyDAO.getAllEnterprises()).thenThrow(new BusinessException());
    Exception exception = assertThrows(BusinessException.class, () ->
        companyUCC.getAllEnterprises());

    assertNotNull(exception);
  }

}
