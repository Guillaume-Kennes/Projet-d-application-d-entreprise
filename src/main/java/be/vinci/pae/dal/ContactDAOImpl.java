package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class implements the ContactDAO interface to provide methods for accessing. and manipulating
 * contact data in the database.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DALBackServices dalServices;

  /**
   * Inserts a new contact into the database.
   *
   * @param contactDTO The contact data to be inserted.
   * @return The contact data after insertion.
   * @throws FatalException if an unrecoverable error occurs during database operation.
   */
  @Override
  public ContactDTO insert(ContactDTO contactDTO) {
    try {
      String query = """
              INSERT INTO pae.contacts (
              state,
              enterprise,
              inscription_UE,
              reason_for_refusal,
              is_followed,
              meeting_place)
          VALUES ('initié',
          (SELECT e.id_enterprise
           FROM pae.enterprises e
           WHERE e.trade_name LIKE ?),
          (SELECT DISTINCT c.inscription_ue
           FROM pae.users u, pae.contacts c, pae.inscriptions_UE i
           WHERE u.id_user = i.student
           AND i.id_inscription_UE = c.inscription_ue
           AND u.id_user = ?),
          null, true, null)
          RETURNING *;
            """;

      System.out.println("Generated SQL query: " + query);

      // String tradeName = "N"; // Or any other search term
      // String wildcardTradeName = "%" + tradeName + "%";
      // changer le wildcard en id de l entreprise

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {

        System.out.println("2 Generated SQL query : " + query);

        ps.setString(1, "%" + contactDTO.getTradeName() + "%");
        ps.setInt(2, contactDTO.getUserId());

        System.out.println("ContactDAOImpl -------> Enterprise : "
            + contactDTO.getTradeName());
        System.out.println("ContactDAOImpl -------> UserId : "
            + contactDTO.getUserId());

        System.out.println("ContactDAOImpl ----> ps : " + ps);
        ps.execute();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }
    System.out.println("ContactDAOImpl --> contactDTO : " + contactDTO);
    return contactDTO;
  }

}
