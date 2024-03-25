package be.vinci.pae.ucc;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.utils.AppBinderTest;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;

public class ContactUCCTest {
  private ContactUCC contactUCC;
  private DomainFactory myDomainFactory;
  private ContactDAO contactDAO;
  private ContactDTO contactDTO;

  /**
   * Method executed before each test.
   */
  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());

    contactUCC = locator.getService(ContactUCC.class);
    myDomainFactory = locator.getService(DomainFactory.class);
    contactDAO = locator.getService(ContactDAO.class);
    contactDTO = myDomainFactory.getContact();
  }



}
