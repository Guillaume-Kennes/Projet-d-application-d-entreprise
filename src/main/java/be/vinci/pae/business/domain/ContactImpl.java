package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ContactImpl implements ContactDTO {

  private int id;
  private String state;
  private int enterprise;
  @JsonProperty("inscription_ue")
  private int inscriptionUe;
  private String reasonForRefusal;
  private boolean isFollowed;
  private String meetingPlace;
  private int userId;
  private String tradeName;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public int getEnterprise() {
    return enterprise;
  }

  @Override
  public void setEnterprise(int enterprise) {
    this.enterprise = enterprise;
  }

  @Override
  public int getInscriptionUe() {
    return inscriptionUe;
  }

  @Override
  public void setInscriptionUe(int inscriptionUe) {
    this.inscriptionUe = inscriptionUe;
  }

  @Override
  public String getReasonForRefusal() {
    return reasonForRefusal;
  }

  @Override
  public void setReasonForRefusal(String reasonForRefusal) {
    this.reasonForRefusal = reasonForRefusal;
  }

  @Override
  public boolean getIsFollowed() {
    return isFollowed;
  }

  @Override
  public void setIsFollowed(boolean isFollowed) {
    this.isFollowed = isFollowed;
  }

  @Override
  public String getMeetingPlace() {
    return meetingPlace;
  }

  @Override
  public void setMeetingPlace(String meetingPlace) {
    this.meetingPlace = meetingPlace;
  }

  @Override
  public int getUserId() {
    return userId;
  }

  @Override
  public void setUserId(int userId) {
    this.userId = userId;
  }

  // Add tradeName getter and setter
  public String getTradeName() {
    return tradeName;
  }

  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  @Override
  public String toString() {
    return "ContactImpl{" +
        "id=" + id +
        ", state='" + state + '\'' +
        ", enterprise=" + enterprise +
        ", inscriptionUe=" + inscriptionUe +
        ", reasonForRefusal='" + reasonForRefusal + '\'' +
        ", isFollowed=" + isFollowed +
        ", meetingPlace='" + meetingPlace + '\'' +
        '}';
  }
}
