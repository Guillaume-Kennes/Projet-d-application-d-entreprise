/* eslint-disable no-console */

function getToken(){
  const token = localStorage.getItem('token');
  if(!token){
    return null;
  }
  console.log(token);
  return token;
}

function getUserIdFromToken() {
  const token = getToken();

  if (!token) {
    return null;
  }

  try {
    // Split the token into three parts: header, payload, signature
    const tokenParts = token.split('.');

    // Decode the payload (claims), which is the second part of the token
    const decodedPayload = atob(tokenParts[1]);

    // Parse the decoded payload as JSON
    const payload = JSON.parse(decodedPayload);

    // Extract the 'id' claim from the payload
    const userId = payload.user;

    return userId;
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }
}

export { getToken, getUserIdFromToken };
