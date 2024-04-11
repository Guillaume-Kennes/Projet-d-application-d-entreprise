package be.vinci.pae.business.domain;

/**
 * Generates an object.
 */
public interface DomainFactory {

  /**
   * Gets a user.
   *
   * @return a user
   */
  UserDTO getUser();

  /**
   * Gets an internship.
   *
   * @return an internship
   */
  InternshipDTO getInternship();

  /**
   * Gets an internship supervisor.
   *
   * @return an internship supervisor
   */
  InternshipSupervisorDTO getInternshipSupervisor();

  /**
   * Gets a company.
   *
   * @return a company
   */
  ViewCompanyDTO getCompany();

  /**
   * Gets a UE Inscription.
   *
   * @return a UE Inscription
   */
  UEInscriptionDTO getUEInscription();

  /**
   * Gets a contact.
   *
   * @return a contact
   */
  ContactDTO getContact();

}
