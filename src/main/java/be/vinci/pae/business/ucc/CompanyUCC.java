package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.CompanyDTO;

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



}
