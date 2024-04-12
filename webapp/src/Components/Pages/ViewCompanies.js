import { clearPage } from "../../utils/render";
import { getToken } from "../../utils/user";

const viewAllCompaniesPage = async () => {
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

async function allCompanies(companies) {
  const main = document.querySelector('main');
  main.innerHTML = `
        <table class="table table-bordered">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Nom <button class="sort-button" data-column="tradeName" value="tradeName">&#x25BC;</button><button class="sort-button" data-column="tradeName" value="-tradeName">&#x25B2;</button></th>
              <th scope="col">Adresse <button class="sort-button" data-column="address" value="address">&#x25BC;</button><button class="sort-button" data-column="address" value="-address">&#x25B2;</button></th>
              <th scope="col">Ville <button class="sort-button" data-column="city" value="city">&#x25BC;</button><button class="sort-button" data-column="city" value="-city">&#x25B2;</button></th>
              <th scope="col">Moyen de communication <button class="sort-button" data-column="meansOfCommunication" value="communication">&#x25BC;</button><button class="sort-button" data-column="meansOfCommunication" value="-communication">&#x25B2;</button></th>
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

function setCompanyRow(companies) {
  const body = document.querySelector("tbody");
  companies.forEach(company =>  {

    body.innerHTML += `
      <tr>
            <td>${company.id}</td>
            <td>${company.tradeName}</td>
             <td >${company.address}</td>
             <td >${company.city}</td>
             <td >${company.meansOfCommunication || ''}</td>
        
      </tr>
    `;
  });

}

/*
  renderTable(companies);
  addListeners();

  const sortData = (column, order) => companies.slice().sort((a, b) => {
    const aValue = a[column];
    const bValue = b[column];
    return (order === 'asc') ? aValue.localeCompare(bValue) : bValue.localeCompare(aValue);
  })

} catch (error) {
  // Gestion des erreurs existante
  alert('Vous ne possédez pas les droits pour accéder à cette ressource. Seulement les professeurs ou administratifs peuvent y accéder');
  console.error('Une erreur est survenue : ', error);
}
}

*/

export default viewAllCompaniesPage;