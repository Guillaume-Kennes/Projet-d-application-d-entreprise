package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ViewCompanyDTO;

public interface ViewCompanyDAO {

  /**
   * Inserts a new item in the system.
   *
   * @param companyDTOToInsert ItemDTO object containing the information of the item to be
   *                           inserted.
   * @return int of the object created
   */
  int insert(ViewCompanyDTO companyDTOToInsert);

}
