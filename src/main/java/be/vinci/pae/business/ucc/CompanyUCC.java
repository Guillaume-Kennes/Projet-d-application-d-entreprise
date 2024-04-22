package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.CompanyDTO;
import java.util.List;

/**
 * Represents a Company Use Case Controller (UCC) with methods related to company operations.
 */
public interface CompanyUCC {

  /**
   * Add a new company to the database.
   *
   * @param companyDTO the company to be added
   * @return the added company
   */
  CompanyDTO addCompany(CompanyDTO companyDTO);


  /**
   * Retrieves a company by its ID.
   *
   * @param idCompany The ID of the company.
   * @return The company with the given ID.
   */
  CompanyDTO getCompanyById(int idCompany);

  /**
   * Stops following a contact.
   *
   * @param companyDTO The company to blacklist.
   * @return The updated company after the blacklist.
   */
  CompanyDTO blackList(CompanyDTO companyDTO);


  /**
   * Retrieves a list of all enterprises from the database.
   *
   * @return A list of CompanyDTO objects representing all enterprises.
   */
  List<CompanyDTO> getAllEnterprises();
}
