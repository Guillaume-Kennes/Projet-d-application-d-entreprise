package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a company data transfer object (DTO) with various properties. This interface defines
 * methods for accessing and modifying company-related information.
 */
@JsonDeserialize(as = CompanyImpl.class)
public interface CompanyDTO {

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

  /**
   * Returns the enterprise's name.
   *
   * @return the enterprise's name
   */
  String getTradeName();

  /**
   * Sets the name of the company.
   *
   * @param tradeName the new name of the item.
   */
  void setTradeName(String tradeName);

  /**
   * Returns the enterprise's designation.
   *
   * @return the enterprise's designation
   */
  String getDesignation();

  /**
   * Sets the designation of the company.
   *
   * @param designation the new designation of the item.
   */
  void setDesignation(String designation);

  /**
   * Returns the enterprise's address.
   *
   * @return the enterprise's address
   */
  String getAddress();

  /**
   * Sets the address of the company.
   *
   * @param address the new address of the item.
   */
  void setAddress(String address);

  /**
   * Returns the enterprise's city.
   *
   * @return the enterprise's city
   */
  String getCity();

  /**
   * Sets the city of the company.
   *
   * @param city the new city of the item.
   */
  void setCity(String city);

  /**
   * Returns the enterprise's means of communication.
   *
   * @return the enterprise's means of communication
   */
  String getMeansOfCommunication();

  /**
   * Sets the means of communication of the company.
   *
   * @param meansOfCommunication the new means of communication.
   */
  void setMeansOfCommunication(String meansOfCommunication);

}
