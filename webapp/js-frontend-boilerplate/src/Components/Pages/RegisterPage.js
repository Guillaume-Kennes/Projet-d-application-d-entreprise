/* eslint-disable no-console */
import {
  getRememberMe,
  setAuthenticatedUser,
  setRememberMe
} from '../../utils/auths';
import {clearPage, renderPageTitle} from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const RegisterPage = () => {
  clearPage();
  renderPageTitle('Register');
  renderRegisterForm();
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  const lastname = document.createElement('input');
  lastname.type = 'text';
  lastname.id = 'lastname';
  lastname.placeholder = 'Nom';
  lastname.required = true;
  lastname.className = 'form-control mb-3';
  const firstname = document.createElement('input');
  firstname.type = 'text';
  firstname.id = 'firstname';
  firstname.placeholder = 'Prénom';
  firstname.required = true;
  firstname.className = 'form-control mb-3';
  const email = document.createElement('input');
  email.type = 'text';
  email.id = 'email';
  email.placeholder = 'Email';
  email.required = true;
  email.className = 'form-control mb-3';
  const password = document.createElement('input');
  password.type = 'password';
  password.id = 'password';
  password.required = true;
  password.placeholder = 'Mot de passe';
  password.className = 'form-control mb-3';
  const confirmationPassword = document.createElement('input');
  confirmationPassword.type = 'password';
  confirmationPassword.id = 'CofirmationPassword';
  confirmationPassword.required = true;
  confirmationPassword.placeholder = 'Confirmation de mot de passe';
  confirmationPassword.className = 'form-control mb-3';
  const phoneNumber = document.createElement('input');
  phoneNumber.type = 'text';
  phoneNumber.id = 'phoneNumber';
  phoneNumber.placeholder = 'Numéro de téléphone';
  phoneNumber.required = true;
  phoneNumber.className = 'form-control mb-3';
  const submit = document.createElement('input');
  submit.value = "S'inscrire";
  submit.type = 'submit';
  submit.className = 'btn btn-info';
  const formCheckWrapper = document.createElement('div');
  formCheckWrapper.className = 'mb-3 form-check';

  const rememberme = document.createElement('input');
  rememberme.type = 'checkbox';
  rememberme.className = 'form-check-input';
  rememberme.id = 'rememberme';
  rememberme.checked = getRememberMe();
  rememberme.addEventListener('click', onCheckboxClicked);

  const checkLabel = document.createElement('label');
  checkLabel.htmlFor = 'rememberme';
  checkLabel.className = 'form-check-label';
  checkLabel.textContent = 'Se souvenir de moi';

  formCheckWrapper.appendChild(rememberme);
  formCheckWrapper.appendChild(checkLabel);

  form.appendChild(lastname);
  form.appendChild(firstname);
  form.appendChild(email);
  form.appendChild(password);
  form.appendChild(confirmationPassword);
  form.appendChild(phoneNumber);
  form.appendChild(formCheckWrapper);
  form.appendChild(submit);
  main.appendChild(form);
  form.addEventListener('submit', onRegister);
}

function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}

async function onRegister(e) {
  e.preventDefault();

  const lastname = document.querySelector('#lastname').value;
  const firstname = document.querySelector('#firstname').value;
  const email = document.querySelector('#email').value;
  const password = document.querySelector('#password').value;
  const confirmationPassword = document.querySelector('#confirmationPassword').value;
  const phoneNumber = document.querySelector('#phoneNumber').value;



  const options = {
    method: 'POST',
    body: JSON.stringify({
      lastname,
      firstname,
      email,
      password,
      confirmationPassword,
      phoneNumber
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`http://localhost:3000/auths/register`, options);

  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);

  const authenticatedUser = await response.json();

  console.log('Newly registered & authenticated user : ', authenticatedUser);

  setAuthenticatedUser(authenticatedUser);

  Navbar();

  Navigate('/');
}

export default RegisterPage;
