package be.vinci.pae.dal;

import be.vinci.pae.business.domain.CompanyDTO;
import java.sql.ResultSet;

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
   * Returns the company corresponding to the id.
   *
   * @param id the company's id
   *
   * @return the company corresponding to the id
   */
  CompanyDTO getCompanyById(int id);

}
