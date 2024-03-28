import { removePathPrefix, usePathPrefix } from '../../utils/path-prefix';
import routes from './routes';
import {clearAuthenticatedUser, setAuthenticatedUser} from "../../utils/auths";

const Router = () => {
  onFrontendLoad();
  onNavBarClick();
  onHistoryChange();
};

function onNavBarClick() {
  const navbarWrapper = document.querySelector('#navbarWrapper');

  navbarWrapper.addEventListener('click', (e) => {
    e.preventDefault();
    const navBarItemClicked = e.target;
    const uri = navBarItemClicked?.dataset?.uri;
   if(!uri){
     return;
   }
   if(uri === '/logout'){
     logout();
   } else {
     const componentToRender = routes[uri];
     if( !componentToRender)
       throw Error(`The ${uri} ressource does not exist.`);

     componentToRender();
     window.history.pushState({}, '', usePathPrefix(uri));
   }
  });
}

function onHistoryChange() {
  window.addEventListener('popstate', () => {
    const uri = removePathPrefix(window.location.pathname);
    const componentToRender = routes[uri];
    componentToRender();
  });
}

function onFrontendLoad() {
  window.addEventListener('load', async () => {
    const uri = removePathPrefix(window.location.pathname);
    const componentToRender = routes[uri];
    if (!componentToRender) throw Error(`The ${uri} ressource does not exist.`);
    componentToRender();

    // refresh

    const token = localStorage.getItem('token');
    console.log("TOKEN " , token);

    if (token) {
      try {
        const response = await fetch('/auths/refresh', {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
          }
        });

        if(response.ok) {
          const text = await response.text();
          try{
            const user = JSON.parse(text);
            setAuthenticatedUser(user);
          } catch (e) {
            console.log('Error while parsing JSON:', e);
          }
        } else {
          clearAuthenticatedUser();
        }
      } catch (e) {
        console.log('Error while fetching user information', e);
      }
    }
  });
}

function logout(){
  clearAuthenticatedUser();
  localStorage.removeItem('token');
  window.location.href = '/';
}


export default Router;
