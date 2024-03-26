package be.vinci.pae.utils;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.business.ucc.ContactUCCImpl;
import be.vinci.pae.business.ucc.InternshipUCC;
import be.vinci.pae.business.ucc.InternshipUCCImpl;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.business.ucc.UserUCCImpl;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.dal.ContactDAOImpl;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.DALServicesImpl;
import be.vinci.pae.dal.InternshipDAO;
import be.vinci.pae.dal.InternshipDAOImpl;
import be.vinci.pae.dal.InternshipSupervisorDAO;
import be.vinci.pae.dal.InternshipSupervisorDAOImpl;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.dal.UserDAOImpl;
import be.vinci.pae.dal.ViewCompanyDAO;
import be.vinci.pae.dal.ViewCompanyDAOImpl;
import be.vinci.pae.dal.ViewUEInscriptionDAO;
import be.vinci.pae.dal.ViewUEInscriptionDAOImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Provider class for binding application components using HK2. This class extends AbstractBinder to
 * configure dependency injections for various components.
 */
@Provider
public class ApplicationBinder extends AbstractBinder {

  /**
   * Configures the binding of components.
   */
  @Override
  protected void configure() {
    bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
    bind(UserDAOImpl.class).to(UserDAO.class).in(Singleton.class);
    bind(DALServicesImpl.class).to(DALBackServices.class).to(DALServices.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).in(Singleton.class);
    bind(InternshipDAOImpl.class).to(InternshipDAO.class).in(Singleton.class);
    bind(InternshipUCCImpl.class).to(InternshipUCC.class).in(Singleton.class);
    bind(ViewCompanyDAOImpl.class).to(ViewCompanyDAO.class).in(Singleton.class);
    bind(ViewUEInscriptionDAOImpl.class).to(ViewUEInscriptionDAO.class).in(Singleton.class);
    bind(InternshipSupervisorDAOImpl.class).to(InternshipSupervisorDAO.class).in(Singleton.class);
  }
}
