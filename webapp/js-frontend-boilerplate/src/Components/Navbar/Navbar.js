// eslint-disable-next-line no-unused-vars
// import { Navbar as BootstrapNavbar } from 'bootstrap'; unused import (for the moment)
import { getAuthenticatedUser, isAuthenticated } from '../../utils/auths';

const SITE_NAME = 'DevObs';

const Navbar = () => {
  renderNavbar();
};

function renderNavbar() {
  const authenticatedUser = getAuthenticatedUser();
  const authenticatedUserEmail = authenticatedUser?.user?.email;
  const authenticatedUserId = authenticatedUser?.user?.id;
  console.log("Navbar --> authenticatedUserEmail : ", authenticatedUserEmail);
  console.log("Navbar --> authenticatedUserId : ", authenticatedUserId);

  const anonymousUserNavbar = `
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container-fluid">
        <a class="navbar-brand" href="#" data-uri="/">${SITE_NAME}</a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0" style="position: absolute; right: 0;">      
            <li id="loginItem" class="nav-item">
              <a class="nav-link" href="#" data-uri="/login" style="color: white;">Se connecter</a>
            </li>  
            <li id="registerItem" class="nav-item">
              <a class="nav-link" href="#" data-uri="/register" style="color: white;">S'inscrire</a>
            </li>          
          </ul>
        </div>
      </div>
    </nav>
`;

  const authenticatedUserNavbar = `
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">${SITE_NAME}</a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0 " style="position: absolute; right: 0;">
            <li class="nav-item">
              <a class="nav-link" href="#" data-uri="/viewUsers" style="color: white;">Rechercher tous les utilisateurs</a>
            </li>
            <li id="contactCompanyItem" class="nav-item">
              <a class="nav-link" href="#" data-uri="/contact" style="color: white;">Contacter une entreprise</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" data-uri="/meetCompany" style="color: white;">Indiquer une rencontre avec l'entreprise</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" data-uri="/users" style="color: white;">Profil</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" data-uri="/contacts" style="color: white;">Contacts</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" data-uri="/logout" style="color: white;">Se déconnecter</a>
            </li>
          </ul>
        </div> 
      </div>
    </nav>
`;

  const navbar = document.querySelector('#navbarWrapper');

  navbar.innerHTML = isAuthenticated() ? authenticatedUserNavbar : anonymousUserNavbar;
}

export default Navbar;
