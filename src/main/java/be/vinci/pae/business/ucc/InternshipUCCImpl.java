package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.dal.InternshipDAO;
import jakarta.inject.Inject;

/**
 * Implementation of the InternshipUCC interface.
 * Provides methods related to internship operations.
 */
public class InternshipUCCImpl implements InternshipUCC{

  @Inject
  private InternshipDAO internshipDAO;

  /**
   * Returns the internship corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the internship corresponding to the user
   */
  public InternshipDTO getInternshipByUserId(int id) {
    return internshipDAO.getInternshipByUserId(id);
  }
}
