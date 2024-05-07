/* eslint-disable import/no-cycle */

const STORE_NAME = 'user';
const REMEMBER_ME = 'remembered';

let currentUser;

const getAuthenticatedUser = () => {
  if (currentUser !== undefined) {
    return currentUser;
  }

  const remembered = getRememberMe();
  const serializedUser = remembered
      ? localStorage.getItem(STORE_NAME)
      : sessionStorage.getItem(STORE_NAME);

  if (!serializedUser) {
    return undefined;
  }

  currentUser = JSON.parse(serializedUser);
  return currentUser;
};

const setAuthenticatedUser = (authenticatedUser) => {
  const serializedUser = JSON.stringify(authenticatedUser);
  const remembered = getRememberMe();
  if (remembered) {
    localStorage.setItem(STORE_NAME, serializedUser);
  } else {
    sessionStorage.setItem(STORE_NAME, serializedUser);
  }

  currentUser = authenticatedUser;
};

const isAuthenticated = () => currentUser !== undefined;

const clearAuthenticatedUser = () => {
  localStorage.removeItem(STORE_NAME);
  sessionStorage.removeItem(STORE_NAME);
  localStorage.removeItem(REMEMBER_ME);
  currentUser = undefined;
};

function getRememberMe() {
  const rememberedSerialized = localStorage.getItem(REMEMBER_ME);
  return JSON.parse(rememberedSerialized);
}

function setRememberMe(remembered) {
  const rememberedSerialized = JSON.stringify(remembered);
  localStorage.setItem(REMEMBER_ME, rememberedSerialized);
}

const refreshAuthenticatedUser = async () => {

  const API = await import('./api');

  const authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    return;
  }

  const token = localStorage.getItem('token'); // Modifier la récupération du token
  try {
    const updatedUser = await API.get('auths/user', {  // todo
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      }
    });

    setAuthenticatedUser({...authenticatedUser, ...updatedUser});
  } catch (error) {
    if (!(error instanceof TypeError)) {
      clearAuthenticatedUser();
    }
  }
};


const isProfessor = () => {
  const authenticatedUser = getAuthenticatedUser();
  const prof = authenticatedUser?.user?.role;
  return prof === 'Professeur';
};

const isAdmin = () => {
  const authenticatedUser = getAuthenticatedUser();
  const admin = authenticatedUser?.user?.role;
  return admin === 'Administratif';
};

export {
  getAuthenticatedUser,
  setAuthenticatedUser,
  isAuthenticated,
  clearAuthenticatedUser,
  getRememberMe,
  setRememberMe,
  refreshAuthenticatedUser,
  isProfessor,
  isAdmin,
};
