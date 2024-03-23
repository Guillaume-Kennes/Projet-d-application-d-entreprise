import {getAuthenticatedUser, isAuthenticated} from "../../utils/auths";

const HomePage = () => {
  const main = document.querySelector('main');
  const authenticatedUser = getAuthenticatedUser();

  if(isAuthenticated()){
    main.innerHTML = `<p> Hello, ${authenticatedUser?.email}</p>`;
  } else {
    main.innerHTML = '';

  }
/*
  const anonymousUserNavbar = `<a>Connectez vous s'il vous plait</a>`;
  const authenticatedUserNavbar = `<a>${authenticatedUser?.email}</a>`;
*/
};

export default HomePage;
