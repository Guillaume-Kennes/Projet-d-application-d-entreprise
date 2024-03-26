package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {

  /**
   * Retrieves the ID of the item.
   *
   * @return the ID of the item.
   */
  int getId();

  /**
   * Sets the ID of the item.
   *
   * @param id the new ID of the item.
   */
  void setId(int id);

  String getState();

  /**
   * Returns the enterprise's name.
   */
  void setState(String state);


  int getEnterprise();

  /**
   * Returns the enterprise's name.
   */
  void setEnterprise(int enterprise);


  int getInscriptionUe();

  /**
   * Returns the enterprise's name.
   */
  void setInscriptionUe(int inscriptionUe);


  String getReasonForRefusal();

  void setReasonForRefusal(String reasonForRefusal);

  boolean getIsFollowed();

  /**
   * Returns the enterprise's name.
   */
  void setIsFollowed(boolean isFollowed);

  String getMeetingPlace();

  /**
   * Returns the enterprise's name.
   */
  void setMeetingPlace(String meetingPlace);

  int getUserId();

  void setUserId(int userId);

  // New method to retrieve trade name
  String getTradeName();

  void setTradeName(String tradeName);
}

/*
id_contact => incremente tout seul;
state => initié;
entreprise => l entreprise qui est selectrionnée ou créée;
inscription_UE => l utilisateur connecté;
reason_for_refusal => null;
is_followed => true par defaut;
meeting_place => null;
*/