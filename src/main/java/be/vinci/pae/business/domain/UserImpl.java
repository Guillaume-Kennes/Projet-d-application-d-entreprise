package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class UserImpl implements User {

  private int id;
  private String email;
  //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String lastName;
  private String firstName;
  private String phoneNumber;
  private Date registrationDate;
  private String role;

  public UserImpl() {
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
    return this.lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getFirstName() {
    return this.firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
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
  public Date getRegistrationDate() {
    return registrationDate;
  }

  @Override
  public void setRegistrationDate(Date registrationDate) {
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
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Override
  public String toString() {
    return "{id:" + id + ", login:" + email + ", password:" + password + "}";
  }

}
