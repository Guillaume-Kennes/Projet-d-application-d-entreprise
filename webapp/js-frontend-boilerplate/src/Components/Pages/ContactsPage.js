import { clearPage } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import {
  getToken,
  getUserIdFromToken,
} from "../../utils/user";

const ContactsPage = async () => {
  clearPage();
  Navbar();
  try {
    const contacts = await getValues();
    renderContactsPage(contacts);
  } catch (error) {
    console.error('Error fetching user data:', error);
  }
};

function renderContactsPage(contact) {
  const main = document.querySelector('main');

  main.innerHTML = `<div class="fw-bold mb-n1">Vos contacts</div><ul id="contact"></ul>`;

  const contactList = document.getElementById("contact");
  if (contact.contacts && Object.keys(contact.contacts).length > 0) {
    Object.entries(contact.contacts).forEach(([id, description]) => {
      const listItem = document.createElement("li");
      const contactText = document.createElement("span");
      const button = document.createElement("button");

      contactText.textContent = `${description}`; // Display the contact description
      button.textContent = "Indiquer que le contact est pris";
      button.addEventListener("click", () => showForm(id)); // Attach event listener

      listItem.appendChild(contactText);
      listItem.appendChild(button);
      contactList.appendChild(listItem);
    });
  } else {
    contactList.innerHTML = "<li>Aucun contact</li>";
  }
}

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
  form.addEventListener('submit', (e) => meetCompany(e, idContact));
}

async function meetCompany(e, idContact) {
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
    window.location.href = `ContactsPage?id=${getUserIdFromToken()}`;
  } catch (error) {
    console.error('Error meeting company:', error);
  }
}

async function getValues() {
  const token = getToken();
  const id = getUserIdFromToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  };
  let contacts;
  const response = await fetch(`http://localhost:3000/contacts/${id}`, options);
  if (!response.ok) {
    throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
  } else {
    const responseData = await response.text();
    if (responseData.trim() === '') {
      return { contacts: [] }; // Return an empty array if response body is empty
    }
    contacts = JSON.parse(responseData);
    return contacts;
  }
}

export default ContactsPage;
