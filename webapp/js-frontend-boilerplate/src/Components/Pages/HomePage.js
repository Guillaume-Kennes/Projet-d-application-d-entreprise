import {getAuthenticatedUser, isAuthenticated} from "../../utils/auths";

const HomePage = () => {
  const main = document.querySelector('main');
  const authenticatedUser = getAuthenticatedUser();
  const anonymousUserNavbar = `<a>Connectez vous s'il vous plait</a>`;
  const authenticatedUserNavbar = `<a>${authenticatedUser?.user?.email}</a>`;
  main.innerHTML = isAuthenticated() ? authenticatedUserNavbar : anonymousUserNavbar;

};

export default HomePage;
