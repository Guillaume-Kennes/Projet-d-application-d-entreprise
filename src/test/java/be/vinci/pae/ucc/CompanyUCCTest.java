package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.dal.CompanyDAO;
import be.vinci.pae.utils.AppBinderTest;
import be.vinci.pae.utils.exception.BusinessException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompanyUCCTest {
  private CompanyUCC companyUCC;
  private CompanyDAO companyDAO;
  private CompanyDTO companyDTO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());
    companyUCC = locator.getService(CompanyUCC.class);
    companyDAO = locator.getService(CompanyDAO.class);
    DomainFactory myDomainFactory = locator.getService(DomainFactory.class);
    companyDTO = myDomainFactory.getCompany();
  }

  @Test
  void getALlEnterprisesTest_Failure() {
    when(companyDAO.getAllEnterprises()).thenThrow(new BusinessException("Error while retrieving enterprises"));
    Exception exception = assertThrows(BusinessException.class, () -> companyUCC.getAllEnterprises());

    assertNotNull(exception);
  }

}
