package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class UserImpl implements User {

  private String email;
  //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String lastName;
  private String firstName;
  private String phoneNumber;
  private String registrationDate;
  private String role;
  private int id;

  public UserImpl() {
  }

  @Override

  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }


  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {

  }

  @Override
  public String getFirstName() {
    return this.firstName;
  }

  @Override
  public void setFirstName(String lastName) {
    this.lastName = lastName;
  }


  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String getRegistrationDate() {
    return registrationDate;
  }

  @Override
  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public String getRole() {
    return role;
  }

  @Override
  public void setRole(String role) {
    this.role = role;
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
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String toString() {
    return "{login:" + email + ", password:" + password + "}";
  }

}
