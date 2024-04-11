package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.dal.CompanyDAO;
import be.vinci.pae.dal.DALServices;
import jakarta.inject.Inject;

/**
 * Implementation of the ViewCompanyUCC interface. Provides methods related to company operations.
 */
public class CompanyUCCImpl implements CompanyUCC {

  @Inject
  private CompanyDAO companyDAO;

  @Inject
  private DALServices dalServices;

  @Override
  public CompanyDTO addCompany(CompanyDTO companyDTO) {
    dalServices.start();
    try {
      System.out.println("CompanyUCCImpl ------> companyDTO : " + companyDTO);
      CompanyDTO company = companyDAO.insert(companyDTO);

      dalServices.commit();
      System.out.println("CompanyUCCImpl ------> company : " + company);
      return company;
    } catch (Exception e) {
      System.out.println("la ??????????");
      dalServices.rollBack();
      throw e;
    }
  }
}
