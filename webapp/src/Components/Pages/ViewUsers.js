import {clearPage} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";
import Navigate from "../Router/Navigate";

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
      authorization: getAuthenticatedUser().token
    },
  };

  const response = await fetch("http://localhost:3000/users/getAllUsers",
      options);

  try {
    if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`)

    let users = await response.json();

    const renderUsers = (users1) => {
      const userRows = users1.map(user => `
          <tr>
            <td>${user.id}</td>
            <td>${user.email}</td>
            <td>${user.lastName}</td>
            <td>${user.firstName}</td>
            <td>${user.phoneNumber}</td>
            <td>${user.role}</td>
            ${user.role === "Etudiant" ? `<td><button class="btn btn-outline-dark profileButton" data-user-id="${user.id}">Voir profil</button></td>` : `<td></td>`}
          </tr>
        `);

      main.innerHTML = `
          <button class="btn btn-outline-light" id="filterButton">Afficher uniquement les étudiants</button>
          <table class="table table-bordered">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Email</th>
                <th scope="col">Nom</th>
                <th scope="col">Prénom</th>
                <th scope="col">Numéro de téléphone</th>
                <th scope="col">Rôle</th>
                <th scope="col">Profils</th>
              </tr>
            </thead>
            <tbody>
              ${userRows.join('')}
            </tbody>
          </table>
        `;
    };

    renderUsers(users);

    const filterButton = document.getElementById('filterButton');
    filterButton.addEventListener('click', async () => {
      const studentUsers = users.filter(user => user.role === "Etudiant");
      renderUsers(studentUsers);
      users = studentUsers;
      attachProfileButtonListeners();
    });

    attachProfileButtonListeners();

  } catch (error) {
    console.log("Erreur");
    alert(
        'Vous ne possédez pas les droits pour accéder à cette ressource. Seulement les professeurs ou administratifs peuvent y accéder');
    console.error('Une erreur est survenue : ', error);
  }
}

function attachProfileButtonListeners() {
  document.querySelectorAll('.profileButton').forEach(button => {
    const userId = button.getAttribute('data-user-id');
    button.addEventListener('click', () => Navigate(`/studentProfile?userId=${userId}`));
  });
}

export default viewAllUsersPage;