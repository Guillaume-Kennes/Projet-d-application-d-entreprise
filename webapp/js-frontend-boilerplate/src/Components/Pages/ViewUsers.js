import { clearPage } from "../../utils/render";

const viewAllUsersPage = async () => {
  clearPage();
  await allUsers();
}

async function allUsers() {
  const main = document.querySelector('main');

  const response = await fetch("http://localhost:3000/users/getAllUsers");

  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`)

  const users = await response.json();

  const userElements = users.map(user => `
      <div>
        <h2>${user.id}</h2>
        <p>${user.email}</p>
        <p>${user.lastName}</p>
        <p>${user.firstName}</p>
        <p>${user.phoneNumber}</p>
        <p>${user.registrationDate}</p>
        <p>${user.role}</p>
      </div>
    `);

  main.innerHTML = userElements.join('');

}

export default viewAllUsersPage;