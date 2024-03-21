package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.domain.ViewCompany;
import be.vinci.pae.business.domain.ViewCompanyDTO;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewCompanyDAOImpl implements ViewCompanyDAO {

  @Inject
  private DALBackServices dalServices;
  @Inject
  private DomainFactory myDomainFactory;

  @Override
  public int insert(ViewCompanyDTO companyDTO) {
    int generatedId = 0;
    try {
      String query = """
              INSERT INTO pae.enterprises (
              trade_name,
              designation,
              adress,
              city,
              means_of_commlunication,
              is_black_listed,
              motivation_blacklist)
          VALUES (?, ?, ?, ?, ?, false, null)
            RETURNING id_enterprise;
            """;

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {

        ps.setString(1, companyDTO.getTradeName());
        ps.setString(2, companyDTO.getDesignation());
        ps.setString(3, companyDTO.getAdress());
        ps.setString(4, companyDTO.getAdress()); // city
        ps.setString(5, companyDTO.getMeansOfCommunication());

      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    System.out.println("generatedId = " + generatedId);
    return generatedId;
  }

  /**
   * Method to retrieve company information from a ResultSet and map it to a ViewCompanyDTO object.
   *
   * @param resultSet The ResultSet containing company information.
   *
   * @return A ViewCompanyDTO object populated with company information from the ResultSet.
   */
  public ViewCompanyDTO companyInfos(ResultSet resultSet) {
    ViewCompanyDTO companyDTO = myDomainFactory.getCompany();

    try {
      companyDTO.setId(resultSet.getInt("id_enterprise"));
      companyDTO.setTradeName(resultSet.getString("trade_name"));
      companyDTO.setDesignation(resultSet.getString("designation"));
      companyDTO.setAdress(resultSet.getString("adress"));
      companyDTO.setCity(resultSet.getString("city"));
      companyDTO.setMeansOfCommunication(resultSet.getString("means_of_communication"));
    } catch (SQLException e) { //DEMANDER AU PROF quelle exception
      e.getMessage();
    }
    return companyDTO;
  }

  /**
   * Method to retrieve a company by their ID.
   *
   * @param id The ID of the company to retrieve.
   *
   * @return A ViewCompanyDTO object representing the company with the specified ID, or null if not found.
   *
   * @throws IllegalArgumentException if the company is not found in the database.
   */
  public ViewCompanyDTO getCompanyById(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.entreprises e WHERE e.id_enterprise = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    ViewCompanyDTO company = myDomainFactory.getCompany();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        company = companyInfos(resultSet);
      } else {
        company = null;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    } finally {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return company;
  }

}
