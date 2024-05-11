package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.utils.AppLogger;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the InternshipDAO interface. Provides methods for retrieving internship-related
 * data from the database.
 */
public class InternshipDAOImpl implements InternshipDAO {

  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private DALBackServices dalServices;
  private Logger log;

  /**
   * Retrieves an internship by their user id from the database.
   *
   * @param id The id of the user whose internship to retrieve.
   * @return an internship corresponding to the specified user, or null if not found.
   * @throws FatalException if an SQL exception occurs while accessing the database.
   */
  public InternshipDTO getInternshipByUserId(int id) throws SQLException {

    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.internships i, pae.contacts c, pae.users u, "
            + "pae.inscriptions_ue iu, pae.internship_supervisors s, pae.enterprises e "
            + "WHERE i.contact = c.id_contact AND c.inscription_ue = iu.id_inscription_ue "
            + "AND iu.student = u.id_user AND c.enterprise = e.id_enterprise AND "
            + "i.internship_supervisor = s.id_supervisor AND u.id_user = ?");

    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }

    InternshipDTO internship = myDomainFactory.getInternship();

    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        internship = internshipInfos(resultSet);
      } else {
        internship = null;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      preparedStatement.close();
    }
    return (InternshipDTO) internship;
  }

  /**
   * Method to retrieve internship information and map it to an InternshipDTO object.
   *
   * @param resultSet The ResultSet containing internship information.
   * @return An InternshipDTO object populated with internship information.
   * @throws FatalException if an SQL exception occurs while accessing the database.
   */
  public InternshipDTO internshipInfos(ResultSet resultSet) {
    InternshipDTO internshipDTO = myDomainFactory.getInternship();

    try {
      internshipDTO.setId(resultSet.getInt("id_internship"));
      internshipDTO.setProject(resultSet.getString("internship_project"));
      internshipDTO.setSignatureDate(resultSet.getDate("signature_date"));
      internshipDTO.setContact(resultSet.getInt("contact"));
      internshipDTO.setSupervisor(resultSet.getInt("internship_supervisor"));
      internshipDTO.setVersionNumber(resultSet.getInt("version_internships"));
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return internshipDTO;
  }

  /**
   * Inserts a new internship in the database.
   *
   * @param contact       The contact of the internship.
   * @param supervisor    The supervisor of the internship.
   * @param projet        The project of the internship.
   * @param signatureDate The signature date of the internship.
   * @return The inserted internship.
   * @throws FatalException if an SQL exception occurs while accessing the database.
   */
  public InternshipDTO createAnInternship(int contact,
      int supervisor, String projet, Date signatureDate) {
    try {
      String query = """
            INSERT INTO pae.internships (
            contact,
            internship_supervisor,
            internship_project,
            signature_date,
            version_internships)
            VALUES (?, ?, ?, ?, ?)
          """;

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {
        ps.setInt(1, contact);
        ps.setInt(2, supervisor);
        ps.setString(3, projet);
        ps.setDate(4, signatureDate);
        ps.setInt(5, 1);

        ps.executeUpdate();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    log = AppLogger.getLogger("Création d'un stage");
    log.log(Level.FINE, "Cration d'un stage :"
      + contact + " " + supervisor + " " + projet
        + " " + signatureDate);

    return myDomainFactory.getInternship();
  }

  /**
   * Updates an existing internship in the database.
   *
   * @param internshipDTO The internship to update.
   * @throws FatalException if an SQL exception occurs while accessing the database.
   */
  public void update(InternshipDTO internshipDTO) {
    try {
      String query = """
          UPDATE pae.internships
          SET contact = ?,
          internship_supervisor = ?,
          internship_project = ?,
          signature_date = ?,
          version_internships = version_internships + 1
          WHERE id_internship = ? AND version_internships = ?
          """;

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {
        ps.setInt(1, internshipDTO.getContact());
        ps.setInt(2, internshipDTO.getSupervisor());
        ps.setString(3, internshipDTO.getProject());
        ps.setDate(4, internshipDTO.getSignatureDate());
        ps.setInt(5, internshipDTO.getId());
        ps.setInt(6, internshipDTO.getVersionNumber());

        int correctVersion = ps.executeUpdate();
        if (correctVersion == 0) {
          if (getInternshipByUserId(internshipDTO.getId()) == null) {
            throw new FatalException("Contact not found");
          } else {
            throw new IllegalArgumentException("Error not the same version");
          }
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  /**
   * Retrieves an internship by their id from the database.
   *
   * @param internshipId The id of the internship to retrieve.
   * @return an internship corresponding to the specified id, or null if not found.
   * @throws FatalException if an SQL exception occurs while accessing the database.
   */
  public InternshipDTO getInternshipById(int internshipId) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.internships i WHERE i.id_internship = ?"
    );

    try {
      preparedStatement.setInt(1, internshipId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return internshipInfos(resultSet);
        }
      }
    } catch (SQLException e) {
      throw new FatalException("Contact not found");
    }
    return null;
  }

  /**
   * Retrieves all internships from the database.
   *
   * @return a list of all internships.
   * @throws FatalException if an SQL exception occurs while accessing the database.
   */
  public List<InternshipDTO> getAllInternships() {
    List<InternshipDTO> internshipsList = new ArrayList<>();
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.internships");
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        InternshipDTO internship = myDomainFactory.getInternship();
        internship.setId(resultSet.getInt("id_internship"));
        internship.setProject(resultSet.getString("internship_project"));
        internship.setSignatureDate(resultSet.getDate("signature_date"));
        internship.setContact(resultSet.getInt("contact"));
        internship.setSupervisor(resultSet.getInt("internship_supervisor"));
        internship.setVersionNumber(resultSet.getInt("version_internships"));
        internshipsList.add(internship);
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return internshipsList;
  }

  /**
   * Retrieves a list of the different school years.
   *
   * @return A list of the school years.
   */
  public List<String> getSchoolYears() {
    List<String> schoolYears = new ArrayList<>();

    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT DISTINCT iu.school_year FROM pae.inscriptions_ue iu")) {

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          schoolYears.add(resultSet.getString("school_year"));
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    return schoolYears;
  }
}