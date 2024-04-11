package be.vinci.pae.dal;

import be.vinci.pae.business.domain.Company;
import be.vinci.pae.business.domain.CompanyDTO;
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
  public int insert(CompanyDTO companyDTO) {
    int generatedId = 0;
    try {
      String query = """
            INSERT INTO pae.enterprises (
              trade_name,
              designation,
              address,
              city,
              means_of_communication,
              is_black_listed,
              motivation_blacklist)
            VALUES (?, ?, ?, ?, ?, false, null)
            RETURNING id_enterprise;
          """;

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {

        ps.setString(1, companyDTO.getTradeName());
        ps.setString(2, companyDTO.getDesignation());
        ps.setString(3, companyDTO.getAddress());
        ps.setString(4, companyDTO.getCity()); // city
        ps.setString(5, companyDTO.getMeansOfCommunication());

      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return generatedId;
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
        "SELECT * FROM pae.enterprises"
    );
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        CompanyDTO companyDTO = myDomainFactory.getCompany();
        companyDTO.setTradeName(resultSet.getString("trade_name"));
        companyDTO.setDesignation(resultSet.getString("designation"));
        companyDTO.setAddress(resultSet.getString("address"));
        companyDTO.setCity(resultSet.getString("city"));
        companyDTO.setMeansOfCommunication(resultSet.getString("means_of_communication"));
        enterprisesList.add(companyDTO);
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return enterprisesList;
  }
}