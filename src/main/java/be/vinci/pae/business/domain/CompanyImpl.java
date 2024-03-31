package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Implementation class for the ViewCompany interface. This class provides implementations for
 * various company-related methods.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CompanyImpl implements Company {

  private int id;
  private String tradeName;
  private String designation;
  private String address;
  private String city;
  private String meansOfCommunication;


  public CompanyImpl() {
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getTradeName() {
    return tradeName;
  }

  @Override
  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  @Override
  public String getDesignation() {
    return designation;
  }

  @Override
  public void setDesignation(String designation) {
    this.designation = designation;
  }

  @Override
  public String getAddress() {
    return address;
  }

  @Override
  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String getMeansOfCommunication() {
    return meansOfCommunication;
  }

  @Override
  public void setMeansOfCommunication(String meansOfCommunication) {
    this.meansOfCommunication = meansOfCommunication;
  }

  @Override
  public String toString() {
    return "ContactImpl{"
        + "tradeName='" + tradeName + '\''
        + ", designation='" + designation + '\''
        + ", adress='" + address + '\''
        + ", meansOfCommunication='" + meansOfCommunication + '\''
        + '}';
  }
}

