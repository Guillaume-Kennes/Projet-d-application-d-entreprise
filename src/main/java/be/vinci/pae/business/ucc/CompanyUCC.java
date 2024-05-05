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
   * Blacklists the specified company with the given reason.
   *
   * @param companyDTO The CompanyDTO object representing the company to be blacklisted.
   * @param reason     The reason for blacklisting the company.
   * @return A CompanyDTO object representing the blacklisted company.
   */
  CompanyDTO blackList(CompanyDTO companyDTO, String reason);

  /**
   * Retrieves a list of all enterprises from the database.
   *
   * @return A list of CompanyDTO objects representing all enterprises.
   */
  List<CompanyDTO> getAllEnterprises();

  /**
   * Retrieves the number of students taken by a company.
   *
   * @param idCompany the id of the company
   * @return the number of students taken by the company
   */
  int numberOfStudentsTaken(int idCompany);
}
