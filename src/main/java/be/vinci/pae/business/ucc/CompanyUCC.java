package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.ContactDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Company Use Case Controller (UCC) with methods related to company operations.
 */
public interface CompanyUCC {

  /**
   * Add a new company to the database.
   *
   * @param companyDTO the company to be added
   *
   * @return the added company
   */
  CompanyDTO addCompany(CompanyDTO companyDTO);


  /**
   * Retrieves a list of all enterprises from the database.
   *
   * @return A list of CompanyDTO objects representing all enterprises.
   */
  List<CompanyDTO> getAllEnterprises();

  /**
   * Retrieves the number of students taken by a company.
   *
   * @param idCompany the id of the company
   *
   * @return the number of students taken by the company
   */
  int numberOfStudentsTaken(int idCompany);

  /**
   * Retrieves a list of all contacts made by a company.
   *
   * @param id The id of the company.
   * @return A list of ContactDTO objects.
   */
  ArrayList<ContactDTO> getAllContacts(int id) throws SQLException;
}
