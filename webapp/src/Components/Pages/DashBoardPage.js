import {clearPage} from "../../utils/render";
import {getToken} from "../../utils/user";

const viewDashBoard = async () => {
  clearPage();
  const companies = await fetchCompanies();
  await allCompanies(companies);
}














async function fetchCompanies() {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = "http://localhost:3000/companies/getEnterprises";
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}

async function fetchNumberOfStudentsTakenByCompany(idCompany) {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = `http://localhost:3000/companies/numberOfStudentsTaken/${idCompany}`;
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}

async function allCompanies(companies) {
  const main = document.querySelector('main');
  main.innerHTML = `
        <table class="table table-bordered">
          <thead>
            <tr>
              <th scope="col">Nom <button class="sort-button" data-column="tradeName" value="tradeName">&#x25BC;</button><button class="sort-button" data-column="tradeName" value="-tradeName">&#x25B2;</button></th>
              <th scope ="col">Désignation <button class="sort-button" data-column="designation" value="designation">&#x25BC;</button><button class="sort-button" data-column="designation" value="-designation">&#x25B2;</button></th>
              <th scope="col">Numéro de téléphone <button class="sort-button" data-column="meansOfCommunication" value="communication">&#x25BC;</button><button class="sort-button" data-column="meansOfCommunication" value="-communication">&#x25B2;</button></th>
              <th scope="col">Nombre d'étudiants pris en stage <button class="sort-button" data-column="numberOfStudents" value="numberOfStudents">&#x25BC;</button><button class="sort-button" data-column="numberOfStudents" value="-numberOfStudents">&#x25B2;</button></th>
              <th scope="col">Black listée <button class="sort-button" data-column="blackListed" value="blackListed">&#x25BC;</button><button class="sort-button" data-column="blackListed" value="-blackListed">&#x25B2;</button></th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      `;
  setCompanyRow(companies);
  await addListeners();
}


async function addListeners() {
  const main = document.querySelector("main");
  // Écoutez les événements de clic sur les boutons de tri
  main.querySelectorAll('.sort-button').forEach(button => {
    button.addEventListener('click', async () => {
      const sortColumn = button.dataset.column;
      const sortOrder = button.value.startsWith('-') ? 'desc' : 'asc';
      let companies = await fetchCompanies();
      // Trier les entreprises
      companies = sortCompanies(companies, sortColumn, sortOrder);
      // Rendre les entreprises triées
      await allCompanies(companies);
    });
  });
}

function sortCompanies(companies, sortColumn, sortOrder) {
  return companies.sort((a, b) => {
    if (sortOrder === 'asc') {
      return a[sortColumn] > b[sortColumn] ? 1 : -1;
    }
    return a[sortColumn] < b[sortColumn] ? 1 : -1;

  });
}

async function setCompanyRow(companies) {
  const body = document.querySelector("tbody");
    const promises = companies.map(async (company) => {
      const numberOfStudents = await fetchNumberOfStudentsTakenByCompany(company.id);

      return `
      <tr>
            <td>${company.tradeName}</td>
            <td>${company.designation || '/'}</td>
            <td>${company.meansOfCommunication || ''}</td>
            <td>${numberOfStudents || '0'}</td>
            <td>${company.blackListed ? 'Oui' : 'Non'}</td>
      </tr>
    `;
    });
  // Attendre la résolution de toutes les promesses
  const tableRows = await Promise.all(promises);

  // Ajouter les lignes au tableau
  body.innerHTML = tableRows.join('');

}



export default viewDashBoard;