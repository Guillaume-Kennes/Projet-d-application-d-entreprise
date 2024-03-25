package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.utils.AppBinderTest;
import java.util.ArrayList;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactUCCTest {
  private ContactUCC contactUCC;
  private DomainFactory domainFactory;
  private ContactDAO contactDAO;
  private ContactDTO contactDTO;
  private ArrayList<ContactDTO> expectedContacts;

  /**
   * Method executed before each test.
   */
  @BeforeEach
  public void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new AppBinderTest());

    contactUCC = locator.getService(ContactUCC.class);
    domainFactory = locator.getService(DomainFactory.class);
    contactDAO = locator.getService(ContactDAO.class);
    contactDTO = domainFactory.getContact();
    expectedContacts = new ArrayList<>();
  }

  /**
   * Test for successful retrieving of the user's contacts.
   */
  @Test
  public void testGetContactsByUserId() {

    ContactDTO expectedContact1 = domainFactory.getContact();
    expectedContact1.setId(4);
    expectedContacts.add(expectedContact1);
    ContactDTO expectedContact2 = domainFactory.getContact();
    expectedContact2.setId(5);
    expectedContacts.add(expectedContact2);
    ContactDTO expectedContact3 = domainFactory.getContact();
    expectedContact3.setId(6);
    expectedContacts.add(expectedContact3);
    ContactDTO expectedContact4 = domainFactory.getContact();
    expectedContact4.setId(7);
    expectedContacts.add(expectedContact4);

    int userId = 7;
    when(contactDAO.getContactsByUserId(userId)).thenReturn(expectedContacts);

    ArrayList<ContactDTO> result = contactUCC.getContactsByUserId(userId);

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

    ContactDTO expectedContact = domainFactory.getContact();
    expectedContact.setId(8);
    expectedContacts.add(expectedContact);

    int userId = 5;
    when(contactDAO.getContactsByUserId(userId)).thenReturn(expectedContacts);

    ArrayList<ContactDTO> result = contactUCC.getContactsByUserId(userId);

    assertNotNull(result);
    assertEquals(expectedContact, result.get(0));
  }
}
