package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ContactDTO;

/**
 * Interface for Use Case Controller (UCC) for Contact.
 */
public interface ContactUCC {

  /**
   * Arranges a meeting with a company.
   *
   * @param contact The contact for the meeting.
   *
   * @param place The place of the meeting.
   *
   * @return The updated contact after the meeting.
   */
  ContactDTO meetCompany(ContactDTO contact, String place);

  /**
   * Retrieves a contact by its ID.
   *
   * @param idContact The ID of the contact.
   *
   * @return The contact with the given ID.
   */
  ContactDTO getContactById(int idContact);

  /**
   * Stops following a contact.
   *
   * @param contact The contact to stop following.
   *
   * @return The updated contact after stopping the follow.
   */
  ContactDTO stopFollowing(ContactDTO contact);

  /**
   * Handles the case when a company refuses an internship.
   *
   * @param contact The contact for the company.
   *
   * @param reason The reason for refusal.
   *
   * @return The updated contact after the refusal.
   */
  ContactDTO companyRefusedInternship(ContactDTO contact, String reason);

}
