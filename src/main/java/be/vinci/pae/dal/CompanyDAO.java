package be.vinci.pae.dal;

import be.vinci.pae.business.domain.CompanyDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Represents a Data Access Object (DAO) for managing company-related data.
 * Provides methods to retrieve company information based on ID.
 */
public interface CompanyDAO {

  /**
   * Inserts a new item in the system.
   *
   * @param companyDTOToInsert ItemDTO object containing the information of the item to be
   *                           inserted.
   * @return int of the object created
   */
  int insert(CompanyDTO companyDTOToInsert);

  /**
   * Returns the information of a company.
   *
   * @param resultSet the given resultSet
   *
   * @return the company corresponding to that result set
   */
  CompanyDTO companyInfos(ResultSet resultSet);


  /**
   * Retrieves information of the company corresponding to the specified identifier.
   *
   * @param id The identifier of the company to retrieve.
   *
   * @return A {@code CompanyDTO} object representing the information of the found company.
   *
   * @throws SQLException If an error occurs while accessing the database.
   */
  CompanyDTO getCompanyById(int id) throws SQLException;

  /**
   * Retrieves the list of all companies available in the system.
   *
   * @return A list containing CompanyDTO objects representing all companies.
   *     If no companies are found, the list will be empty.
   */
  List<CompanyDTO> getAllEnterprises();
}
