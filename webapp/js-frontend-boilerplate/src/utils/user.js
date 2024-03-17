const jwt = require('jsonwebtoken');

function getToken(){
  const token = localStorage.getItem('token');
  if(!token){
    return null;
  }
  return token;
}

function getUserInfoFromToken() {
  const token = getToken();

  // Decode the token
  try {
    const decodedToken = jwt.decode(token);

    const userInfo = {
      id: decodedToken.id,
      email: decodedToken.email,
      lastName: decodedToken.lastName,
      firstName: decodedToken.firstName,
      phoneNumber: decodedToken.phoneNumber,
      registrationDate: decodedToken.registrationDate,
      role: decodedToken.role
    };

    return userInfo;
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }
}

export { getToken, getUserInfoFromToken };