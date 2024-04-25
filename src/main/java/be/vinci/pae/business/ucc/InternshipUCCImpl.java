package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.InternshipDAO;
import be.vinci.pae.utils.exception.NotFoundException;
import jakarta.inject.Inject;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of the InternshipUCC interface. Provides methods related to internship
 * operations.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private InternshipDAO internshipDAO;
  @Inject
  private DALServices dalServices;

  /**
   * Returns the internship corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   * @return the internship corresponding to the user
   */
  public InternshipDTO getInternshipByUserId(int id) throws SQLException {
    dalServices.start();
    try {
      InternshipDTO internshipDTO = internshipDAO.getInternshipByUserId(id);
      System.out.println("Ucc Internship : " + internshipDTO);
      dalServices.commit();
      return internshipDTO;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Create an internship.
   *
   * @param contact the contact of the internship
   * @param supervisor the supervisor of the internship
   * @param projet the project of the internship
   * @param signatureDate the signature date of the internship
   * @return the created internship
   */
  public InternshipDTO createAnInternship(int contact, int supervisor, String projet,
      Date signatureDate) {
    dalServices.start();
    try {

      if (contact == 0) {
        dalServices.rollBack();
        throw new NotFoundException("Contact is null");
      }

      if (supervisor == 0) {
        dalServices.rollBack();
        throw new NotFoundException("Supervisor is null");
      }

      if (signatureDate == null) {
        dalServices.rollBack();
        throw new NotFoundException("Signature date is null");
      }

      InternshipDTO internshipDTO = internshipDAO.createAnInternship(contact, supervisor, projet,
          signatureDate);
      dalServices.commit();
      return internshipDTO;
    } catch (Exception e) {
      dalServices.rollBack();
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Create or modify an internship.
   *
   * @param internship the internship to create or modify
   * @param subject the subject of the internship
   * @return the created or modified internship
   */
  public InternshipDTO createOrModifyAnInternship(InternshipDTO internship, String subject) {
    dalServices.start();
    try {
      if (internship == null) {
        dalServices.rollBack();
        throw new NotFoundException("Internship is null");
      }

      if (subject == null) {
        dalServices.rollBack();
        throw new NotFoundException("Subject is null");
      }

      internship.setProject(subject);

      internshipDAO.update(internship);
      dalServices.commit();
      return internship;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Retrieves the internship associated with the specified ID.
   *
   * @param internshipId The ID of the internship to retrieve information for.
   * @return An InternshipDTO object representing the internship information.
   */
  public InternshipDTO getInternshipById(int internshipId) {
    dalServices.start();
    try {
      InternshipDTO internship = internshipDAO.getInternshipById(internshipId);
      dalServices.commit();
      return internship;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Retrieves all internships.
   *
   * @return A list of all internships.
   */
  public List<InternshipDTO> getAllInternships() {
    dalServices.start();
    try {
      List<InternshipDTO> internships = internshipDAO.getAllInternships();
      dalServices.commit();
      return internships;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Retrieves the list of school years.
   *
   * @return A list of the school years.
   */
  public List<String> getSchoolYears() {
    dalServices.start();
    try {
      List<String> schoolYears = internshipDAO.getSchoolYears();
      dalServices.commit();
      return schoolYears;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }
}
