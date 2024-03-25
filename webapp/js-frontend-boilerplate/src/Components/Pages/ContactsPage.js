import {clearPage} from '../../utils/render';
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

  main.innerHTML =
      `<div class="fw-bold mb-n1">Vos contacts</div>
            <ul id="contact"></ul>`;

  const contactList = document.getElementById("contact");
  if (contact.contacts && Object.keys(contact.contacts).length > 0) {
    Object.entries(contact.contacts).forEach(([id, description]) => {
      const listItem = document.createElement("li");
      const contactText = document.createElement("span");
      const button = document.createElement("button");

      contactText.textContent = `${description}`; // Display the contact description
      button.textContent = "Meet Company";
      button.addEventListener("click", () => meetCompany(id)); // Attach event listener

      listItem.appendChild(contactText);
      listItem.appendChild(button);
      contactList.appendChild(listItem);
    });
  } else {
    contactList.innerHTML = "<li>Aucun contact</li>";
  }
}

async function meetCompany(idContact) {
  const token = getToken();
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  };

  try {
    const response = await fetch(`http://localhost:3000/contacts/meet/${idContact}`, options);
    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
    window.location.href = `http://localhost:3000/contacts/meet/${idContact}`;
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
  }else{
    const responseData = await response.text();
    if (responseData.trim() === '') {
      return { contacts: [] }; // Return an empty array if response body is empty
    }
    contacts = JSON.parse(responseData);
    return contacts;
  }
}
export default ContactsPage;