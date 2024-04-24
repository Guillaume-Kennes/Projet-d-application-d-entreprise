package be.vinci.pae.dal;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ViewCompanyDAO interface. Provides methods for retrieving company-related
 * data from the database.
 */
public class CompanyDAOImpl implements CompanyDAO {

  @Inject
  private DALBackServices dalServices;
  @Inject
  private DomainFactory myDomainFactory;

  @Override
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
        ps.setString(2, companyDTO.getDesignation());
        ps.setString(3, companyDTO.getAddress());
        ps.setString(4, companyDTO.getCity()); // city
        ps.setString(5, companyDTO.getMeansOfCommunication());

        ps.executeQuery();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    System.out.println(
        "CompanyDAOImpl companyDTO : " + "\n"
            + "Trade name : " + companyDTO.getTradeName() + "\n"
            + "Designation : " + companyDTO.getDesignation() + "\n"
            + "Address : " + companyDTO.getAddress() + "\n"
            + "City : " + companyDTO.getCity() + "\n"
            + "MeansOfCommunication : " + companyDTO.getMeansOfCommunication() + "\n"
    );
    System.out.println("CompanyDAOImpl insert : " + companyDTO);
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
        "SELECT * FROM pae.enterprises ORDER BY trade_name, designation");
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {

        CompanyDTO companyDTO = companyInfos(resultSet);
        enterprisesList.add(companyDTO);
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
        System.out.println("CompanyDAOImpl numberOfStudentsTaken : " + numberOfStudents);
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return numberOfStudents;
  }

}