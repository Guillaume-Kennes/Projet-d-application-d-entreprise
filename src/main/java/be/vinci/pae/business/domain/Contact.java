package be.vinci.pae.business.domain;

/**
 * Interface Contact extending ContactDTO.
 */
public interface Contact extends ContactDTO{
  /**
   * Checks if the user role is student.
   *
   * @param id_user The ID of the user.
   *
   * @return true if the user role is student, false otherwise.
   */
  boolean userRoleIsStudent(int id_user);

  /**
   * Checks if the school year is correct.
   *
   * @return true if the school year is correct, false otherwise.
   */
  boolean correctSchoolYear();
}
