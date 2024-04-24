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


  /**
   * Retrieves the number of students taken by a company.
   *
   * @param idCompany The ID of the company.
   * @return The number of students taken by the company.
   */
  @Override
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
