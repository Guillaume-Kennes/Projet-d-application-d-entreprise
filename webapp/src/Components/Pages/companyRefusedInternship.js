import { clearPage } from '../../utils/render';
import {getToken} from "../../utils/user";
import Navigate from "../Router/Navigate";

const companyRefusedInternship = async () => {
    clearPage();
    const idContact = getIdContactFromUrl();
    showFormRefusal(idContact);
};

function showFormRefusal(idContact) {
    const main = document.querySelector('main');
    main.innerHTML = `
    <div class="container mt-5">
      <h1>Formulaire de refus d'un contact</h1>
      <form id="refusal-form">
        <h3> Raisons du refus </h3>
        <div class="form-group">
          <label for="reason1">Raison :</label>
            <input type="text" class="form-control" id="reason" name="reason" placeholder="Entrez la raison du refus" required>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Envoyer</button>
      </form>
    </div>
  `;
    const form = document.getElementById('refusal-form');
    form.addEventListener('submit', (e) => refuseInternship(e, idContact));
}

async function refuseInternship(e, idContact) {
    e.preventDefault();
    const reasonRefusal = document.querySelector('input[name="reason"]').value;
    const token = getToken();
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: token,
        },
        body: JSON.stringify({ reasonRefusal }),
    };

    try {
        const response = await fetch(`http://localhost:3000/contacts/companyrefused/${idContact}`, options);
        if (!response.ok) {
            throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
        }
    } catch (error) {
        console.error('Error setting refusal reason :', error);
    }

    Navigate(`/contacts`);
}

function getIdContactFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('contactId');
}

export default companyRefusedInternship;