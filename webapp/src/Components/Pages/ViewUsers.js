import { clearPage } from "../../utils/render";
import { getAuthenticatedUser } from "../../utils/auths";
import Navigate from "../Router/Navigate";
import { getToken } from "../../utils/user";

let students = []; // Variable globale pour stocker les étudiants récupérés

const viewAllUsersPage = async () => {
  clearPage();
  await allUsers(); // Affiche tous les utilisateurs
  await createSchoolYearDropdown(); // Crée le menu déroulant pour les années académiques
}

async function allUsers() {
  try {
    const options = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        authorization: getAuthenticatedUser().token
      },
    };

    const response = await fetch("http://localhost:3000/users/getAllUsers", options);

    if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`)

    const users = await response.json();
    renderUsers(users);
  } catch (error) {
    console.log("Erreur");
    alert(
        'Vous ne possédez pas les droits pour accéder à cette ressource. Seulement les professeurs ou administratifs peuvent y accéder');
    console.error('Une erreur est survenue : ', error);
  }
}

function renderUsers(users) {
  const main = document.querySelector('main');
  const usersList = document.createElement('div');
  usersList.id = 'usersList';
  const userRows = users.map(user => `
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
  attachProfileButtonListeners();

  students = users;

  const filterButton = document.createElement('button');
  filterButton.className = 'btn btn-outline-light';
  filterButton.id = 'filterButton';
  filterButton.textContent = 'Afficher uniquement les étudiants';
  filterButton.addEventListener('click', async () => {
    const studentUsers = students.filter(user => user.role === "Etudiant");
    renderUsers(studentUsers);
  });

  main.insertBefore(filterButton, main.firstChild); // Insère le bouton avant le tableau des utilisateurs
}

function attachProfileButtonListeners() {
  document.querySelectorAll('.profileButton').forEach(button => {
    const userId = button.getAttribute('data-user-id');
    button.addEventListener('click', () => Navigate(`/studentProfile?userId=${userId}`));
  });
}

async function fetchStudentsBySchoolYear(schoolYear) {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = `http://localhost:3000/users/getStudentsByAcademicYear/${schoolYear}`;
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}

async function createSchoolYearDropdown() {
  const main = document.querySelector('main');
  const usersList = document.createElement('div');
  usersList.id = 'usersList';

  const dropdown = document.createElement('select');
  dropdown.id = 'schoolYear-dropdown';

  let schoolYears;

  try {
    schoolYears = await fetchGetAllSchoolYears();
  } catch (error) {
    console.error('Error fetching school years:', error);
    return;
  }

  if (!schoolYears || schoolYears.length === 0) {
    console.error('No school year found');
    return;
  }

  // Ajouter une option par défaut "Choisir une année"
  const defaultOption = document.createElement('option');
  defaultOption.value = ''; // Valeur vide pour l'option par défaut
  defaultOption.text = 'Choisir une année';
  dropdown.appendChild(defaultOption);

  // Ajouter chaque année académique comme une option dans le menu déroulant
  schoolYears.forEach(schoolYear => {
    const option = document.createElement('option');
    option.value = schoolYear;
    option.text = schoolYear;
    dropdown.appendChild(option);
  });


  dropdown.value = ''; // Définir l'option par défaut comme sélectionnée

  // Ajouter l'événement de changement
  dropdown.addEventListener('change', async (event) => {
    const selectedYear = event.target.value; // Récupérer l'année sélectionnée dans le menu déroulant
    try {
      // Appel à fetchStudentsBySchoolYear avec l'année sélectionnée
      const studentsByYear = await fetchStudentsBySchoolYear(selectedYear);

      // Mettre à jour la variable globale des étudiants avec les nouvelles données
      students = studentsByYear;

      // Appeler renderUsers avec les nouveaux étudiants récupérés
      renderUsers(studentsByYear);
    } catch (error) {
      console.error('Error fetching students by school year:', error);
    }
  });

  usersList.appendChild(dropdown);
  main.insertBefore(usersList, main.firstChild); // Insère le menu déroulant avant le tableau des utilisateurs
}

async function fetchGetAllSchoolYears() {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = "http://localhost:3000/internship/schoolYears";
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}

export default viewAllUsersPage;
