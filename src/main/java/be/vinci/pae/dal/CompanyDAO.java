package be.vinci.pae.dal;

import be.vinci.pae.business.domain.CompanyDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) for managing company-related data. Provides methods to
 * retrieve company information based on ID.
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
   * @return the company corresponding to that result set
   */
  CompanyDTO companyInfos(ResultSet resultSet);

  /**
   * Retrieves the company information associated with the specified company ID.
   *
   * @param id The ID of the company to retrieve information for.
   * @return A CompanyDTO object representing the company information.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  CompanyDTO getCompanyById(int id) throws SQLException;

}
