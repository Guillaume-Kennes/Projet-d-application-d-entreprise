import { clearPage } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import {
  getToken,
} from "../../utils/user";
import {getAuthenticatedUser} from "../../utils/auths";
import Navigate from '../Router/Navigate';


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
      const button2 = document.createElement("button");
      const button3 = document.createElement("button");
      const button4 = document.createElement("button");
      button.className = 'btn btn-primary btn-block btn-light myButton';
      button2.className = 'btn btn-primary btn-block btn-light myButton';
      button3.className = 'btn btn-primary btn-block btn-light myButton';

      contactText.textContent = `${description}`;
      button.textContent = "Indiquer que le contact est pris";
      button.addEventListener("click", () => Navigate(`/meetCompany?contactId=${id}`));
      button2.textContent = "Indiquer que le contact est refusé";
      button2.addEventListener("click", () => Navigate(`/companyRefused?contactId=${id}`));
      button3.textContent = "Arrêter de suivre le contact";
      button3.addEventListener("click", () => stopFollowing(id));
      button4.textContent = "Créer un stage";
      button4.addEventListener("click", () => Navigate(`/createInternship?contactId=${id}`));

      listItem.appendChild(contactText);
      listItem.appendChild(button);
      listItem.appendChild(button2);
      listItem.appendChild(button3);
      listItem.appendChild(button4);
      contactList.appendChild(listItem);
    });
  } else {
    contactList.innerHTML = "<li>Aucun contact</li>";
  }
}

async function stopFollowing(idContact) {
  const token = getToken();
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  };

  try {
    const response = await fetch(`http://localhost:3000/contacts/stop/${idContact}`, options);
    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
  } catch (error) {
    console.error('Error stopping following the contact :', error);
  }

  Navigate(`/contacts`);
}

async function getValues() {
  const authenticatedUser = getAuthenticatedUser();
  console.log("CONTACTS --> authenticatedUser : ", authenticatedUser);
  const id = authenticatedUser?.user?.id;
  console.log("CONTACTS --> authenticatedUserId : ", id);

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
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
