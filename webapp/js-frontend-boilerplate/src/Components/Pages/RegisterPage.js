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
  // Définir la fonction createInput
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

  // Définir la fonction createLabel
  function createLabel(text){
    const label = document.createElement('label');
    label.className = 'form-label';
    label.textContent = text;
    return label;
  }

  const main = document.querySelector('main');
  const form = document.createElement('form');

  // Créer les champs de formulaire pour les autres informations
  const inputAttributes = [
    { type: 'text', id: 'lastName', placeholder: 'Nom', required: true },
    { type: 'text', id: 'firstName', placeholder: 'Prénom', required: true },
    { type: 'email', id: 'email', placeholder: 'Email', required: true },
    { type: 'password', id: 'password', placeholder: 'Mot de passe', required: true },
    { type: 'text', id: 'phoneNumber', placeholder: 'Numéro de téléphone', required: true },
  ];

  inputAttributes.forEach(attr => {
    const input = createInput(attr.type, attr.id, attr.placeholder, attr.required);
    form.appendChild(input);
  });

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

  // Ajouter les boutons radio au formulaire
  form.appendChild(roleToggleWrapper);

  // Créer le bouton d'inscription
  const submit = document.createElement('button');
  submit.textContent = "S'inscrire";
  submit.type = 'submit';
  submit.className = 'btn btn-info';

  // Ajouter le bouton d'inscription au formulaire
  form.appendChild(submit);

  // Ajouter le formulaire à la page principale
  main.appendChild(form);

  // Ajouter un écouteur d'événements pour gérer l'inscription
  form.addEventListener('submit', onRegister);
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
    const professorInput = document.querySelector('input[value="Professeur"]');
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

    const response = await fetch(`http://localhost:3000/auths/register`,
        options);
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