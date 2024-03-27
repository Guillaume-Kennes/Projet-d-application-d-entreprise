package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ViewCompanyDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.ViewCompanyDAO;
import be.vinci.pae.utils.exception.BusinessException;
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
  public ViewCompanyDTO addCompany(ViewCompanyDTO companyDTO) {
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
