package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a UEInscription data transfer object (DTO) with various properties.
 * This interface defines methods for accessing and modifying UEInscription-related information.
 */
@JsonDeserialize(as = UserImpl.class)
public interface ViewUEInscriptionDTO {

  /**
   * Sets the UEInscription's id.
   *
   * @param id the UEInscription's id to set
   */
  void setId(int id);

  /**
   * Sets the UEInscription's student.
   *
   * @param student the UEInscription's student to set
   */
  void setStudent(User student);

  /**
   * Sets the UEInscription's school year.
   *
   * @param schoolYear the UEInscription's school year to set
   */
  void setSchoolYear(String schoolYear);
}
