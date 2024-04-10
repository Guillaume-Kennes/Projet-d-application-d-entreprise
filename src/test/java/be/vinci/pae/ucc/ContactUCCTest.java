package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.utils.AppBinderTest;
import be.vinci.pae.utils.exception.BusinessException;
import be.vinci.pae.utils.exception.NotFoundException;
import java.sql.SQLException;
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
  // private ContactDTO contactDTO;
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
    // contactDTO = domainFactory.getContact();
    expectedContacts = new ArrayList<>();
  }

  /**
   * Test for successful retrieving of the user's contacts.
   */
  @Test
  public void testGetContactsByUserId() throws SQLException {

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

    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(expectedContact1, result.get(0)),
        () -> assertEquals(expectedContact2, result.get(1)),
        () -> assertEquals(expectedContact3, result.get(2)),
        () -> assertEquals(expectedContact4, result.get(3))
    );

  }

  /**
   * Test for successful retrieving of the user's taken contacts.
   */
  @Test
  public void testGetTakenContactsByUserId() throws SQLException {

    ContactDTO expectedContact = domainFactory.getContact();
    expectedContact.setId(8);
    expectedContacts.add(expectedContact);

    int userId = 5;
    when(contactDAO.getContactsByUserId(userId)).thenReturn(expectedContacts);

    ArrayList<ContactDTO> result = contactUCC.getContactsByUserId(userId);

    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(expectedContact, result.get(0))
    );
  }

  /**
   * Test for meeting a company when the contact is null. Failure expected.
   */
  @Test
  public void meetCompanyTest_nullContact_fail() {
    assertThrows(BusinessException.class, () -> {

      contactUCC.meetCompany(null, "enterprise");
    });
  }

  /**
   * Test for meeting a company when the place is null. Failure expected.
   */
  @Test
  public void meetCompanyTest_nullPlace_fail() {
    // arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("initié");

    assertThrows(BusinessException.class, () ->
        contactUCC.meetCompany(contact, null));
  }

  /**
   * Test for meeting a company when the state is initiated. Success expected.
   */
  @Test
  public void meetCompanyTest_StateInitiated_success() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("initié");
    String place = "enterprise";

    ContactDTO result = contactUCC.meetCompany(contact, place);

    // Assert
    assertAll(
        () -> verify(contact).setState("pris"),
        () -> verify(contact).setMeetingPlace(place),
        () -> verify(contactDAO).update(contact),
        () -> assertEquals(contact, result)
    );
  }

  /**
   * Test for meeting a company when the state is taken. Failure expected.
   */
  @Test
  public void meetCompanyTest_StateTaken_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("pris");

    assertThrows(BusinessException.class, () ->
        contactUCC.meetCompany(contact, "enterprise"));
  }

  /**
   * Test for meeting a company when the state is refused. Failure expected.
   */
  @Test
  public void meetCompanyTest_StateRefused_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("refusé");

    assertThrows(BusinessException.class, () ->
        contactUCC.meetCompany(contact, "distance"));
  }

  /**
   * Test for meeting a company when the state is accepted. Failure expected.
   */
  @Test
  public void meetCompanyTest_StateAccepted_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("accepté");

    assertThrows(BusinessException.class, () ->
        contactUCC.meetCompany(contact, "distance"));
  }

  /**
   * Test for meeting a company when the state is suspended. Failure expected.
   */
  @Test
  public void meetCompanyTest_StateSuspended_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("suspendu");

    assertThrows(BusinessException.class, () ->
        contactUCC.meetCompany(contact, "distance"));
  }

  /**
   * Test for meeting a company when the state is abandoned. Failure expected.
   */
  @Test
  public void meetCompanyTest_StateAbandoned_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("abandonné");

    assertThrows(BusinessException.class, () ->
        contactUCC.meetCompany(contact, "enterprise"));
  }

  /**
   * Test for successfully retrieving a contact by its id.
   */
  @Test
  public void getContactByIdTest_Success() {
    int idContact = 1;
    ContactDTO expectedContact = mock(ContactDTO.class);
    when(contactDAO.getContactById(idContact)).thenReturn(expectedContact);

    ContactDTO result = contactUCC.getContactById(idContact);

    // Assert
    assertAll(
        () -> verify(contactDAO).getContactById(idContact),
        () -> assertEquals(expectedContact, result)
    );
  }

  /**
   * Test for retrieving a contact by its id. Failure expected.
   */
  @Test
  public void getContactByIdTest_Failure() {
    int idContact = 1;
    when(contactDAO.getContactById(idContact)).thenThrow(new RuntimeException());

    Exception exception = assertThrows(RuntimeException.class, () ->
        contactUCC.getContactById(idContact));

    assertNotNull(exception);
  }


  /**
   * Test for stopping following a contact. Failure expected because the contact is null.
   */
  @Test
  public void stopFollowingTest_nullContact_fail() {
    // Act and Assert
    assertThrows(BusinessException.class, () -> {
      contactUCC.stopFollowing(null);
    });
  }

  /**
   * Test for stopping following a contact. Failure expected.
   */
  @Test

  public void stopFollowingErrorTest_fail() {
    // arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.isFollowed()).thenReturn(false);

    assertThrows(BusinessException.class, () ->
        contactUCC.stopFollowing(contact));
  }

  /**
   * Test for stopping following a contact. Success expected.
   */
  @Test
  public void stopFollowingCorrectTest_success() {
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.isFollowed()).thenReturn(true);

    ContactDTO result = contactUCC.stopFollowing(contact);

    // assert
    assertAll(
        () -> verify(contact).setFollowed(false),
        () -> verify(contact).setState("abandonné"),
        () -> verify(contactDAO).update(contact),
        () -> assertEquals(contact, result)
    );
  }

  /**
   * Test for refusing a contact. Failure expected because the contact is null.
   */
  @Test
  public void companyRefusedInternshipTest_nullContact_fail() {
    // Act and Assert
    assertThrows(BusinessException.class, () -> {
      contactUCC.companyRefusedInternship(null, "hello");
    });
  }

  /**
   * Test for refusing a contact. Failure expected because the reason for refusal is null.
   */
  @Test
  public void companyRefusedInternshipTest_nullReason_fail() {
    // arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("pris");

    assertThrows(BusinessException.class, () ->
        contactUCC.companyRefusedInternship(contact, null));
  }

  /**
   * Test for refusing a contact with taken state. Success expected.
   */
  @Test
  public void companyRefusedInternshipTest_stateTaken_success() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("pris");
    String reason = "hello";

    ContactDTO result = contactUCC.companyRefusedInternship(contact, reason);

    // Assert
    assertAll(
        () -> verify(contact).setState("refusé"),
        () -> verify(contact).setReasonForRefusal(reason),
        () -> verify(contactDAO).update(contact),
        () -> assertEquals(contact, result)
    );
  }

  /**
   * Test for refusing a contact with initiated state. Failure expected.
   */
  @Test
  public void companyRefusedInternshipTest_stateInitiated_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("initié");

    assertThrows(BusinessException.class, () ->
        contactUCC.companyRefusedInternship(contact, "hello"));
  }

  /**
   * Test for refusing a contact with refused state. Failure expected.
   */
  @Test
  public void companyRefusedInternshipTest_stateRefused_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("refusé");

    assertThrows(BusinessException.class, () ->
        contactUCC.companyRefusedInternship(contact, "hello"));
  }

  /**
   * Test for refusing a contact with accepted state. Failure expected.
   */
  @Test
  public void companyRefusedInternshipTest_stateAccepted_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("accepté");

    assertThrows(BusinessException.class, () ->
        contactUCC.companyRefusedInternship(contact, "hello"));
  }

  /**
   * Test for refusing a contact with suspended state. Failure expected.
   */
  @Test
  public void companyRefusedInternshipTest_stateSuspended_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("suspendu");

    assertThrows(BusinessException.class, () ->
        contactUCC.companyRefusedInternship(contact, "hello"));
  }

  /**
   * Test for refusing a contact with abandoned state. Failure expected.
   */
  @Test
  public void companyRefusedInternshipTest_stateAbandoned_fail() {
    // Arrange
    ContactDTO realContact = domainFactory.getContact();
    ContactDTO contact = spy(realContact);
    when(contact.getState()).thenReturn("abandonné");

    assertThrows(BusinessException.class, () ->
        contactUCC.companyRefusedInternship(contact, "hello"));
  }

  /**
   * Test for successfully adding a contact.
   */
  @Test
  public void addContactTest_Success() {
    ContactDTO contactDTO = mock(ContactDTO.class);
    when(contactDAO.insert(contactDTO)).thenReturn(contactDTO);

    ContactDTO result = contactUCC.addContact(contactDTO);

    assertEquals(contactDTO, result);
  }

  /**
   * Test for adding a contact. Failure expected.
   */
  @Test
  public void addContactTest_Failure() {
    ContactDTO contactDTO = mock(ContactDTO.class);
    when(contactDAO.insert(contactDTO)).thenThrow(new RuntimeException());

    Exception exception = assertThrows(RuntimeException.class, () ->
        contactUCC.addContact(contactDTO));

    assertNotNull(exception);
  }

  /**
   * Test for successfully getting a contact from the id of its user.
   */
  @Test
  public void getTakenContactsByUserIdTest_Success() throws SQLException {
    int userId = 1;
    ArrayList<ContactDTO> expectedContacts = new ArrayList<>();
    when(contactDAO.getTakenContactsByUserId(userId)).thenReturn(expectedContacts);

    ArrayList<ContactDTO> result = contactUCC.getTakenContactsByUserId(userId);

    assertEquals(expectedContacts, result);
  }

  /**
   * Test for getting a contact from the id of its user. Failure expected.
   */
  @Test
  public void getTakenContactsByUserIdTest_Failure() throws SQLException {
    int userId = 1;
    when(contactDAO.getTakenContactsByUserId(userId)).thenThrow(new RuntimeException());

    Exception exception = assertThrows(RuntimeException.class, () ->
        contactUCC.getTakenContactsByUserId(userId));

    assertNotNull(exception);
  }

  /*
   * @Test
  public void getContactsByUserIdTest_Success() {
    // Arrange
    int userId = 1;
    ArrayList<ContactDTO> expectedContacts = new ArrayList<>();
    when(contactDAO.getContactsByUserId(userId)).thenReturn(expectedContacts);

    // Act
    ArrayList<ContactDTO> result = contactUCC.getContactsByUserId(userId);

    // Assert
    assertEquals(expectedContacts, result);
  }
   */

  /**
   * Test for getting a contact from the id of its user. Failure expected.
   */
  @Test
  public void getContactsByUserIdTest_Failure() throws SQLException {
    int userId = 1;
    when(contactDAO.getContactsByUserId(userId)).thenThrow(new RuntimeException());

    Exception exception = assertThrows(RuntimeException.class, () ->
        contactUCC.getContactsByUserId(userId));

    assertNotNull(exception);
  }
}
