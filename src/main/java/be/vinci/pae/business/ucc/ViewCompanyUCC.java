package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ViewCompanyDTO;

/**
 * Represents a Company Use Case Controller (UCC) with methods related to company operations.
 */
public interface ViewCompanyUCC {

  /**
   * Add a new company to the database.
   *
   * @param companyDTO the company to be added
   *
   * @return the added company
   */
  ViewCompanyDTO addCompany(ViewCompanyDTO companyDTO);
}
