import { clearPage } from '../../utils/render';
import {getToken} from "../../utils/user";
import Navigate from "../Router/Navigate";

const meetCompany = async () => {
  clearPage();
  const idContact = getIdContactFromUrl();
  showForm(idContact);
};

function showForm(idContact) {
  const main = document.querySelector('main');
  main.innerHTML = `
    <div class="container mt-5">
      <h1>Formulaire de Contact</h1>
      <form id="meet-company-form">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" id="contactMade">
          <label class="form-check-label" for="contactMade">
            Contact pris
          </label>
        </div>
        <br>
        <h3> Lieu de rencontre avec l'entreprise </h3>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="meetingLocation" id="entreprise" value="entreprise">
          <label class="form-check-label" for="entreprise">
            Rencontre dans l'entreprise
          </label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="meetingLocation" id="distance" value="à distance">
          <label class="form-check-label" for="distance">
            Rencontre à distance
          </label>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Envoyer</button>
      </form>
    </div>
  `;

  const form = document.getElementById('meet-company-form');
  form.addEventListener('submit', (e) => createMeeting(e, idContact));
}

async function createMeeting(e, idContact) {
  e.preventDefault();
  const meetLocation = document.querySelector('input[name="meetingLocation"]:checked').value;
  const token = getToken();
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify({ meetLocation }),
  };

  try {
    const response = await fetch(`http://localhost:3000/contacts/meet/${idContact}`, options);
    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
    Navigate(`/contacts`);
  } catch (error) {
    console.error('Error meeting company:', error);
  }
}

function getIdContactFromUrl() {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get('contactId');
}

export default meetCompany;
