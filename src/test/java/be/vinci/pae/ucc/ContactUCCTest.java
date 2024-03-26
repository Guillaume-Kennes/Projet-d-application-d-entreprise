package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

/**
 * Test class for ContactUCC.
 */
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

  /**
   * Test for meeting a company when the contact is null.
   */
  @Test
  public void meetCompanyTest_nullContact() {
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.meetCompany(null, "enterprise");
    });
  }

  /**
   * Test for meeting a company when the place is null.
   */
  @Test
  public void meetCompanyTest_nullPlace() {
    // arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("initié");

    // act and assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.meetCompany(contact, null);
    });
  }

  /**
   * Test for meeting a company when the state is initiated.
   */
  @Test
  public void meetCompanyTest_StateInitiated() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("initié");
    String place = "enterprise";

    // Act
    ContactDTO result = contactUCC.meetCompany(contact, place);

    // Assert
    verify(contact).setState("pris");
    verify(contact).setMeetingPlace(place);
    verify(contactDAO).update(contact);
    assertEquals(contact, result);
  }

  @Test
  public void meetCompanyTest_StateTaken() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("pris");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.meetCompany(contact, "enterprise");
    });
  }

  @Test
  public void meetCompanyTest_StateRefused() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("refusé");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.meetCompany(contact, "distance");
    });
  }

  @Test
  public void meetCompanyTest_StateAccepted() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("accepté");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.meetCompany(contact, "distance");
    });
  }

  @Test
  public void meetCompanyTest_StateSuspended() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("suspendu");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.meetCompany(contact, "distance");
    });
  }

  @Test
  public void meetCompanyTest_StateAbandoned() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("abandonné");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.meetCompany(contact, "enterprise");
    });
  }

  @Test
  public void getContactByIdTest() {
    // Arrange
    int idContact = 1;
    ContactDTO expectedContact = mock(ContactDTO.class);
    when(contactDAO.getContactById(idContact)).thenReturn(expectedContact);

    // Act
    ContactDTO result = contactUCC.getContactById(idContact);

    // Assert
    verify(contactDAO).getContactById(idContact);
    assertEquals(expectedContact, result);
  }

  @Test
  public void stopFollowingTest_nullContact() {
    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.stopFollowing(null);
    });
  }

  @Test
  public void stopFollowingErrorTest() {
    // arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.isFollowed()).thenReturn(false);

    // act and assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.stopFollowing(contact);
    });
  }

  @Test
  public void stopFollowingCorrectTest() {
    // arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.isFollowed()).thenReturn(true);

    // act
    ContactDTO result = contactUCC.stopFollowing(contact);

    // assert
    verify(contact).setFollowed(false);
    verify(contact).setState("abandonné");
    verify(contactDAO).update(contact);
    assertEquals(contact, result);
  }

  @Test
  public void companyRefusedInternshipTest_nullContact() {
    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.companyRefusedInternship(null, "hello");
    });
  }

  @Test
  public void companyRefusedInternshipTest_nullReason() {
    // arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("pris");

    // act and assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.companyRefusedInternship(contact, null);
    });
  }

  @Test
  public void companyRefusedInternshipTest_stateTaken() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("pris");
    String reason = "hello";

    // Act
    ContactDTO result = contactUCC.companyRefusedInternship(contact, reason);

    // Assert
    verify(contact).setState("refusé");
    verify(contact).setReasonForRefusal(reason);
    verify(contactDAO).update(contact);
    assertEquals(contact, result);
  }

  @Test
  public void companyRefusedInternshipTest_stateInitiated() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("initié");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.companyRefusedInternship(contact, "hello");
    });
  }

  @Test
  public void companyRefusedInternshipTest_stateRefused() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("refusé");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.companyRefusedInternship(contact, "hello");
    });
  }

  @Test
  public void companyRefusedInternshipTest_stateAccepted() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("accepté");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.companyRefusedInternship(contact, "hello");
    });
  }

  @Test
  public void companyRefusedInternshipTest_stateSuspended() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("suspendu");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.companyRefusedInternship(contact, "hello");
    });
  }

  @Test
  public void companyRefusedInternshipTest_stateAbandoned() {
    // Arrange
    ContactDTO contact = mock(ContactDTO.class);
    when(contact.getState()).thenReturn("abandonné");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> {
      contactUCC.companyRefusedInternship(contact, "hello");
    });
  }
}
