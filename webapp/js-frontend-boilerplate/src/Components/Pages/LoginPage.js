/* eslint-disable no-console */
import { getRememberMe, setAuthenticatedUser, setRememberMe } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const LoginPage = () => {
  clearPage();
  renderPageTitle('Login');
  renderRegisterForm();
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  const mainFiller = `
    <div class="container login-container" style="margin-top: 200px;">
      <form id="loginForm">
        <div class="col-lg-4 mx-auto">
          <div class="form-group">
            <input type="text" name="email" class="form-control mb-2" id="email" placeholder="Email address">
          </div>
        </div>
        <div class="col-lg-4 mx-auto">
          <div class="form-group">
            <div class="input-group">
              <input type="password" name="password" class="form-control mb-2" id="password" placeholder="Password" required>    
              <button class="btn btn-outline-secondary mb-2" type="button" id="togglePasswordBtn">
                Show/Hide
              </button>
            </div>
          </div>
          <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="rememberme" checked>
            <label class="form-check-label" for="rememberme">Remember me</label>
          </div>
        </div>
        <div class="text-center">
          <button type="submit" class="btn btn-primary btn-block btn-light myButton">Login</button>
          <p>Not a member ? <a href="#" tagName="myButton" class="myButton" data-uri="/signup">Sign up</a></p>
          <div id="errorContainer" class="text-danger"></div>
        </div>
      </form>
    </div>
  `;
  main.innerHTML = mainFiller;

  const togglePasswordVisibility = () => {
    const passwordInput = document.getElementById('password');
    
    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
    } else {
      passwordInput.type = 'password';
    }
  };

  const togglePasswordBtn = document.getElementById('togglePasswordBtn');
  togglePasswordBtn.addEventListener('click', togglePasswordVisibility);

  const rememberme = document.getElementById('rememberme');
  const remembered = getRememberMe();
  rememberme.checked = remembered;
  rememberme.addEventListener('click', onCheckboxClicked);

  const checkLabel = document.createElement('label');
  checkLabel.htmlFor = 'rememberme';
  checkLabel.className = 'form-check-label';
  checkLabel.textContent = 'Remember me';

 
  main.appendChild(form);
  form.addEventListener('submit', onLogin);
}

function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}

async function onLogin(e) {
  e.preventDefault();

  const username = document.querySelector('#username').value;
  const password = document.querySelector('#password').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({
      username,
      password,
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`${process.env.API_BASE_URL}/auths/login`, options);
  console.log("JE SUIS ICI !!!!!!!!!!!")

  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);

  const authenticatedUser = await response.json();

  console.log('Authenticated user : ', authenticatedUser);

  setAuthenticatedUser(authenticatedUser);

  Navbar();

  Navigate('/');
}

export default LoginPage;
