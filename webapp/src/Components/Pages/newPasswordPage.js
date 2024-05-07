import {getToken} from "../../utils/user";
import {getAuthenticatedUser} from "../../utils/auths";
import {clearPage} from "../../utils/render";
import Navigate from "../Router/Navigate";

const newPassword = async () => {
  clearPage();
  showFormPassword();
};

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
    Navigate(`/users`);
  } catch (error) {
    console.error('Error setting new password :', error);
  }
}

export default newPassword;