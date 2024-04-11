import {clearPage} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";

const viewAllCompaniesPage = async () => {
  clearPage();
  await allCompanies();
}

async function allCompanies() {
  const main = document.querySelector('main');

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      authorization: getAuthenticatedUser().token
    },
  };

  const response = await fetch("http://localhost:3000/companies/getAllEnterprises",
      options);

  try {
    if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`)

    const companies = await response.json();

    const companyRows = companies.map(company => `
          <tr>
            <td>${company.id}</td>
            <td>${company.tradeName}</td>
            <td>${company.designation}</td>
            <td>${company.address}</td>
            <td>${company.city}</td>
            <td>${company.meansOfCommunication}</td>
          </tr>
        `);

    main.innerHTML = `
          <table class="table table-bordered">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Trade Name</th>
                <th scope="col">Designation</th>
                <th scope="col">Address</th>
                <th scope="col">City</th>
                <th scope="col">Means of communication</th>
              </tr>
            </thead>
            <tbody>
              ${companyRows.join('')}
            </tbody>
          </table>
        `;
  } catch (error) {
    // Gestion des erreurs existante
    alert(
        'Vous ne possédez pas les droits pour accéder à cette ressource. Seulement les professeurs ou administratifs peuvent y accéder');
    console.error('Une erreur est survenue : ', error);
  }
}

export default viewAllCompaniesPage();