import { clearPage } from "../../utils/render";
import { getToken } from "../../utils/user";

const viewAllCompaniesPage = async () => {
  clearPage();
  await allCompanies();
}

async function fetchCompanies(options) {
  const token = getToken();

  const main = document.querySelector('main');

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };

  const response = await fetch("http://localhost:3000/companies/getEntreprises",
      options);

  try {
    if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`)

  return await response.json();
}

async function allCompanies(companies) {
  const main = document.querySelector('main');
  main.innerHTML = `
        <table class="table table-bordered">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Trade Name <button class="sort-button" data-column="tradeName" value="tradeName">&#x25BC;</button><button class="sort-button" data-column="tradeName" value="-tradeName">&#x25B2;</button></th>
              <th scope="col">Address <button class="sort-button" data-column="address" value="address">&#x25BC;</button><button class="sort-button" data-column="address" value="-address">&#x25B2;</button></th>
              <th scope="col">City <button class="sort-button" data-column="city" value="city">&#x25BC;</button><button class="sort-button" data-column="city" value="-city">&#x25B2;</button></th>
              <th scope="col">Means of communication <button class="sort-button" data-column="meansOfCommunication" value="communication">&#x25BC;</button><button class="sort-button" data-column="meansOfCommunication" value="-communication">&#x25B2;</button></th>
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
      const sort = button.value; // Récupérer la valeur du bouton de tri
      console.log(sort);
      const options = {
        method: 'GET',
        body: JSON.stringify({
          sort,
        }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: getToken(),
        },
      };

      const companies = await fetchCompanies(options);
      // Rendre les entreprises triées
      // TODO API TRI DES ENTREPRISES

      allCompanies(companies);
    });
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

export default viewAllCompaniesPage;