/* eslint-disable no-console */
import {
  setAuthenticatedUser,
} from '../../utils/auths';
import { clearPage } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const RegisterPage = () => {
  clearPage();
  renderRegisterForm();
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.id = 'registerForm'; // Add an ID for easier styling
  form.className = 'container register-container'; // Set the form class to match the fixed HTML structure
  form.style.marginTop = '100px'; // Set the top margin using inline styles

  const colDiv1 = document.createElement('div');
  colDiv1.className = 'col-lg-4 mx-auto';

  // Create input elements
  const lastName = createInput('text', 'lastName', 'Nom', true);
  const firstName = createInput('text', 'firstName', 'Prénom', true);
  const email = createInput('email', 'email', 'Email', true);
  const passwordGroup = createPasswordInput();
  const phoneNumber = createInput('text', 'phoneNumber', 'Numéro de téléphone', true);

  // Append inputs to form
  colDiv1.appendChild(lastName);
  colDiv1.appendChild(firstName);
  colDiv1.appendChild(email);
  colDiv1.appendChild(passwordGroup);
  colDiv1.appendChild(phoneNumber);

  // Créer les boutons radio pour le choix du rôle
  const roleToggleWrapper = document.createElement('div');
  roleToggleWrapper.className = 'mb-3';

  const professorLabel = document.createElement('label');
  professorLabel.className = 'mr-3';
  professorLabel.textContent = 'Professeur';

  const professorInput = document.createElement('input');
  professorInput.type = 'radio';
  professorInput.name = 'role';
  professorInput.value = 'Professeur';
  professorInput.className = 'mr-2';
  professorInput.id = 'professorRadio';

  const administrativeLabel = document.createElement('label');
  administrativeLabel.textContent = 'Administratif';

  const administrativeInput = document.createElement('input');
  administrativeInput.type = 'radio';
  administrativeInput.name = 'role';
  administrativeInput.value = 'Administratif';

  roleToggleWrapper.appendChild(professorInput);
  roleToggleWrapper.appendChild(professorLabel);
  roleToggleWrapper.appendChild(administrativeInput);
  roleToggleWrapper.appendChild(administrativeLabel);

  colDiv1.appendChild(roleToggleWrapper);

  const textCenterDiv = document.createElement('div');
  textCenterDiv.className = 'text-center';

  const submit = document.createElement('button');
  submit.type = 'submit';
  submit.className = 'btn btn-primary btn-block btn-light myButton';
  submit.textContent = "S'inscrire";

  textCenterDiv.appendChild(submit);
  form.appendChild(colDiv1);
  form.appendChild(textCenterDiv);

  main.appendChild(form);
  form.addEventListener('submit', onRegister);
}

// Helper functions to create input elements
function createInput(type, id, placeholder, required = true) {
  const input = document.createElement('input');
  input.type = type;
  input.id = id;
  input.placeholder = placeholder;
  input.required = required;
  input.className = 'form-control mb-3';
  return input;
}

function createPasswordInput() {
  const passwordGroup = document.createElement('div');
  passwordGroup.className = 'form-group';

  const passwordInputDiv = document.createElement('div');
  passwordInputDiv.className = 'input-group';

  const password = createInput('password', 'password', 'Mot de passe', true);

  const togglePasswordBtn = document.createElement('button');
  togglePasswordBtn.type = 'button';
  togglePasswordBtn.className = 'btn btn-outline-secondary mb-3';
  togglePasswordBtn.id = 'togglePasswordBtn';
  togglePasswordBtn.textContent = 'Afficher/Masquer';

  // Toggle Password Functionality
  let isPasswordVisible = false;
  togglePasswordBtn.addEventListener('click', () => {
    if (isPasswordVisible) {
      password.type = 'password';
      isPasswordVisible = false;
    } else {
      password.type = 'text';
      isPasswordVisible = true;
    }
  });

  passwordInputDiv.appendChild(password);
  passwordInputDiv.appendChild(togglePasswordBtn);
  passwordGroup.appendChild(passwordInputDiv);

  return passwordGroup;
}

async function onRegister(e) {
  e.preventDefault();

  const lastName = document.querySelector('#lastName').value;
  const firstName = document.querySelector('#firstName').value;
  const email = document.querySelector('#email').value;
  const password = document.querySelector('#password').value;
  const phoneNumber = document.querySelector('#phoneNumber').value;

  let role;

  if (email.endsWith('@vinci.be')) {
    const professorInput = document.querySelector('#professorRadio');
    role = professorInput.checked ? 'Professeur' : 'Administratif';
  } else if (email.endsWith('@student.vinci.be')) {
    role = 'Etudiant';
  } else {
    role = 'Autre';
  }

  const options = {
    method: 'POST',
    body: JSON.stringify({
      email,
      password,
      lastName,
      firstName,
      phoneNumber,
      role
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  try{
    const response = await fetch(`http://localhost:3000/auths/register`, options);

    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }

    const authenticatedUser = await response.json();

    setAuthenticatedUser(authenticatedUser);

    Navbar();

    Navigate('/');
  } catch (error) {
    alert('Une erreur est survenue pendant l\'inscription. Réessayez s\'il vous plaît.');
    console.error('Une erreur est survenue pendant l\'inscription : ', error);
  }
}

export default RegisterPage;
