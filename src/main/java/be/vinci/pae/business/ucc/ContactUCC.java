package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ContactDTO;

public interface ContactUCC {

  ContactDTO meetCompany(ContactDTO contact, String param);

  ContactDTO getContactById(int id_contact);

  ContactDTO stopFollowing(ContactDTO contact);

}
