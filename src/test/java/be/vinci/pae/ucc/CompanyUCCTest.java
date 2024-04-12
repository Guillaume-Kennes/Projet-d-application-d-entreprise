package be.vinci.pae.ucc;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.dal.CompanyDAO;
import be.vinci.pae.utils.AppBinderTest;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;

/**
 * The type Company ucc test.
 */
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




}