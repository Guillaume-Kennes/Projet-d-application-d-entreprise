package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class UserImpl implements User {

  private int id;
  private String email;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String lastName;
  private String firstName;
  private String phoneNumber;
  private LocalDate registration_date;
  private String role;

  @Override

  public String getEmail() {
    return this.email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }


  @Override
  public String getPassword() {
    return this.password;
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

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public LocalDate getRegistration_date() {
    return this.registration_date;
  }

  public void setRegistration_date(LocalDate registrationDate) {
    this.registration_date = registrationDate;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    if(!role.equals("professeur") && !role.equals("étudiant") && !role.equals("administratif"))
      throw new IllegalArgumentException("Rôle non-autorisé");
    this.role = role;
  }
}
