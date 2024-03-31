package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.ViewCompanyDAO;
import jakarta.inject.Inject;

/**
 * Implementation of the ViewCompanyUCC interface.
 * Provides methods related to company operations.
 */
public class ViewCompanyUCCImpl implements ViewCompanyUCC {

  @Inject
  private ViewCompanyDAO itemDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public CompanyDTO addCompany(CompanyDTO companyDTO) {
    try {
      dalServices.start();

      int id = itemDAO.insert(companyDTO);
      companyDTO.setId(id);

      dalServices.commit();
      return companyDTO;

    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }
}
