package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ViewCompanyDTO;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ViewCompanyDAOImpl implements ViewCompanyDAO {

  @Inject
  private DALBackServices dalServices;

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

}
