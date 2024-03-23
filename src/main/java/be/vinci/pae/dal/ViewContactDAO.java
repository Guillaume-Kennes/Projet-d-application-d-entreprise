package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ViewContactDTO;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Represents a Data Access Object (DAO) for managing contact-related data.
 * Provides methods to retrieve contact information based on ID.
 */
public interface ViewContactDAO {

  /**
   * Returns the information of a contact.
   *
   * @param resultSet the given resultSet
   *
   * @return the contact corresponding to that result set
   */
  ViewContactDTO contactInfos(ResultSet resultSet);

  /**
   * Returns the contact corresponding to the id.
   *
   * @param id the contact's id
   *
   * @return the contact corresponding to the id
   */
  ViewContactDTO getContactById(int id);

  /**
   * Returns the contacts corresponding to the given user.
   *
   * @param id the user's id
   *
   * @return a list of the contacts corresponding to the given user
   */
  ArrayList<ViewContactDTO> getContactsByUserId(int id);

  /**
   * Returns the contacts in the state "pris" corresponding to the given user.
   *
   * @param id the user's id
   *
   * @return a list of the "pris" contacts corresponding to the given user
   */
  ArrayList<ViewContactDTO> getTakenContactsByUserId(int id);
}
