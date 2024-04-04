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

  boolean initieState(ContactDTO contact);
  boolean prisState(ContactDTO contact);

  boolean isFollowed(ContactDTO contact);
}
