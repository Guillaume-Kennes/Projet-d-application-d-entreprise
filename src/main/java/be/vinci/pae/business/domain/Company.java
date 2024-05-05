package be.vinci.pae.business.domain;

/**
 * Represents a company with various properties. Extends the ViewCompanyDTO interface.
 */
public interface Company extends CompanyDTO {

  /**
   * Checks if the company is blacklisted.
   *
   * @param companyDTO The company DTO to check if black listed.
   * @return true if the company is blacklisted, false otherwise.
   */
  boolean isBlackListed(CompanyDTO companyDTO);
}
