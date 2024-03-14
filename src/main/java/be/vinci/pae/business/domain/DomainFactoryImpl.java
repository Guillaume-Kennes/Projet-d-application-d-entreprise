package be.vinci.pae.business.domain;


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
}