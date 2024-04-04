package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a UEInscription data transfer object (DTO) with various properties. This interface
 * defines methods for accessing and modifying UEInscription-related information.
 */
@JsonDeserialize(as = UEInscriptionImpl.class)
public interface UEInscriptionDTO {

  User getStudent();

  /**
   * Sets the UEInscription's student.
   *
   * @param student the UEInscription's student to set
   */
  void setStudent(User student);

  String getSchoolYear();

  /**
   * Sets the UEInscription's school year.
   *
   * @param schoolYear the UEInscription's school year to set
   */
  void setSchoolYear(String schoolYear);

  /**
   * Gets the id of the UE inscription.
   *
   * @return the id of the UE inscription.
   */
  int getId();

  /**
   * Sets the UEInscription's id.
   *
   * @param id the UEInscription's id to set
   */
  void setId(int id);
}
