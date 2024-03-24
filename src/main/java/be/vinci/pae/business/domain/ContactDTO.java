package be.vinci.pae.business.domain;

public interface ContactDTO {
  int getId();

  void setId(int id);

  String getState();

  void setState(String state);

  int getEnterprise();

  void setEnterprise(int enterprise);

  int getInscriptionUE();

  void setInscriptionUE(int inscriptionUE);

  String getReasonForRefusal();

  void setReasonForRefusal(String reasonForRefusal);

  boolean isFollowed();

  void setFollowed(boolean isFollowed);

  String getMeetingPlace();

  void setMeetingPlace(String meetingPlace);
}
