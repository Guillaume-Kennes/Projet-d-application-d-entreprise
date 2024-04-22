package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.Company;
import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.dal.CompanyDAO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.utils.exception.BusinessException;
import be.vinci.pae.utils.exception.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of the ViewCompanyUCC interface. Provides methods related to company operations.
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

  @Override
  public CompanyDTO blackList(CompanyDTO companyDTO) {
    System.out.println("Contact " + companyDTO);
    dalServices.start();
    try {
      if (companyDTO == null) {
        dalServices.rollBack();
        throw new NotFoundException("Company not found");
      }

      Company companyBiz = (Company) companyDTO;

      System.out.println("CompanyUCCImpl " + companyBiz.isBlackListed(companyDTO));
      if (companyBiz.isBlackListed(companyDTO)) {
        dalServices.rollBack();
        throw new BusinessException("Invalid company state");

      } else {
        companyDTO.setBlackListed(true);

        companyDAO.update(companyDTO);
        System.out.println("CompanyUCCImpl " + companyBiz.isBlackListed(companyDTO));
        System.out.println("CompanyUCCImpl " + companyDTO.isBlackListed());
        dalServices.commit();

        return companyDTO;
      }
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }


  /**
   * Retrieves a company by its ID.
   *
   * @param idCompany The ID of the company.
   * @return The company with the given ID.
   */
  @Override
  public CompanyDTO getCompanyById(int idCompany) {
    dalServices.start();
    try {
      CompanyDTO companyDTO = companyDAO.getCompanyById(idCompany);
      dalServices.commit();
      return companyDTO;
    } catch (Exception e) {
      dalServices.rollBack();
      System.out.println("il veut pas du throw e " + e.getMessage());
    }
    return null;
  }


  /**
   * Retrieves a list of all companies.
   *
   * @return A list of CompanyDTO objects representing all the companies.
   */
  @Override
  public List<CompanyDTO> getAllEnterprises() {
    dalServices.start();
    try {
      List<CompanyDTO> companiesList = companyDAO.getAllEnterprises();

      dalServices.commit();
      return companiesList;

    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }


}
