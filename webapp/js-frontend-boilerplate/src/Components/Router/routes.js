import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import meetCompany from '../Pages/meetCompanyPage';
import companyRefusedInternship from '../Pages/companyRefusedInternship';

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/logout': Logout,
  '/meetCompany': meetCompany,
  '/companyRefused': companyRefusedInternship,
};

export default routes;
