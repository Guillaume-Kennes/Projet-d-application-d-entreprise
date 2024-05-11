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
 * Implementation of the CompanyUCC interface. Provides methods related to company operations.
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
  public CompanyDTO addCompany(CompanyDTO companyDTO) {
    dalServices.start();
    try {
      CompanyDTO company = companyDAO.insert(companyDTO);
      dalServices.commit();
      return company;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Blacklists the specified company with the given reason.
   *
   * @param companyDTO The CompanyDTO object representing the company to be blacklisted.
   * @param reason     The reason for blacklisting the company.
   * @return A CompanyDTO object representing the blacklisted company.
   * @throws NotFoundException if the specified company is not found.
   * @throws BusinessException if the reason field is null or if the company is already
   *                           blacklisted.
   */
  public CompanyDTO blackList(CompanyDTO companyDTO, String reason) {
    dalServices.start();
    try {
      if (companyDTO == null) {
        dalServices.rollBack();
        throw new NotFoundException("Company not found");
      }

      if (reason == null) {
        dalServices.rollBack();
        throw new BusinessException("Reason field cannot be null");
      }

      Company companyBiz = (Company) companyDTO;

      if (companyBiz.isBlackListed(companyDTO)) {
        dalServices.rollBack();
        throw new BusinessException("Invalid company state");

      } else {
        companyDTO.setIsBlackListed(true);
        companyDTO.setMotivationBlackList(reason);

        companyDAO.update(companyDTO);
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

  /**
   * Retrieves a list of all companies for a given school year.
   *
   * @param schoolYear The school year for which to retrieve the companies.
   * @return A list of CompanyDTO objects representing all the companies for the given school year.
   */
  public List<CompanyDTO> getAllEnterprises(String schoolYear) {
    dalServices.start();
    try {
      List<CompanyDTO> companiesList = companyDAO.getAllEnterprises(schoolYear);

      dalServices.commit();
      return companiesList;

    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Retrieves the number of students taken by a company.
   *
   * @param idCompany The ID of the company.
   * @return The number of students taken by the company.
   */
  public int numberOfStudentsTaken(int idCompany) {
    dalServices.start();
    try {
      return companyDAO.numberOfStudentsTaken(idCompany);
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }
}
