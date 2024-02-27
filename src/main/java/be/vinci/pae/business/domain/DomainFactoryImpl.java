package be.vinci.pae.business.domain;

public class DomainFactoryImpl implements DomainFactory {
  @Override
  public UserDTO getUser() {
    return new UserImpl();
  }
}