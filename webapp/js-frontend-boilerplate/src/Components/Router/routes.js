import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import meetCompany from '../Pages/meetCompanyPage';
import companyRefusedInternship from '../Pages/companyRefusedInternship';
import ProfilePage from "../Pages/ProfilePage";
import ContactsPage from "../Pages/ContactsPage";

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/logout': Logout,
  '/meetCompany': meetCompany,
  '/companyRefused': companyRefusedInternship,
  '/users': ProfilePage,
  '/contacts': ContactsPage
};

export default routes;
