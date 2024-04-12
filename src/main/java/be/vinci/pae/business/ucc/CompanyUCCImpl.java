package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.dal.CompanyDAO;
import be.vinci.pae.dal.DALServices;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of the ViewCompanyUCC interface.
 * Provides methods related to company operations.
 */
public class CompanyUCCImpl implements CompanyUCC {

  @Inject
  private CompanyDAO companyDAO;
  @Inject
  private DALServices dalServices;

  /**
   * Adds a new company to the database.
   *
   * @param companyDTO The CompanyDTO object representing the company to be added.
   * @return The CompanyDTO object representing the added company with the assigned ID.
   */
  @Override
  public CompanyDTO addCompany(CompanyDTO companyDTO) {
    dalServices.start();

    try {
      int id = companyDAO.insert(companyDTO);
      companyDTO.setId(id);

      dalServices.commit();
      return companyDTO;

    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }


  /**
   * Retrieves a list of all companies.
   *
   * @return A list of CompanyDTO objects representing all the companies.
   */
  @Override
  public List<CompanyDTO> getAllEnterprises(String sort) {
    dalServices.start();
    try {
      List<CompanyDTO> companiesList = companyDAO.getAllEnterprises(sort);

      dalServices.commit();
      return companiesList;

    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }


}
