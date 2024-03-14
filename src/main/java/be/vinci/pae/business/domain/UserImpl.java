package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class UserImpl implements User {

  private String email;
  //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;


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
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }


}
