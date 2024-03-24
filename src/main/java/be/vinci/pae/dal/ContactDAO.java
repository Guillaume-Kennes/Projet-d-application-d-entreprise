package be.vinci.pae.dal;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import java.sql.ResultSet;

public interface ContactDAO {
  ContactDTO getContactById(int id_contact);

  ContactDTO contactInfos(ResultSet resultSet);

  void update(ContactDTO contactDTO);
}
