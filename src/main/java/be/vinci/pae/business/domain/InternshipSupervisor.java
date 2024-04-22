package be.vinci.pae.business.domain;

/**
 * Represents an internship supervisor with various properties.
 * Extends the InternshipSupervisorDTO interface.
 */
public interface InternshipSupervisor extends InternshipSupervisorDTO {
  /**
   * Checks if there is a known supervisor for the specified enterprise.
   *
   * @param enterpriseId The ID of the enterprise to check.
   *
   * @return true if there is a known supervisor for the specified enterprise, false otherwise.
   */
  boolean knownSupervisorForThisEnterprise(int enterpriseId);
}
