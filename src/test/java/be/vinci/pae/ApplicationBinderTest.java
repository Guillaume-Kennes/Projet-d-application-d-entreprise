package be.vinci.pae;

import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.business.ucc.UserUCCImpl;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.DALServicesImpl;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.dal.UserDAOImpl;
import be.vinci.pae.utils.ApplicationBinder;
import jakarta.inject.Singleton;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector.Factory;
import org.mockito.Mockito;

public class ApplicationBinderTest extends ApplicationBinder {

  protected void configure() {
    bind(DomainFactoryImpl.class).to(Factory.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
    bind(Mockito.mock(DALServicesImpl.class)).to(DALServices.class);
  }

}
