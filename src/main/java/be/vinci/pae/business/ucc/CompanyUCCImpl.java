package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.CompanyDAO;
import jakarta.inject.Inject;

/**
 * Implementation of the ViewCompanyUCC interface.
 * Provides methods related to company operations.
 */
public class CompanyUCCImpl implements CompanyUCC {

  @Inject
  private CompanyDAO itemDAO;
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
      System.out.println("e.getMessage() = " + e.getMessage());
      throw e;
    }
  }
}
