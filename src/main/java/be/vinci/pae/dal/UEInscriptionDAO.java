package be.vinci.pae.dal;

import be.vinci.pae.business.domain.UEInscriptionDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) for managing UE inscription-related data. Provides methods
 * to retrieve UE Inscription information based on ID.
 */
public interface UEInscriptionDAO {

  /**
   * Returns the information of a UE Inscription.
   *
   * @param resultSet the given resultSet
   * @return the UE Inscription corresponding to that result set
   */
  UEInscriptionDTO ueInscriptionInfos(ResultSet resultSet);

  /**
   * Retrieves the inscription information for a UE (Unité d'enseignement) identified by its ID.
   *
   * @param id The ID of the UE inscription to retrieve information for.
   * @return A UEInscriptionDTO object representing the inscription information for the UE.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  UEInscriptionDTO getUeInscriptionById(int id) throws SQLException;
}
