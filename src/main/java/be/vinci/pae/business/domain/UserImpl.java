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
  private int versionNumber;

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
  public String getEmail() {
    return email;
  }

  /**
   * Set the email of the user.
   *
   * @param email The email to set.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get the password of the user.
   *
   * @return The password of the user.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set the password of the user.
   *
   * @param password The password to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get the last name of the user.
   *
   * @return The last name of the user.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Set the last name of the user.
   *
   * @param lastName The last name to set.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get the first name of the user.
   *
   * @return The first name of the user.
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Set the first name of the user.
   *
   * @param firstName The first name to set.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get the phone number of the user.
   *
   * @return The phone number of the user.
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Set the phone number of the user.
   *
   * @param phoneNumber The phone number to set.
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Get the registration date of the user.
   *
   * @return The registration date of the user.
   */
  public Date getRegistrationDate() {
    return registrationDate;
  }

  /**
   * Set the registration date of the user.
   *
   * @param registrationDate The registration date to set.
   */
  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  /**
   * Get the role of the user.
   *
   * @return The role of the user.
   */
  public String getRole() {
    return role;
  }

  /**
   * Set the role of the user.
   *
   * @param role The role to set.
   */
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * Get the ID of the user.
   *
   * @return The ID of the user.
   */
  public int getId() {
    return id;
  }

  /**
   * Set the ID of the user.
   *
   * @param id The ID to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Retrieves the version number.
   *
   * @return The current version number.
   */
  public int getVersionNumber() {
    return this.versionNumber;
  }

  /**
   * Sets the version number to the specified value.
   *
   * @param versionNumber The version number to set.
   * @return The updated version number after setting.
   */
  public int setVersionNumber(int versionNumber) {
    return this.versionNumber = versionNumber;
  }

  /**
   * Check if the provided password matches the user's password.
   *
   * @param password The password to check.
   *
   * @return True if the password matches, false otherwise.
   */
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
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Checks whether the current user has teacher privileges.
   *
   * @return true if the current user has teacher privileges, false otherwise
   */
  public boolean isTeacher() {
    return this.role.equals("Professeur");
  }

  /**
   * Checks whether the current user has administrative privileges.
   *
   * @return true if the current user has administrative privileges, false otherwise
   */
  public boolean isAdmin() {
    return this.role.equals("Administratif");
  }

  /**
   * Checks if the provided email belongs to a Vinci member.
   *
   * @param email The email address to be checked.
   * @return true if the email belongs to a Vinci member, false otherwise.
   */
  public boolean emailIsVinci(String email) {
    return email.endsWith("@vinci.be");
  }

  /**
   * Checks if the provided email belongs to a student.
   *
   * @param email The email address to be checked.
   * @return true if the email belongs to a student, false otherwise.
   */
  public boolean emailIsStudent(String email) {
    return email.endsWith("@student.vinci.be");
  }

  /**
   * Get a string representation of the UserImpl object.
   *
   * @return A string representation of the UserImpl object.
   */
  public String toString() {
    return "{login:" + email + ", password:" + password + "}";
  }
}
