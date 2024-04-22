package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Implementation class for the ViewCompany interface. This class provides implementations for
 * various company-related methods.
 */
@JsonInclude(Include.USE_DEFAULTS)
public class CompanyImpl implements Company {

  private int id;
  private String tradeName;
  private String designation;
  private String address;
  private String city;
  private String meansOfCommunication;
  private boolean isBlackListed;
  private String motivationBlackList;
  private int versionNumber;

  /**
   * Constructs a new CompanyImpl object with default values. This constructor initializes a new
   * CompanyImpl object with default values for all fields. The default values for numeric fields
   * are typically 0, and for String fields, null.
   */
  public CompanyImpl() {
  }


  /**
   * Returns the unique identifier of the company.
   *
   * @return The unique identifier of the company.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the company.
   *
   * @param id The unique identifier to set.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Returns the trade name of the company.
   *
   * @return The trade name of the company.
   */
  @Override
  public String getTradeName() {
    return tradeName;
  }


  /**
   * Sets the trade name of the company.
   *
   * @param tradeName The trade name to set.
   */
  @Override
  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }


  /**
   * Returns the designation of the company.
   *
   * @return The designation of the company.
   */
  @Override
  public String getDesignation() {
    return designation;
  }


  /**
   * Sets the designation of the company.
   *
   * @param designation The designation to set.
   */
  @Override
  public void setDesignation(String designation) {
    this.designation = designation;
  }


  /**
   * Returns the address of the company.
   *
   * @return The address of the company.
   */
  @Override
  public String getAddress() {
    return address;
  }


  /**
   * Sets the address of the company.
   *
   * @param address The address to set.
   */
  @Override
  public void setAddress(String address) {
    this.address = address;
  }


  /**
   * Returns the city of the company.
   *
   * @return The city of the company.
   */
  @Override
  public String getCity() {
    return city;
  }


  /**
   * Sets the city of the company.
   *
   * @param city The city to set.
   */
  @Override
  public void setCity(String city) {
    this.city = city;
  }


  /**
   * Returns the means of communication of the company.
   *
   * @return The means of communication of the company.
   */
  @Override
  public String getMeansOfCommunication() {
    return meansOfCommunication;
  }


  /**
   * Sets the means of communication of the company.
   *
   * @param meansOfCommunication The means of communication to set.
   */
  @Override
  public void setMeansOfCommunication(String meansOfCommunication) {
    this.meansOfCommunication = meansOfCommunication;
  }

  /**
   * Returns whether the company is blacklisted.
   *
   * @return true if the company is blacklisted, false otherwise
   */
  @Override
  public boolean getIsBlackListed() {
    return isBlackListed;
  }

  /**
   * Sets whether the company is blacklisted.
   *
   * @param isBlackListed true if the company is blacklisted, false otherwise
   */
  @Override
  public void setIsBlackListed(boolean isBlackListed) {
    this.isBlackListed = isBlackListed;
  }


  @Override
  public String getMotivationBlackList() {
    return motivationBlackList;
  }

  @Override
  public void setMotivationBlackList(String motivationBlackList) {
    this.motivationBlackList = motivationBlackList;
  }


  public int getVersionNumber() {
    return versionNumber;
  }

  public void setVersionNumber(int versionNumber) {
    this.versionNumber = versionNumber;
  }

  /**
   * Get the value of the isBlackListed boolean.
   *
   * @return The value of the isBlackListed boolean
   */
  @Override
  public boolean isBlackListed() {
    return isBlackListed;
  }

  /**
   * Set the value of the isBlackListed boolean.
   *
   * @param isBlackListed The value to set.
   */
  @Override
  public void setBlackListed(boolean isBlackListed) {
    this.isBlackListed = isBlackListed;
  }

  /**
   * Checks if the company is black listed. This method checks if the given company is black
   * listed.
   *
   * @param company The company to check.
   * @return true if the company is black listed, false otherwise.
   */
  public boolean isBlackListed(CompanyDTO company) {
    return company.isBlackListed();
  }

  /**
   * Returns a string representation of the company.
   *
   * @return A string representation of the company.
   */
  @Override
  public String toString() {
    return "CompanyImpl{"
        + "tradeName='" + tradeName + '\''
        + ", designation='" + designation + '\''
        + ", address='" + address + '\''
        + ", city='" + city + '\''
        + ", meansOfCommunication='" + meansOfCommunication + '\''
        + ", isBlackListed=" + isBlackListed
        + '}';
  }
}

