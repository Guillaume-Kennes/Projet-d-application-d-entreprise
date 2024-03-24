package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;

public interface ContactUCC {

  ContactDTO meetCompany(ContactDTO contact, String place);

  ContactDTO getContactById(int id_contact);

  ContactDTO stopFollowing(ContactDTO contact);

  ContactDTO companyRefusedInternship(ContactDTO contact, String reason);

}
