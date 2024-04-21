import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import companyRefusedInternship from '../Pages/companyRefusedInternship';
import ProfilePage from "../Pages/ProfilePage";
import ContactsPage from "../Pages/ContactsPage";
import meetCompanyPage from "../Pages/meetCompanyPage";
import ViewUsers from "../Pages/ViewUsers";
import ViewCompanies from "../Pages/ViewCompanies";
import ContactCompanyPage from "../Pages/ContactCompanyPage";
import DashBoardPage from "../Pages/DashBoardPage";
import ContactsDetailsPage from "../Pages/ContactsDetailsPage";

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/viewUsers': ViewUsers,
  '/viewCompanies': ViewCompanies,
  '/logout': Logout,
  '/meetCompany': meetCompanyPage,
  '/companyRefused': companyRefusedInternship,
  '/users': ProfilePage,
  '/contacts': ContactsPage,
  '/contact': ContactCompanyPage,
  '/dashboard': DashBoardPage,
  '/contactsDetails': ContactsDetailsPage,
};

export default routes;
