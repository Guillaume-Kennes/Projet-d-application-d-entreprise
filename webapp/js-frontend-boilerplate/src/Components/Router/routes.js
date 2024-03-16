import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import ContactCompanyPage from "../Pages/ContactCompanyPage";

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/logout': Logout,
  '/contact': ContactCompanyPage
};

export default routes;
