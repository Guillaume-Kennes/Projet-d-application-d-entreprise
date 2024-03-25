import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage } from '../../utils/render';

const meetCompany = async () => {
  clearPage();
  renderForm();
};

function renderForm() {
  const main = document.querySelector('main');
  main.innerHTML +=
      `
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
                    <input class="form-check-input" type="radio" name="meetingLocation" id="distance" value="distance">
                    <label class="form-check-label" for="distance">
                        Rencontre à distance
                    </label>
                </div>
                <button type="submit" class="btn btn-primary mt-3">Submit</button>
            </form>
        </div>
    `;

  const form = document.getElementById('meet-company-form');

  form.addEventListener('submit', saveMeeting);
}

async function saveMeeting(e) {
  e.preventDefault();
  const contactMade = document.getElementById('contactMade').checked;
  const meetLocation = document.querySelector('input[name="meetingLocation"]:checked').value;
  console.log('Choix de l\'utilisateur (contact made) :', contactMade);
  console.log('Choix de l\'utilisateur (meet location) :', meetLocation);

  const options = {
    method: 'POST',
    body: JSON.stringify({
      contactMade,
      meetLocation,
    }),
    headers: {
      'Content-Type': 'application/json',
      authorization: getAuthenticatedUser().token,
    },
  };

  const response = await fetch(`http://localhost:3000/contacts/meet`, options);

  if(!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
}

export default meetCompany;
