package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ContactDTO;

/**
 * This interface defines methods for managing contacts.
 */
public interface ContactUCC {

  /**
   * Adds a new contact.
   *
   * @param contactDTO The contact data to be added.
   * @return The added contact data.
   */
  ContactDTO addContact(ContactDTO contactDTO);
}
