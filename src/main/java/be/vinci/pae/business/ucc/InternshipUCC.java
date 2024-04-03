package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.InternshipDTO;
import java.sql.SQLException;

/**
 * Represents an Internship Use Case Controller (UCC) with methods related to internship operations.
 */
public interface InternshipUCC {

  /**
   * Returns the internship of the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the internship corresponding to the user
   */
  InternshipDTO getInternshipByUserId(int id) throws SQLException;
}
