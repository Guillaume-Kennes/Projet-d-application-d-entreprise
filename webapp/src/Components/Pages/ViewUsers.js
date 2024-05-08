import {clearPage} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";
import {getToken} from "../../utils/user";

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

    const users = await response.json();

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

    document.querySelectorAll('.profileButton').forEach(button => {
      const userId = button.getAttribute('data-user-id');
      button.addEventListener('click', (e) => showStudentData(e, parseInt(userId, 10)));
    });

    const filterButton = document.getElementById('filterButton');
    filterButton.addEventListener('click', async () => {
      const studentUsers = users.filter(user => user.role === "Etudiant");
      renderUsers(studentUsers);
    });

  } catch (error) {
    console.log("Erreur");
    alert(
        'Vous ne possédez pas les droits pour accéder à cette ressource. Seulement les professeurs ou administratifs peuvent y accéder');
    console.error('Une erreur est survenue : ', error);
  }
}

async function showStudentData(e, id) {
  e.preventDefault();
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  };
  let user;
  console.log(id);
  const response = await fetch(`http://localhost:3000/users/${id}`, options);
  if (!response.ok) {
    throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
  }else{
    user = await response.json();
    showProfile(user);
  }
}

function showProfile(user) {
  const main = document.querySelector('main');

  main.innerHTML =
      `<div class="container">
        <div class="row">
          <div class="col-md-5 bg-secondary rounded mt-3 ms-3">
            <div class="d-flex justify-content-between align-items-center">
              <h3 class="text-right mt-3">Mon profil</h3>
            </div>
            <p class="fw-bold mb-n1">Nom</p>
            <p id="lastName">${user.lastName}</p>
            <p class="fw-bold mb-n1">Prénom</p>
            <p id="firstName">${user.firstName}</p>
            <p class="fw-bold mb-n1">Email</p>
            <p id="email">${user.email}</p>
            <p class="fw-bold mb-n1">Téléphone</p>
            <p id="phoneNumber">${user.phoneNumber}</p>
          </div>
          <div class="col-md-5 bg-secondary rounded mt-3 ms-3">
            <div class="d-flex justify-content-between align-items-center">
              <h3 class="text-right mt-3">Données de stage</h3>
            </div>
            <p class="fw-bold mb-n1">Titre</p>
            <p id="internshipTitle">${user.internshipTitle ? user.internshipTitle : 'aucun'}</p>
            <p class="fw-bold mb-n1">Entreprise</p>
            <p id="internshipCompany">${user.internshipCompany ? user.internshipCompany : 'aucun'}</p>
            <p class="fw-bold mb-n1">Responsable</p>
            <p id="internshipSupervisor">${user.internshipSupervisor ? user.internshipSupervisor : 'aucun'}</p>
            <p class="fw-bold mb-n1">Sujet</p>
            <p id="internshipSubject">${user.internshipSubject ? user.internshipSubject : 'aucun'}</p>
          </div>
          <div class="fw-bold mb-n1">Contacts pris</div>
            <ul id="contactCompanies"></ul>
        </div>
      </div>`;

  const contactCompaniesList = document.getElementById("contactCompanies");
  if (user.contactCompanies && user.contactCompanies.length > 0) {
    user.contactCompanies.forEach(company => {
      const listItem = document.createElement("li");
      listItem.textContent = company;
      contactCompaniesList.appendChild(listItem);
    });
  } else {
    contactCompaniesList.innerHTML = "<li>Aucun contact pris</li>";
  }
}

export default viewAllUsersPage;