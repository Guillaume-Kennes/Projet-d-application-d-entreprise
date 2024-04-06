package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Implementation class for the User interface.
 * This class provides implementations for various user-related methods.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class UserImpl implements User {

  private String email;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String lastName;
  private String firstName;
  private String phoneNumber;
  private Date registrationDate;
  private String role = " ";
  private int id;

  /**
   * Default constructor for UserImpl.
   */
  public UserImpl() {
  }

  /**
   * Get the email of the user.
   *
   * @return The email of the user.
   */
  @Override
  public String getEmail() {
    return email;
  }

  /**
   * Set the email of the user.
   *
   * @param email The email to set.
   */
  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get the password of the user.
   *
   * @return The password of the user.
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Set the password of the user.
   *
   * @param password The password to set.
   */
  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get the last name of the user.
   *
   * @return The last name of the user.
   */
  @Override
  public String getLastName() {
    return lastName;
  }

  /**
   * Set the last name of the user.
   *
   * @param lastName The last name to set.
   */
  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get the first name of the user.
   *
   * @return The first name of the user.
   */
  @Override
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Set the first name of the user.
   *
   * @param firstName The first name to set.
   */
  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get the phone number of the user.
   *
   * @return The phone number of the user.
   */
  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Set the phone number of the user.
   *
   * @param phoneNumber The phone number to set.
   */
  @Override
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Get the registration date of the user.
   *
   * @return The registration date of the user.
   */
  @Override
  public Date getRegistrationDate() {
    return registrationDate;
  }

  /**
   * Set the registration date of the user.
   *
   * @param registrationDate The registration date to set.
   */
  @Override
  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  /**
   * Get the role of the user.
   *
   * @return The role of the user.
   */
  @Override
  public String getRole() {
    return role;
  }

  /**
   * Set the role of the user.
   *
   * @param role The role to set.
   */
  @Override
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * Get the ID of the user.
   *
   * @return The ID of the user.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Set the ID of the user.
   *
   * @param id The ID to set.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Check if the provided password matches the user's password.
   *
   * @param password The password to check.
   *
   * @return True if the password matches, false otherwise.
   */
  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  /**
   * Hashes the provided password using the BCrypt algorithm.
   *
   * @param password The password to be hashed.
   *
   * @return A hashed representation of the provided password.
   */
  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Checks whether the current user has teacher privileges.
   *
   * @return true if the current user has teacher privileges, false otherwise
   */
  @Override
  public boolean isTeacher() {
    return this.role.equals("Professeur");
  }

  /**
   * Checks whether the current user has administrative privileges.
   *
   * @return true if the current user has administrative privileges, false otherwise
   */
  @Override
  public boolean isAdmin() {
    return this.role.equals("Administratif");
  }

  /**
   * Get a string representation of the UserImpl object.
   *
   * @return A string representation of the UserImpl object.
   */
  @Override
  public String toString() {
    return "{login:" + email + ", password:" + password + "}";
  }
}
