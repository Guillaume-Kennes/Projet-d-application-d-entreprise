package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ViewCompanyDTO;

/**
 * Represents a Company Use Case Controller (UCC) with methods related to company operations.
 */
public interface ViewCompanyUCC {

  ViewCompanyDTO addCompany(ViewCompanyDTO companyDTO);
}
