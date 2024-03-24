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
   * Retrieves a ContactDTO object.
   *
   * @return A ContactDTO object.
   */
  ContactDTO getContact();

}
