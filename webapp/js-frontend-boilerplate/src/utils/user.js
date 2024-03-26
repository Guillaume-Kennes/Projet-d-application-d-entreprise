/* eslint-disable no-console */
function getToken(){
  const token = localStorage.getItem('user');
  if(!token){
    return null;
  }
  console.log(token);
  return token;
}

function getUserInfoFromToken() {
  const tokenString = getToken();

  if (!tokenString) {
    return null;
  }

  let token;
  try {
    token = JSON.parse(tokenString);
  } catch (error) {
    console.error('Error parsing token:', error);
    return null;
  }

  const userInfo = {
    token: token.token || null,
    id: token.id || null,
    email: token.email || null,
    lastName: token.lastName || null,
    firstName: token.firstName || null,
    phoneNumber: token.phoneNumber || null,
    registrationDate: token.registrationDate || null,
    role: token.role || null
  };

  return userInfo;
}

export { getToken, getUserInfoFromToken };
