import {clearPage} from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import {getAuthenticatedUser} from "../../utils/auths";

const ProfilePage = async () => {
  clearPage();
  Navbar();
  try {
    const user = await getValues();
    renderProfilePage(user);
  } catch (error) {
    console.error('Error fetching user data:', error);
  }
};

function renderProfilePage(user) {
  console.log(user);
  const main = document.querySelector('main');

  main.innerHTML =
      `<div class="container">
        <div class="row">
          <div class="col-md-5 bg-secondary rounded mt-3 ms-3">
            <div class="d-flex justify-content-between align-items-center">
              <h3 class="text-right mt-3">Mon profil</h3>
              <button class="btn btn-primary me-3">Modifier le mot de passe</button>
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
              <button class="btn btn-primary me-3">Modifier ou ajouter le sujet de stage</button>
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

async function getValues() {
  const authenticatedUser = getAuthenticatedUser();
  const id = authenticatedUser?.user?.id;
  console.log("PROFIL --> authenticatedUserId : ", id);

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
    },
  };
  let user;
  const response = await fetch(`http://localhost:3000/users/${id}`, options);
  if (!response.ok) {
    throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
  }else{
    user = await response.json();
    return user;
  }
}
export default ProfilePage;