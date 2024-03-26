package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ContactDTO;

/**
 * This interface defines methods for accessing and manipulating contact data in the database.
 */
public interface ContactDAO {

  /**
   * Inserts a new contact into the database.
   *
   * @param contactDTOToInsert The contact data to be inserted.
   * @return The contact data after insertion.
   */
  ContactDTO insert(ContactDTO contactDTOToInsert);
}
