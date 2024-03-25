package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.ViewContactDTO;
import be.vinci.pae.business.ucc.ViewContactUCC;
import be.vinci.pae.dal.ViewContactDAO;
import be.vinci.pae.utils.AppBinderTest;
import java.util.ArrayList;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * Test class for ViewContactUCC.
 */
public class ViewContactUCCTest {
  private ViewContactUCC contactUCC;
  private DomainFactory domainFactory;
  @Mock
  private ViewContactDAO contactDAO;
  private ArrayList<ViewContactDTO> expectedContacts;

  /**
   * Method executed before each test.
   */
  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());

    contactUCC = locator.getService(ViewContactUCC.class);
    contactDAO = locator.getService(ViewContactDAO.class);
    domainFactory = locator.getService(DomainFactory.class);
    expectedContacts = new ArrayList<>();
  }

  /**
   * Test for successful retrieving of the user's contacts.
   */
  @Test
  public void testGetContactsByUserId() {

    ViewContactDTO expectedContact1 = domainFactory.getContact();
    expectedContact1.setId(4);
    expectedContacts.add(expectedContact1);
    ViewContactDTO expectedContact2 = domainFactory.getContact();
    expectedContact2.setId(5);
    expectedContacts.add(expectedContact2);
    ViewContactDTO expectedContact3 = domainFactory.getContact();
    expectedContact3.setId(6);
    expectedContacts.add(expectedContact3);
    ViewContactDTO expectedContact4 = domainFactory.getContact();
    expectedContact4.setId(7);
    expectedContacts.add(expectedContact4);

    int userId = 7;
    when(contactDAO.getContactsByUserId(userId)).thenReturn(expectedContacts);

    ArrayList<ViewContactDTO> result = contactUCC.getContactsByUserId(userId);

    assertNotNull(result);
    assertEquals(expectedContact1, result.get(0));
    assertEquals(expectedContact2, result.get(1));
    assertEquals(expectedContact3, result.get(2));
    assertEquals(expectedContact4, result.get(3));
  }

  /**
   * Test for successful retrieving of the user's taken contacts.
   */
  @Test
  public void testGetTakenContactsByUserId() {

    ViewContactDTO expectedContact = domainFactory.getContact();
    expectedContact.setId(8);

    int userId = 5;
    when(contactDAO.getContactsByUserId(userId)).thenReturn(expectedContacts);

    ArrayList<ViewContactDTO> result = contactUCC.getContactsByUserId(userId);

    assertNotNull(result);
    assertEquals(expectedContact, result.get(0));
  }
}
