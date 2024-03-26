import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import companyRefusedInternship from '../Pages/companyRefusedInternship';
import ProfilePage from "../Pages/ProfilePage";
import ContactsPage from "../Pages/ContactsPage";
import meetCompanyPage from "../Pages/meetCompanyPage";

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/viewUsers': ViewUsers,
  '/logout': Logout,
  '/meetCompany': meetCompanyPage,
  '/companyRefused': companyRefusedInternship,
  '/users': ProfilePage,
  '/contacts': ContactsPage
};

export default routes;
