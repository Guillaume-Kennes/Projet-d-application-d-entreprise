package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ContactDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for Use Case Controller (UCC) for Contact.
 */
public interface ContactUCC {

  /**
   * Arranges a meeting with a company.
   *
   * @param contact The contact for the meeting.
   * @param place   The place of the meeting.
   * @return The updated contact after the meeting.
   */
  ContactDTO meetCompany(ContactDTO contact, String place);

  /**
   * Retrieves a contact by its ID.
   *
   * @param idContact The ID of the contact.
   * @return The contact with the given ID.
   */
  ContactDTO getContactById(int idContact);

  /**
   * Stops following a contact.
   *
   * @param contact The contact to stop following.
   * @return The updated contact after stopping the follow.
   */
  ContactDTO stopFollowing(ContactDTO contact);

  /**
   * Handles the case when a company refuses an internship.
   *
   * @param contact The contact for the company.
   * @param reason  The reason for refusal.
   * @return The updated contact after the refusal.
   */
  ContactDTO companyRefusedInternship(ContactDTO contact, String reason);

  /**
   * Retrieves the list of contacts taken by a user identified by their ID.
   *
   * @param id The ID of the user for whom to retrieve the taken contacts.
   * @return An ArrayList of ContactDTO objects representing the contacts taken by the user.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  ArrayList<ContactDTO> getTakenContactsByUserId(int id) throws SQLException;

  /**
   * Retrieves the list of contacts associated with a user identified by their ID.
   *
   * @param id The ID of the user for whom to retrieve the contacts.
   * @return An ArrayList of ContactDTO objects representing the contacts associated with the user.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  ArrayList<ContactDTO> getContactsByUserId(int id) throws SQLException;

  /**
   * Adds a new contact.
   *
   * @param contactDTO The contact data to be added.
   * @return The added contact data.
   */
  ContactDTO addContact(ContactDTO contactDTO);

  /**
   * Retrieves a list of all contacts made by a company.
   *
   * @param id The id of the company.
   * @return A list of ContactDTO objects.
   */
  ArrayList<ContactDTO> getAllContacts(int id) throws SQLException;

  /**
   * Accepts a contact as an internship.
   *
   * @param contact The contact for the company.
   * @return The updated contact after the refusal.
   */
  ContactDTO acceptInternship(ContactDTO contact);

}
