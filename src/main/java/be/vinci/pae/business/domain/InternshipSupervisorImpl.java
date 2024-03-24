package be.vinci.pae.business.domain;

/**
 * Implementation class for the InternshipSupervisor interface.
 * This class provides implementations for various supervisor-related methods.
 */
public class InternshipSupervisorImpl implements InternshipSupervisor {

  private int id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;
  private ViewCompany company;

  /**
   * Default constructor for InternshipSupervisorImpl.
   */
  public InternshipSupervisorImpl() {
  }

  /**
   * Get the id of the internship supervisor.
   *
   * @return The id of the internship supervisor
   */
  public int getId() {
    return this.id;
  }

  /**
   * Set the id of the internship supervisor.
   *
   * @param id The id to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get the first name of the internship supervisor.
   *
   * @return The first name of the internship supervisor
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Get the company of the internship supervisor.
   *
   * @return The company of the internship supervisor
   */
  public ViewCompany getCompany() {
    return this.company;
  }
  /**
   * Set the first name of the internship supervisor.
   *
   * @param firstName The first name to set.
   */
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * Get the last name of the internship supervisor.
   *
   * @return The last name of the internship supervisor
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Set the last name of the internship supervisor.
   *
   * @param lastName The last name to set.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get the phone number of the internship supervisor.
   *
   * @return The phone number of the internship supervisor
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * Set the phone number of the internship supervisor.
   *
   * @param phoneNumber The phone number to set.
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Get the email of the internship supervisor.
   *
   * @return The email of the internship supervisor
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Set the email of the internship supervisor.
   *
   * @param email The email to set.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Set the company of the internship supervisor.
   *
   * @param company The company to set.
   */
  public void setCompany(ViewCompany company) {
    this.company = company;
  }
}
