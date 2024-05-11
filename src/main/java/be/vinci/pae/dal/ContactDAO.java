package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.utils.exception.FatalException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
   * Retrieves the list of contacts associated with a user identified by their ID.
   *
   * @param id The ID of the user for whom to retrieve the contacts.
   * @return An ArrayList of ContactDTO objects representing the contacts associated with the user.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  ArrayList<ContactDTO> getContactsByUserId(int id) throws SQLException;

  /**
   * Retrieves the list of contacts taken by a user identified by their ID.
   *
   * @param id The ID of the user for whom to retrieve the taken contacts.
   * @return An ArrayList of ContactDTO objects representing the contacts taken by the user.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  ArrayList<ContactDTO> getTakenContactsByUserId(int id) throws SQLException;

  /**
   * Inserts a new contact into the database.
   *
   * @param contactDTOToInsert The contact data to be inserted.
   * @return The contact data after insertion.
   */
  ContactDTO insert(ContactDTO contactDTOToInsert);

  /**
   * Retrieves all contacts associated with the specified company ID.
   *
   * @param idCompany The ID of the company.
   * @return An ArrayList of ContactDTO objects representing all contacts associated with the
   *     company.
   * @throws SQLException if an SQL exception occurs while retrieving the contacts.
   */
  ArrayList<ContactDTO> getCompanyContacts(int idCompany) throws SQLException;

  /**
   * Suspend the other contacts of a user once they got an internship.
   *
   * @param contactDTO The contact DTO to update.
   * @throws FatalException if an SQL error occurs.
   */
  void suspendOthers(ContactDTO contactDTO);
}
