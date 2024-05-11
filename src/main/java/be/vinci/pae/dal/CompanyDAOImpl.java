package be.vinci.pae.dal;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.utils.AppLogger;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the ViewCompanyDAO interface. Provides methods for retrieving company-related
 * data from the database.
 */
public class CompanyDAOImpl implements CompanyDAO {

  @Inject
  private DALBackServices dalServices;
  @Inject
  private DomainFactory myDomainFactory;
  private Logger log;

  /**
   * Inserts a new item in the system.
   *
   * @param companyDTO ItemDTO object containing the information of the item to be inserted.
   * @return int of the object created
   */
  public CompanyDTO insert(CompanyDTO companyDTO) {
    try {
      String query = """
            INSERT INTO pae.enterprises (
              trade_name,
              designation,
              address,
              city,
              means_of_communication,
              is_black_listed,
              motivation_black_list,
              version_enterprises)
            VALUES (?, ?, ?, ?, ?, false, null, 1)
            RETURNING *;
          """;

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {

        ps.setString(1, companyDTO.getTradeName());
        if (companyDTO.getDesignation() != null && !companyDTO.getDesignation().isEmpty()) {
          ps.setString(2, companyDTO.getDesignation());
        } else {
          ps.setNull(2, java.sql.Types.VARCHAR);
        }
        ps.setString(3, companyDTO.getAddress());
        ps.setString(4, companyDTO.getCity()); // city
        ps.setString(5, companyDTO.getMeansOfCommunication());

        ps.executeQuery();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    log = AppLogger.getLogger("Insertion d'une entreprise");
    log.log(Level.FINE, "CompanyDAOImpl companyDTO : " + "\n"
        + "Trade name : " + companyDTO.getTradeName() + "\n"
        + "Designation : " + companyDTO.getDesignation() + "\n"
        + "Address : " + companyDTO.getAddress() + "\n"
        + "City : " + companyDTO.getCity() + "\n"
        + "MeansOfCommunication : " + companyDTO.getMeansOfCommunication() + "\n");

    return companyDTO;
  }

  /**
   * Method to retrieve company information from a ResultSet and map it to a ViewCompanyDTO object.
   *
   * @param resultSet The ResultSet containing company information.
   * @return A ViewCompanyDTO object populated with company information from the ResultSet.
   * @throws FatalException if the company info is not found in the database.
   */
  public CompanyDTO companyInfos(ResultSet resultSet) {
    CompanyDTO companyDTO = myDomainFactory.getCompany();

    try {
      companyDTO.setId(resultSet.getInt("id_enterprise"));
      companyDTO.setTradeName(resultSet.getString("trade_name"));
      companyDTO.setDesignation(resultSet.getString("designation"));
      companyDTO.setAddress(resultSet.getString("address"));
      companyDTO.setCity(resultSet.getString("city"));
      companyDTO.setMeansOfCommunication(resultSet.getString("means_of_communication"));
      companyDTO.setIsBlackListed(resultSet.getBoolean("is_black_listed"));
      companyDTO.setMotivationBlackList(resultSet.getString("motivation_black_list"));
      companyDTO.setVersionNumber(resultSet.getInt("version_enterprises"));
      // ici ca resout le probleme du version a 0
      companyDTO.setIsBlackListed(resultSet.getBoolean("is_black_listed"));
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return companyDTO;
  }

  /**
   * Method to retrieve a company by their ID.
   *
   * @param id The ID of the company to retrieve.
   * @return A ViewCompanyDTO object representing the company, or null if not found.
   * @throws FatalException if the company is not found in the database.
   */
  public CompanyDTO getCompanyById(int id) throws SQLException {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.enterprises e WHERE e.id_enterprise = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    CompanyDTO company = myDomainFactory.getCompany();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        company = companyInfos(resultSet);
      } else {
        company = null;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      preparedStatement.close();
    }
    return company;
  }

  /**
   * Retrieves a list of all enterprises from the database.
   *
   * @return A list of CompanyDTO objects representing all enterprises.
   * @throws FatalException If an error occurs during database access or processing.
   */
  public List<CompanyDTO> getAllEnterprises() {
    List<CompanyDTO> enterprisesList = new ArrayList<>();

    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT e.id_enterprise, e.trade_name, "
            + "e.designation, e.address, e.city, "
            + "e.means_of_communication, e.is_black_listed, "
            + "e.motivation_black_list, e.version_enterprises, "
            + "COUNT(DISTINCT i.id_internship) AS number_of_students_taken "
            + "FROM pae.enterprises e LEFT JOIN "
            + "pae.internship_supervisors s ON e.id_enterprise = s.enterprise "
            + "LEFT JOIN pae.internships i ON s.id_supervisor = i.internship_supervisor "
            + "LEFT JOIN pae.contacts c ON i.contact = c.id_contact "
            + "LEFT JOIN pae.inscriptions_ue u ON c.inscription_ue = u.id_inscription_ue "
            + "GROUP BY "
            + "e.id_enterprise, e.trade_name, e.designation, "
            + "e.address, e.city, e.means_of_communication, "
            + "e.is_black_listed, e.motivation_black_list "
            + "ORDER BY e.trade_name, e.designation;");
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        int numberOfStudents = resultSet.getInt("number_of_students_taken");
        CompanyDTO companyDTO = companyInfos(resultSet);
        companyDTO.setNumberOfStudents(numberOfStudents);
        enterprisesList.add(companyDTO);
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return enterprisesList;
  }

  /**
   * Retrieves a list of all enterprises from the database for a given school year.
   *
   * @param schoolYear The school year for which to retrieve the enterprises.
   * @return A list of CompanyDTO objects representing all enterprises for the given school year.
   * @throws FatalException If an error occurs during database access or processing.
   */
  public List<CompanyDTO> getAllEnterprises(String schoolYear) {
    List<CompanyDTO> enterprisesList = new ArrayList<>();

    String query = "SELECT e.id_enterprise, e.trade_name, e.designation, "
            + "e.address, e.city, e.means_of_communication, e.is_black_listed, "
            + "e.motivation_black_list, e.version_enterprises, "
            + "COUNT(DISTINCT i.id_internship) AS nombre_etudiants_stages "
            + "FROM pae.enterprises e "
            + "LEFT JOIN pae.contacts c ON e.id_enterprise = c.enterprise "
            + "LEFT JOIN pae.internships i ON c.id_contact = i.contact "
            + "LEFT JOIN pae.inscriptions_ue iu ON c.inscription_ue = iu.id_inscription_ue "
            + "WHERE iu.school_year = ? "
            + "GROUP BY e.id_enterprise, e.trade_name, e.designation, "
            + "e.address, e.city, e.means_of_communication, "
            + "e.is_black_listed, e.motivation_black_list "
            + "ORDER BY e.trade_name, e.designation;";

    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(query)) {
      preparedStatement.setString(1, schoolYear);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          int numberOfStudents = resultSet.getInt("nombre_etudiants_stages");
          CompanyDTO companyDTO = companyInfos(resultSet);
          companyDTO.setNumberOfStudents(numberOfStudents);
          enterprisesList.add(companyDTO);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return enterprisesList;
  }

  /**
   * Retrieves the number of students taken by a company.
   *
   * @param idCompany The identifier of the company.
   * @return The number of students taken by the company.
   * @throws FatalException If an error occurs during database access or processing.
   */
  public int numberOfStudentsTaken(int idCompany) {
    int numberOfStudents = 0;
    try {
      PreparedStatement preparedStatement = dalServices.getPreparedStatement(
          "SELECT COUNT(i.id_internship) AS number_of_students_taken "
              + "FROM pae.internships i, pae.internship_supervisors isu "
              + "WHERE isu.id_supervisor = i.internship_supervisor "
              + "AND isu.enterprise = ? ;"
      );
      preparedStatement.setInt(1, idCompany);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        numberOfStudents = resultSet.getInt("number_of_students_taken");
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return numberOfStudents;
  }

  /**
   * Updates a contact in the database.
   *
   * @param companyDTO The contact DTO to update.
   * @throws FatalException if an SQL error occurs.
   */
  public void update(CompanyDTO companyDTO) {
    try {
      String query = """
          UPDATE pae.enterprises
          SET trade_name = ?,
          designation = ?,
          address = ?,
          city = ?,
          means_of_communication = ?,
          is_black_listed = ?,
          motivation_black_list = ?,
          version_enterprises = version_enterprises + 1
          WHERE id_enterprise = ? AND version_enterprises = ?
          RETURNING version_enterprises;
          """;

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {
        ps.setString(1, companyDTO.getTradeName());
        ps.setString(2, companyDTO.getDesignation());
        ps.setString(3, companyDTO.getAddress());
        ps.setString(4, companyDTO.getCity());
        ps.setString(5, companyDTO.getMeansOfCommunication());
        ps.setBoolean(6, companyDTO.getIsBlackListed());
        ps.setString(7, companyDTO.getMotivationBlackList());
        ps.setInt(8, companyDTO.getId());
        ps.setInt(9, companyDTO.getVersionNumber());

        ResultSet rs = ps.executeQuery();
        int correctVersion = 0; // faire executeQuery avec RETURNING
        if (rs.next()) {
          correctVersion = rs.getInt("version_enterprises");
        }
        if (correctVersion == 0) {
          if (getCompanyById(companyDTO.getId()) == null) {
            throw new FatalException("Company not found");
          } else {
            throw new IllegalArgumentException("Error not the same version");
          }
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }
}