package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ContactDTO;

public interface ContactDAO {

  ContactDTO insert(ContactDTO contactDTOToInsert);
}
