package be.vinci.pae.business.domain;

/**
 * Represents a contact with various properties.
 * Extends the ContactDTO interface.
 */
public interface Contact extends ContactDTO {

  /**
   * Checks if the user role is student.
   *
   * @param idUser The ID of the user.
   *
   * @return true if the user role is student, false otherwise.
   */
  boolean userRoleIsStudent(int idUser);

  /**
   * Checks if the school year is correct.
   *
   * @return true if the school year is correct, false otherwise.
   */
  boolean correctSchoolYear();


  /**
   * Initializes the state of the contact.
   *
   * @param contact The contact DTO to initialize the state for.
   * @return true if the state was successfully initiated, false otherwise.
   */
  boolean initieState(ContactDTO contact);


  /**
   * Checks if the contact state is 'pris'.
   *
   * @param contact The contact DTO to check the state for.
   * @return true if the contact state is 'pris', false otherwise.
   */
  boolean prisState(ContactDTO contact);


  /**
   * Checks if the contact is being followed.
   *
   * @param contact The contact DTO to check if being followed.
   * @return true if the contact is being followed, false otherwise.
   */
  boolean isFollowed(ContactDTO contact);
}
