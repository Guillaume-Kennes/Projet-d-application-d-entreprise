package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT) // ignore all null fields in order to avoid sending props not linked to a JSON view
class UserImpl implements User {

  private int id;

  private String login;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

  private String password;

  private String nom;
  private String prenom;

  @Override
  public String getLogin() {
    return login;
  }

  @Override
  public void setLogin(String login) {
    this.login = login;
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
    return "{id:" + id + ", login:" + login + ", password:" + password + "}";
  }

}
