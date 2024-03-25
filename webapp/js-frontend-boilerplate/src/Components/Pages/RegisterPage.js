/* eslint-disable no-console */
import {
  setAuthenticatedUser,
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

  function createInput(type, id, placeholder, required = true) {
    const input = document.createElement('input');
    input.type = type;
    input.id = id;
    input.placeholder = placeholder;
    input.required = required;
    input.className = 'form-control mb-3';
    const inputLabel = createLabel(placeholder);
    input.appendChild(inputLabel);
    return input;
  }

  function createLabel(text){
    const label = document.createElement('placeholder');
    label.className = 'form-placeholder';
    label.textContent = text;
    return label;
  }

  const inputAttributes = [
    { type: 'text', id: 'lastName', placeholder: 'Nom', required: true },
    { type: 'text', id: 'firstName', placeholder: 'Prénom', required: true },
    { type: 'text', id: 'email', placeholder: 'Email', required: true },
    { type: 'password', id: 'password', placeholder: 'Mot de passe', required: true },
    { type: 'text', id: 'phoneNumber', placeholder: 'Numéro de téléphone', required: true },
  ];

  inputAttributes.forEach(attr => {
    const input = createInput(attr.type, attr.id, attr.placeholder, attr.required);
    form.appendChild(input); // Append inputs to body, you can change this to another parent element if needed
  });

  const submit = document.createElement('button');
  submit.textContent = "S'inscrire";
  submit.type = 'submit';
  submit.className = 'btn btn-info';
  const formCheckWrapper = document.createElement('div');
  formCheckWrapper.className = 'mb-3 form-check';

  form.appendChild(submit);

  main.appendChild(form);


  form.addEventListener('submit', onRegister);
}


async function onRegister(e) {
  e.preventDefault();

  const lastName = document.querySelector('#lastName').value;
  const firstName = document.querySelector('#firstName').value;
  const email = document.querySelector('#email').value;
  const password = document.querySelector('#password').value;
  const phoneNumber = document.querySelector('#phoneNumber').value;

  // let role = document.querySelector('input[name="gender"]:checked').value;
  let role;
  if (email.endsWith("@student.vinci.be")) {
    role = "Student";
  }

  const options = {
    method: 'POST',
    body: JSON.stringify({
      email,
      password,
      lastName,
      firstName,
      phoneNumber,
      role : role.value
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  console.log("ROLE", role);

  const response = await fetch(`http://localhost:3000/auths/register`, options);
  console.log("RESPONSE", response);

  if (!response.ok) {
    throw new Error(
        `fetch error : ${response.status} : ${response.statusText}`);
  }

  const authenticatedUser = await response.json();

  console.log('Newly registered & authenticated user : ', authenticatedUser);

  setAuthenticatedUser(authenticatedUser);

  Navbar();

  Navigate('/');
}

export default RegisterPage;
