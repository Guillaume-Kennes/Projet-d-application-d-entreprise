import {clearPage} from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';
import {getToken, getUserInfoFromToken} from "../../utils/user";

const ProfilePage = async () => {
  clearPage();
  Navbar();
  const user = await getValues();
  renderProfilePage(user);
};

function renderProfilePage(user) {
  console.log(user);
  const main = document.querySelector('main');

  main.innerHTML =
      <div class="container">
        <div class="row">
          <div class="col-md-5 bg-secondary rounded mt-3 ms-3">
            <div class="d-flex justify-content-between align-items-center">
              <h3 class="text-right mt-3">Mon profil</h3>
              <button class="btn btn-primary me-3">Modifier le mot de passe</button>
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
        </div>
      </div>;
}

async function getValues() {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
      id: getUserInfoFromToken().id
    },
  };
  let user;
  const response = await fetch(`http://localhost:3000/users`, options);
  if (!response.ok) {
    throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
  }else{
    user = await response.json();
    return user;
  }
}
export default ProfilePage;