package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ViewCompanyImpl.class)
public interface ViewCompanyDTO {

  /**
   * Retrieves the ID of the item.
   *
   * @return the ID of the item.
   */
  int getId();

  /**
   * Sets the ID of the item.
   *
   * @param id the new ID of the item.
   */
  void setId(int id);

  String getTradeName();

  /**
   * Returns the enterprise's name.
   *
   * @return the enterprise's name
   */
  void setTradeName(String tradeName);


  String getDesignation();

  /**
   * Returns the enterprise's name.
   *
   * @return the enterprise's name
   */
  void setDesignation(String designation);


  String getAdress();

  /**
   * Returns the enterprise's name.
   *
   * @return the enterprise's name
   */
  void setAdress(String adress);


  String getCity();

  void setCity(String city);

  String getMeansOfCommunication();

  /**
   * Returns the enterprise's name.
   *
   * @return the enterprise's name
   */
  void setMeansOfCommunication(String meansOfCommunication);

}
