package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ViewUEInscriptionDTO;
import java.sql.ResultSet;

/**
 * Represents a Data Access Object (DAO) for managing UE inscription-related data.
 * Provides methods to retrieve UE Inscription information based on ID.
 */
public interface ViewUEInscriptionDAO {

  /**
   * Returns the information of a UE Inscription.
   *
   * @param resultSet the given resultSet
   *
   * @return the UE Inscription corresponding to that result set
   */
  ViewUEInscriptionDTO ueInscriptionInfos(ResultSet resultSet);

  /**
   * Returns the UE Inscription corresponding to the id.
   *
   * @param id the UE Inscription's id
   *
   * @return the UE Inscription corresponding to the id
   */
  ViewUEInscriptionDTO getUeInscriptionById(int id);
}
