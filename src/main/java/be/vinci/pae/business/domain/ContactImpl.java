package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class ContactImpl implements Contact {

  private int id;
  private String state;
  private int enterprise;
  private int inscriptionUE;
  private String reasonForRefusal;
  private boolean isFollowed;
  private String meetingPlace;

  public ContactImpl() {
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
  public int getInscriptionUE() {
    return inscriptionUE;
  }

  @Override
  public void setInscriptionUE(int inscriptionUE) {
    this.inscriptionUE = inscriptionUE;
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
  public boolean isFollowed() {
    return isFollowed;
  }

  @Override
  public void setFollowed(boolean followed) {
    isFollowed = followed;
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
  public void setReasonRefusal(String reasonForRefusal) {

  }

  @Override
  public void setCompany(ViewCompany company) {

  }

  @Override
  public void setUeInscription(ViewUEInscription ueInscription) {

  }

  @Override
  public ViewCompanyDTO getCompany() {
    return null;
  }

  public boolean userRoleIsStudent(int idUser) {
    return false;
  }

  public boolean correctSchoolYear() {
    return false;
  }
}
