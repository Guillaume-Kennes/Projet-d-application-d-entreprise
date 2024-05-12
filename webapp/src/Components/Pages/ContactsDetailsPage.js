import { getToken } from "../../utils/user";
import { clearPage } from "../../utils/render";

const viewContactsDetails = async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const companyId = urlParams.get('company_id');
  clearPage();
  const contacts = await fetchContactInfos(companyId);
  const student = await fetchStudentContactInfo(companyId);
  await displayAllContacts(contacts, student);
}

async function fetchContactInfos(companyId) {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = `http://localhost:3000/contacts/getContacts/${companyId}`;
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}

async function fetchStudentContactInfo(companyId) {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = `http://localhost:3000/contacts/getStudentContacts/${companyId}`;
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}

async function displayAllContacts(contacts, student) {
  const main = document.querySelector('main');
  main.innerHTML = `
        <table class="table table-bordered">
          <thead>
            <tr>
              <th scope="col">Nom </th>
              <th scope ="col">Prénom </th>
              <th scope="col">Email </th>
              <th scope="col">Téléphone </th>
              <th scope="col">Année académique </th>
              <th scope="col">Etat </th>
              <th scope="col">Lieu de rencontre </th>
              <th scope="col">Raison de refus </th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      `;
  const tbody = main.querySelector('tbody');
  const tr = document.createElement('tr');

  tr.innerHTML += `
      <td>${student.lastName}</td>
      <td>${student.firstName}</td>
      <td>${student.email}</td>
      <td>${student.phoneNumber}</td>
      <td>${student.registrationDate}</td>
    `;

  contacts.forEach(contact => {
    tr.innerHTML += `
      <td>${contact.state}</td>
      <td>${contact.meetingPlace}</td>
      <td>${contact.reasonForRefusal}</td>
    `;
  });
  tbody.appendChild(tr);

}

export default viewContactsDetails;
