import {getToken} from "../../utils/user";
import {clearPage} from "../../utils/render";

const studentProfile = async () => {
  clearPage();
  const idUser = getIdUserFromUrl();
  await showStudentData(idUser);
};
async function showStudentData(id) {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  };
  let user;

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
            <p class="fw-bold mb-n1">Sujet</p>
            <p id="internshipTitle">${user.internshipSubject ? user.internshipSubject : 'aucun'}</p>
            <p class="fw-bold mb-n1">Entreprise</p>
            <p id="internshipCompany">${user.internshipCompany ? user.internshipCompany : 'aucun'}</p>
            <p class="fw-bold mb-n1">Responsable</p>
            <p id="internshipSupervisor">${user.internshipSupervisor ? user.internshipSupervisor : 'aucun'}</p>
          </div>
          <div class="fw-bold mb-n1">Contacts</div>
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
    contactCompaniesList.innerHTML = "<li>Aucun contact en cours</li>";
  }
}

function getIdUserFromUrl() {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get('userId');
}

export default studentProfile;