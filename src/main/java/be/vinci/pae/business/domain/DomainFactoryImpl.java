package be.vinci.pae.business.domain;

public class DomainFactoryImpl implements DomainFactory {
  @Override
  public User getUser() {
    return new UserImpl();
  }
}