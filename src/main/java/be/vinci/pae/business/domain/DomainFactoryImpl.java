package be.vinci.pae.business.domain;

import be.vinci.pae.dal.ContactDAOImpl;

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

  public ContactDTO getContact() {
    return new ContactImpl();
  }
}