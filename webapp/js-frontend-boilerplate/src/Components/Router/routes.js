import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import ViewUsers from "../Pages/ViewUsers";

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/viewUsers': ViewUsers,
  '/logout': Logout,
};

export default routes;
