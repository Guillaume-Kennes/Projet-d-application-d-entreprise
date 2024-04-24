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
   *
   * @return the added company
   */
  CompanyDTO addCompany(CompanyDTO companyDTO);


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
   *
   * @return the number of students taken by the company
   */
  int numberOfStudentsTaken(int idCompany);

}
