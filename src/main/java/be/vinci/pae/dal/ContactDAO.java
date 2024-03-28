package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ContactDTO;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Interface for Data Access Object (DAO) for Contact.
 */
public interface ContactDAO {

  /**
   * Retrieves a contact by its ID.
   *
   * @param idContact The ID of the contact.
   * @return The contact with the given ID.
   */
  ContactDTO getContactById(int idContact);

  /**
   * Retrieves contact information from a ResultSet.
   *
   * @param resultSet The ResultSet containing the contact information.
   * @return The contact information retrieved from the ResultSet.
   */
  ContactDTO contactInfos(ResultSet resultSet);

  /**
   * Updates a contact in the database.
   *
   * @param contactDTO The contact information to update.
   */
  void update(ContactDTO contactDTO);

  /**
   * Returns the contacts corresponding to the given user.
   *
   * @param id the user's id
   * @return a list of the contacts corresponding to the given user
   */
  ArrayList<ContactDTO> getContactsByUserId(int id);

  /**
   * Returns the contacts in the state "pris" corresponding to the given user.
   *
   * @param id the user's id
   * @return a list of the "pris" contacts corresponding to the given user
   */
  ArrayList<ContactDTO> getTakenContactsByUserId(int id);

  /**
   * Inserts a new contact into the database.
   *
   * @param contactDTOToInsert The contact data to be inserted.
   * @return The contact data after insertion.
   */
  ContactDTO insert(ContactDTO contactDTOToInsert);
}
