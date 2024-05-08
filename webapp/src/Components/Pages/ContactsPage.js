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
  await allContacts();
};

async function allContacts() {
  const main = document.querySelector('main');
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

  const response = await fetch(`http://localhost:3000/contacts/${id}`, options);
  if (!response.ok) {
    throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
  }

  try {
    const contacts = await response.json();

    const renderContacts = (contacts1) => {
      const contactRows = contacts1.map(contact => `
          <tr>
            <td>${contact.company.tradeName}</td>
            ${contact.company.designation ? `<td>${contact.company.designation}</td>` : `<td>Aucune désignation</td>`}
            <td>${contact.state}</td>
            ${contact.reasonForRefusal ? `<td>${contact.reasonForRefusal}</td>` : `<td>Contact non-refusé</td>`}
            ${contact.meetingPlace ? `<td>${contact.meetingPlace}</td>` : `<td>Contact pas encore pris</td>`}
            <td><button class="takenButton" data-contact-id = "${contact.id}"}">Indiquer que le contact est pris</button></td>
            <td><button class="refusedButton" data-contact-id = "${contact.id}"}">Indiquer que le contact est refusé</button></td>
            <td><button class="unfollowedButton" data-contact-id = "${contact.id}"}">Ne plus suivre le contact</button></td>
            <td><button class="internshipButton" data-company-id="${contact.company.id}" data-contact-id = "${contact.id}"}">Créer un stage</button></td>
          </tr>
        `);

      main.innerHTML = `
          <table class="table table-bordered">
            <thead>
              <tr>
                <th scope="col">Nom de l'entreprise</th>
                <th scope="col">Désignation de l'entreprise</th>
                <th scope="col">Etat</th>
                <th scope="col">Raison du refus</th>
                <th scope="col">Lieu de rencontre</th>
                <th scope="col">Indiquer une rencontre</th>
                <th scope="col">Indiquer un refus</th>
                <th scope="col">Abandonner un contact</th>
                <th scope="col">Accepter un stage</th>
              </tr>
            </thead>
            <tbody>
              ${contactRows.join('')}
            </tbody>
          </table>
        `;
    };

    renderContacts(contacts);

    document.querySelectorAll('.takenButton').forEach(button => {
      const contactId = button.getAttribute('data-contact-id');
      button.addEventListener("click", () => Navigate(`/meetCompany?contactId=${contactId}`));
    });

    document.querySelectorAll('.refusedButton').forEach(button => {
      const contactId = button.getAttribute('data-contact-id');
      button.addEventListener("click", () => Navigate(`/companyRefused?contactId=${contactId}`));
    });

    document.querySelectorAll('.unfollowedButton').forEach(button => {
      const contactId = button.getAttribute('data-contact-id');
      button.addEventListener('click', () => stopFollowing(contactId));
    });

    document.querySelectorAll('.internshipButton').forEach(button => {
      const companyId = button.getAttribute('data-company-id');
      const contactId = button.getAttribute('data-contact-id');

      console.log("company id fnkhiozb : ", companyId);
      console.log("contact id fieozhgfoze : ", contactId);

      button.addEventListener('click', () => Navigate(`/createInternship?companyId=${companyId}&contactId=${contactId}`));
    });

    

  } catch (error) {
    console.log("Erreur");
    alert(
        'Vous ne possédez pas les droits pour accéder à cette ressource. Seulement les professeurs ou administratifs peuvent y accéder');
    console.error('Une erreur est survenue : ', error);
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

export default ContactsPage;
