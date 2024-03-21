package be.vinci.pae.business.domain;

/**
 * Implementation of the DomainFactory interface.
 * Creates and returns a new instance of UserDTO.
 */
public class DomainFactoryImpl implements DomainFactory {
  /**
   * Creates and returns a new instance of UserDTO.
   *
   * @return A new UserDTO instance.
   */
  @Override
  public UserDTO getUser() {
    return new UserImpl();
  }

  /**
   * Creates and returns a new instance of InternshipDTO.
   *
   * @return A new InternshipDTO instance.
   */
  public InternshipDTO getInternship() {
    return new InternshipImpl();
  }

  /**
   * Creates and returns a new instance of InternshipSupervisorDTO.
   *
   * @return A new InternshipSupervisorDTO instance.
   */
  public InternshipSupervisorDTO getInternshipSupervisor() {
    return new InternshipSupervisorImpl();
  }

  /**
   * Creates and returns a new instance of ViewCompanyDTO.
   *
   * @return A new ViewCompanyDTO instance.
   */
  public ViewCompanyDTO getCompany() {
    return new ViewCompanyImpl();
  }

  /**
   * Creates and returns a new instance of ViewUEInscriptionDTO.
   *
   * @return A new ViewUEInscriptionDTO instance.
   */
  public ViewUEInscriptionDTO getUEInscription() {
    return new ViewUEInscriptionImpl();
  }

  /**
   * Creates and returns a new instance of ViewContactDTO.
   *
   * @return A new ViewContactDTO instance.
   */
  public ViewContactDTO getContact() {
    return new ViewContactImpl();
  }
}