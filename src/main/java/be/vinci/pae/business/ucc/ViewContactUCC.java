package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ViewContactDTO;
import java.util.ArrayList;

/**
 * Represents a ViewContact Use Case Controller (UCC) with methods related to contact operations.
 */
public interface ViewContactUCC {

  /**
   * Returns the taken contacts of the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the taken contacts corresponding to the user
   */
  ArrayList<ViewContactDTO> getTakenContactsByUserId(int id);

  /**
   * Returns all the contacts of the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return all the contacts corresponding to the user
   */
  ArrayList<ViewContactDTO> getContactsByUserId(int id);
}
