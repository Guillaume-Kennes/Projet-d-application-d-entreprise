package be.vinci.pae.utils;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.business.ucc.InternshipUCC;
import be.vinci.pae.business.ucc.InternshipUCCImpl;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.business.ucc.UserUCCImpl;
import be.vinci.pae.business.ucc.ViewContactUCC;
import be.vinci.pae.business.ucc.ViewContactUCCImpl;
import be.vinci.pae.dal.DALBackServices;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.DALServicesImpl;
import be.vinci.pae.dal.InternshipDAO;
import be.vinci.pae.dal.InternshipDAOImpl;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.dal.UserDAOImpl;
import be.vinci.pae.dal.ViewContactDAO;
import be.vinci.pae.dal.ViewContactDAOImpl;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

/**
 * This class represents an implementation of an application binder.
 * It extends the base class `AbstractBinder`.
 */
@Provider
public class AppBinderTest extends AbstractBinder {

  /**
   * Configures the bindings for this application binder.
   * In this method, various dependencies are bound to their corresponding implementations.
   */
  @Override
  protected void configure() {
    bind(DomainFactoryImpl.class).to(DomainFactory.class);
    bind(UserUCCImpl.class).to(UserUCC.class);
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
    bind(DALServicesImpl.class).to(DALBackServices.class).to(DALServices.class);
    bind(InternshipUCCImpl.class).to(InternshipUCC.class);
    bind(Mockito.mock(InternshipDAOImpl.class)).to(InternshipDAO.class);
    bind(ViewContactUCCImpl.class).to(ViewContactUCC.class);
    bind(Mockito.mock(ViewContactDAOImpl.class)).to(ViewContactDAO.class);
  }
}
