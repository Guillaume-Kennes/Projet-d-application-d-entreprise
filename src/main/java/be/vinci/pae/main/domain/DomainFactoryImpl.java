package be.vinci.pae.main.domain;

public class DomainFactoryImpl implements DomainFactory {
  @Override
  public User getUser() {
    return new UserImpl();
  }
}