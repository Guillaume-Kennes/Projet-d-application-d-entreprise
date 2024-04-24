import {clearPage} from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import {getAuthenticatedUser} from "../../utils/auths";
import {getToken} from "../../utils/user";

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
              <button class="btn btn-primary me-3" id="button_pw">Modifier le mot de passe</button>
            </div>
            <p class="fw-bold mb-n1">Nom</p>
            <p id="lastName">${user.lastName}</p>
            <p class="fw-bold mb-n1">Prénom</p>
            <p id="firstName">${user.firstName}</p>
            <p class="fw-bold mb-n1">Email</p>
            <p id="email">${user.email}</p>
            <p class="fw-bold mb-n1">Téléphone
              <br><button class="btn btn-primary me-3" id="button_phone">Modifier</button>
            </p>
            <p id="phoneNumber">${user.phoneNumber}</p>
          </div>
          <div class="col-md-5 bg-secondary rounded mt-3 ms-3">
            <div class="d-flex justify-content-between align-items-center">
              <h3 class="text-right mt-3">Données de stage</h3>
              <button class="btn btn-primary me-3" id="button_subject">Modifier ou ajouter le sujet de stage</button>
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

  const passwordButton = document.getElementById('button_pw');
  passwordButton.addEventListener("click", () => showFormPassword());

  const phoneNumberButton = document.getElementById('button_phone');
  phoneNumberButton.addEventListener("click", () => showFormPhone());

  const modifyButton = document.getElementById('button_subject');
  modifyButton.addEventListener("click", () => {
    window.location.href = "/modifySubject";
  });
}

async function getValues() {
  const authenticatedUser = getAuthenticatedUser();
  const id = authenticatedUser?.user?.id;
  console.log(id);
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: authenticatedUser.token,
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

function showFormPassword() {
  const main = document.querySelector('main');
  main.innerHTML = `
    <div class="container mt-5">
      <h1>Formulaire de modification du mot de passe</h1>
      <form id="password-form">
        <h3> Ancien mot de passe </h3>
        <div class="form-group">
          <input type="text" class="form-control" id="ex_password" name="ex_password" placeholder="Entrez votre mot de passe actuel" required>
        </div>
        <h3> Nouveau mot de passe </h3>
        <div class="form-group">
          <input type="text" class="form-control" id="password" name="password" placeholder="Entrez le nouveau mot de passe" required>
        </div>
        <h3> Confirmation du nouveau mot de passe </h3>
        <div class="form-group">
          <input type="text" class="form-control" id="password2" name="password2" placeholder="Confirmez le nouveau mot de passe" required>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Confirmer la modification</button>
      </form>
    </div>
  `;

  const form = document.getElementById('password-form');
  form.addEventListener('submit', (e) => changePassword(e));
}

function showFormPhone() {
  const main = document.querySelector('main');
  main.innerHTML = `
    <div class="container mt-5">
      <h1>Modification du numéro de téléphone</h1>
      <form id="phone-form">
        <h3> Nouveau numéro de téléphone </h3>
        <div class="form-group">
          <input type="text" class="form-control" id="phone" name="phone" placeholder="Entrez le nouveau numéro" required>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Confirmer la modification</button>
      </form>
    </div>
  `;

  const form = document.getElementById('phone-form');
  form.addEventListener('submit', (e) => changePhoneNumber(e));
}

async function changePassword(e) {
  e.preventDefault();

  const password = document.querySelector('input[name="password"]').value;
  const password2 = document.querySelector('input[name="password2"]').value;
  if(password2 !== password) {
    throw new Error('Error confirming the new password');
  }

  const token = getToken();
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify({ password }),
  };

  const authenticatedUser = getAuthenticatedUser();
  const id = authenticatedUser?.user?.id;
  try {
    const response = await fetch(`http://localhost:3000/users/editPassword/${id}`, options);
    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
  } catch (error) {
    console.error('Error setting new password :', error);
  }
}

async function changePhoneNumber(e) {
  e.preventDefault();
  const phone = document.querySelector('input[name="phone"]').value;
  const token = getToken();
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify({ phone }),
  };

  const authenticatedUser = getAuthenticatedUser();
  const id = authenticatedUser?.user?.id;
  try {
    const response = await fetch(`http://localhost:3000/users/editPhoneNumber/${id}`, options);
    console.log(phone);
    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
  } catch (error) {
    console.error('Error setting new phone number :', error);
  }
}
export default ProfilePage;