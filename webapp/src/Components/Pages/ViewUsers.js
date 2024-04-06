import { clearPage } from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";

const viewAllUsersPage = async () => {
  clearPage();
  await allUsers();
}

async function allUsers() {
  const main = document.querySelector('main');

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      authorization: getAuthenticatedUser().user
    },
  };

  const response = await fetch("http://localhost:3000/users/getAllUsers", options);

  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`)

  const users = await response.json();

  const userElements = users.map(user => `
      <div>
        <h2>${user.id}</h2>
        <p>${user.email}</p>
        <p>${user.lastName}</p>
        <p>${user.firstName}</p>
        <p>${user.phoneNumber}</p>
        <p>${user.role}</p>
      </div>
    `);

  main.innerHTML = userElements.join('');

}

export default viewAllUsersPage;