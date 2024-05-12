import {getToken} from "../../utils/user";
import {getAuthenticatedUser} from "../../utils/auths";
import {clearPage} from "../../utils/render";
import Navigate from "../Router/Navigate";

const modifyPhone = async () => {
  clearPage();
  showFormPhone();
};

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
    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
    Navigate(`/users`);
  } catch (error) {
    console.error('Error setting new phone number :', error);
  }
}

export default modifyPhone;