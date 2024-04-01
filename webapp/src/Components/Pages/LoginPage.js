/* eslint-disable no-console */
import {
  getRememberMe,
  setAuthenticatedUser,
  setRememberMe
} from '../../utils/auths';
import {clearPage} from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const LoginPage = () => {
  clearPage();
  renderLoginForm();
};

function renderLoginForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.id = 'loginForm'; // Add an ID for easier styling
  form.className = 'container login-container'; // Set the form class to match the fixed HTML structure
  form.style.marginTop = '100px'; // Set the top margin using inline styles

  const colDiv1 = document.createElement('div');
  colDiv1.className = 'col-lg-4 mx-auto';

  const emailGroup = document.createElement('div');
  emailGroup.className = 'form-group';

  const email = document.createElement('input');
  email.type = 'text';
  email.name = 'email';
  email.className = 'form-control mb-2';
  email.id = 'email';
  email.placeholder = 'Email';
  email.required = true;

  emailGroup.appendChild(email);
  colDiv1.appendChild(emailGroup);

  const passwordGroup = document.createElement('div');
  passwordGroup.className = 'form-group';

  const passwordInputDiv = document.createElement('div');
  passwordInputDiv.className = 'input-group';

  const password = document.createElement('input');
  password.type = 'password';
  password.name = 'password';
  password.className = 'form-control mb-2';
  password.id = 'password';
  password.placeholder = 'Mot de passe';
  password.required = true;

  const togglePasswordBtn = document.createElement('button');
  togglePasswordBtn.type = 'button';
  togglePasswordBtn.className = 'btn btn-outline-secondary mb-2';
  togglePasswordBtn.id = 'togglePasswordBtn';
  togglePasswordBtn.textContent = 'Afficher/Masquer';

  passwordInputDiv.appendChild(password);
  passwordInputDiv.appendChild(togglePasswordBtn);

  passwordGroup.appendChild(passwordInputDiv);
  colDiv1.appendChild(passwordGroup);

  // Remember me checkbox
  const remembermeGroup = document.createElement('div');
  remembermeGroup.className = 'form-group form-check';

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

  remembermeGroup.appendChild(rememberme);
  remembermeGroup.appendChild(checkLabel);
  colDiv1.appendChild(remembermeGroup);

  const textCenterDiv = document.createElement('div');
  textCenterDiv.className = 'text-center';

  const submit = document.createElement('button');
  submit.type = 'submit';
  submit.className = 'btn btn-primary btn-block btn-light myButton';
  submit.textContent = 'Se connecter';

  textCenterDiv.appendChild(submit);
  form.appendChild(colDiv1);
  form.appendChild(textCenterDiv);

  main.appendChild(form);
  form.addEventListener('submit', onLogin);

  // Toggle password visibility
  togglePasswordBtn.addEventListener('click', togglePasswordVisibility);

  function togglePasswordVisibility() {
    if (password.type === 'password') {
      password.type = 'text';
    } else {
      password.type = 'password';
    }
  }
}



function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}

async function onLogin(e) {
  e.preventDefault();

  const email = document.querySelector('#email').value;
  const password = document.querySelector('#password').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({
      email,
      password,
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  try {
    const response = await fetch(`http://localhost:3000/auths/login`, options);

    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }

    const authenticatedUser = await response.json();

    // Stocker le token JWT dans le local storage
    localStorage.setItem('token', authenticatedUser.token);

    setAuthenticatedUser(authenticatedUser);

    Navbar();

    Navigate('/');
  } catch (error) {
    // Gestion des erreurs existante
    alert('Une erreur est survenue pendant la connexion. Réessayez s\'il vous plaît.');
    console.error('Une erreur est survenue pendant la connexion : ', error);
  }
}
export default LoginPage;