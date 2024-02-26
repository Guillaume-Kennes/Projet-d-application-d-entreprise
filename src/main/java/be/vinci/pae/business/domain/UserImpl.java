package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class UserImpl implements User {

  private int id;
  private String email;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String nom;
  private String prenom;

  @Override
<<<<<<< HEAD
=======
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
>>>>>>> 4811fccfeb4df4d53b959ce47e93a56ce551ec4f
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
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Override
  public String toString() {
<<<<<<< HEAD
    return "{id:" + id + ", email:" + email + ", password:" + password + "}";
=======
    return "{id:" + id + ", login:" + email + ", password:" + password + "}";
>>>>>>> 4811fccfeb4df4d53b959ce47e93a56ce551ec4f
  }

}
