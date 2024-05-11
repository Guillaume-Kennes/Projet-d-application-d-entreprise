import Chart from 'chart.js/auto';
import 'chartjs-plugin-datalabels';
import {clearPage} from "../../utils/render";
import {getToken} from "../../utils/user";
import Navigate from "../Router/Navigate";


const viewDashBoard = async () => {
  clearPage();
  await showPieChart();
  await allCompanies();
}

async function showPieChart() {
  const main = document.querySelector('main');
  const dashboard = document.createElement('div');
  dashboard.id = 'dashboard';
  dashboard.style.display = 'flex';
  dashboard.style.flexDirection = 'column';
  dashboard.style.alignItems = 'center';
  dashboard.style.justifyContent = 'center';
  dashboard.style.padding = '20px';
  dashboard.style.margin = '20px';
  dashboard.style.width = '50%';
  dashboard.style.marginLeft = '25%';

  const canvas = document.createElement('canvas');
  canvas.id = 'pie-chart';


  dashboard.appendChild(canvas);

  main.appendChild(dashboard);

  await createSchoolYearDropdown();

  const schoolYears = await fetchGetAllSchoolYears();
  const currentOne = schoolYears[schoolYears.length - 1];
  await showPieChartBySchoolYear(currentOne);
}

async function showPieChartBySchoolYear(schoolYear) {
  const canvas = document.getElementById('pie-chart');
  const canvasContainer = canvas.parentNode;

  canvas.width = canvasContainer.offsetWidth;
  canvas.height = canvasContainer.offsetHeight;

  const studentsFounded = await fetchStudentsWithInternship(schoolYear);
  const studentsNotFound = await fetchStudentsWithoutInternship(schoolYear);


  const data = {
    labels: ['Ont un stage', 'N\'ont pas de stage'],
    datasets: [
      {
        label: `Ont un stage (${studentsFounded})`,
        data: [studentsFounded, studentsNotFound],
        backgroundColor: ['#135a81', '#62a2a4'],
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
        boxWidth: 100,
        boxHeight: 100,
      },
      title: {
        display: true,
        text: `Les étudiants`,
      },
    },
  };
  const ctx = canvas.getContext('2d');

  // Check if a chart instance already exists
  if (canvas.chart) {
    // Destroy the existing chart instance
    canvas.chart.destroy();
  }

  // Create a new chart instance
  // Store the new chart instance for future reference
  canvas.chart = new Chart(ctx, {
    type: 'pie',
    data,
    options,
  });
}

async function allCompanies() {
  const companies = await fetchCompanies();
  const main = document.querySelector('main');
  const tabDiv = document.createElement('div');
  tabDiv.innerHTML = `
        <table class="table table-bordered mt-5">
          <thead>
            <tr>
              <th scope="col">Nom <button class="sort-button" data-column="tradeName" value="tradeName">&#x25BC;</button><button class="sort-button" data-column="tradeName" value="-tradeName">&#x25B2;</button></th>
              <th scope ="col">Appelation <button class="sort-button" data-column="designation" value="designation">&#x25BC;</button><button class="sort-button" data-column="designation" value="-designation">&#x25B2;</button></th>
              <th scope="col">Numéro de téléphone <button class="sort-button" data-column="meansOfCommunication" value="communication">&#x25BC;</button><button class="sort-button" data-column="meansOfCommunication" value="-communication">&#x25B2;</button></th>
              <th scope="col">Nombre d'étudiants pris en stage <button class="sort-button" data-column="numberOfStudents" value="numberOfStudents">&#x25BC;</button><button class="sort-button" data-column="numberOfStudents" value="-numberOfStudents">&#x25B2;</button></th>
              <th scope="col">Black listée <select class="filter-select" id="blacklisted-filter">
                    <option value="">Tous</option>
                    <option value="true">Oui</option>
                    <option value="false">Non</option>
                  </select></th>
              <th scope="col"> </th>
              <th scope="col">Voir contacts passés</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      `;
  main.appendChild(tabDiv);
  await setCompanyRow(companies);
  await addListeners();
}

async function setCompanyRow(companies) {
  const body = document.querySelector('table');
  const bodyTable = body.querySelector('tbody'); // Sélectionnez tbody dans la balise ajoutée à body

  bodyTable.innerHTML = '';

  companies.forEach(company => {
    bodyTable.innerHTML += `
      <tr>
        <td>${company.tradeName}</td>
        <td>${company.designation || '/'}</td>
        <td>${company.meansOfCommunication || ''}</td>
        <td>${company.numberOfStudents || '0'}</td>
        <td>${company.isBlackListed ? 'Oui' : 'Non'}</td>
        <td>${company.isBlackListed ? `${company.motivationBlackList}` : `<button data-company-id="${company.id}" class="btn btn-outline-dark myButton">Blacklister l'entreprise</button>`}</td>
        <td><button class="btn btn-outline-dark contactsButton" data-company-id="${company.id}">Contacts</button></td>
      </tr>
    `;
  });

  document.querySelectorAll('.myButton').forEach(button => {
    const companyId = button.getAttribute('data-company-id');
    button.addEventListener("click", () => showFormBlackList(companyId));
  });
  document.querySelectorAll('.contactsButton').forEach(button => {
    const companyId = button.getAttribute('data-company-id');
    button.addEventListener('click', () => Navigate(`/contactsCompany?companyId=${companyId}`));
  });
}


// async function showContacts(e, id) {
//   e.preventDefault();
//   const token = getToken();
//   const options = {
//     method: 'GET',
//     headers: {
//       'Content-Type': 'application/json',
//       Authorization: token,
//     },
//   };
//   let contacts;
//   const response = await fetch(`http://localhost:3000/companies/${id}`, options);
//   if (!response.ok) {
//     throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
//   }else{
//     contacts = await response.json();
//     displayContacts(contacts);
//   }
// }

// function displayContacts(contacts) {
//   const main = document.querySelector('main');
//
//   main.innerHTML =
//       `<div class="fw-bold mb-n1">Contacts passés</div>
//             <ul id="contactsList"></ul>`;
//
//   const contactsList = document.getElementById("contactsList");
//   console.log("CONTACTS", contacts)
//   if (contacts && contacts.length > 0) {
//     contacts.forEach(contact => {
//       console.log("CONTACT", contact)
//       const listItem = document.createElement("li");
//       listItem.textContent = `Nom: ${contact.name}, Email: ${contact.email}, Téléphone: ${contact.phone}`;
//       contactsList.appendChild(listItem);
//     });
//   } else {
//     contactsList.innerHTML = "<li>Aucun contact avec cette entreprise</li>";
//   }
// }

async function fetchStudentsWithInternship(schoolYear) {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = `http://localhost:3000/users/getStudentsWithInternship/${schoolYear}`;
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}

async function fetchStudentsWithoutInternship(schoolYear) {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = `http://localhost:3000/users/getStudentsWithoutInternship/${schoolYear}`;
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
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
      await setCompanyRow(companies);
    });
  });

  // Écouter le changement de sélection dans le menu déroulant "Black listée"
  const blacklistedFilter = document.getElementById('blacklisted-filter');
  blacklistedFilter.addEventListener('change', async () => {
    const selectedOption = blacklistedFilter.value;
    let companies = await fetchCompanies();
    if (selectedOption !== '') {
      companies = companies.filter(company => company.isBlackListed.toString() === selectedOption);
    }
    await setCompanyRow(companies);
  });
}
//
function sortCompanies(companies, sortColumn, sortOrder) {
  return companies.sort((a, b) => {
    if (sortOrder === 'asc') {
      return a[sortColumn] > b[sortColumn] ? 1 : -1;
    }
    return a[sortColumn] < b[sortColumn] ? 1 : -1;

  });
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

async function fetchCompaniesBySchoolYear(schoolYear) {
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = `http://localhost:3000/companies/getEnterprises/${schoolYear}`;
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}


async function fetchGetAllSchoolYears(){
  const token = getToken();
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };
  const url = "http://localhost:3000/internship/schoolYears";
  const response = await fetch(url, options);

  if (!response.ok) throw new Error(
      `fetch error : ${response.status} : ${response.statusText}`)

  return response.json();
}



async function createSchoolYearDropdown() {
  const dashboard = document.getElementById('dashboard');

  const dropdown = document.createElement('select');
  dropdown.id = 'schoolYear-dropdown';

  let schoolYears;

  try {
    schoolYears = await fetchGetAllSchoolYears();
  } catch (error) {
    console.error('Error fetching school years:', error);
    return;
  }

  if(!schoolYears || schoolYears.length === 0){
    console.error('No school year found');
    return;
  }

  schoolYears.forEach(schoolYear => {
    const option = document.createElement('option');
    option.value = schoolYear;
    option.text = schoolYear;
    dropdown.appendChild(option);
  });

  dropdown.value = schoolYears[schoolYears.length - 1];

  // dropdown.addEventListener('change', async (event) => {
  //   const selectedOne = event.target.value;
  //   await showPieChartBySchoolYear(selectedOne);
  // });
  // dashboard.appendChild(dropdown);

  dropdown.addEventListener('change', async (event) => {
    const selectedYear = event.target.value; // Récupérer l'année sélectionnée dans le menu déroulant
    try {
      // Appel à fetchCompaniesBySchoolYear avec l'année sélectionnée
      const companies = await fetchCompaniesBySchoolYear(selectedYear);
      await showPieChartBySchoolYear(selectedYear);
      // Mettre à jour le tableau des entreprises avec les nouvelles données
      await setCompanyRow(companies);

    } catch (error) {
      console.error('Error fetching companies:', error);
    }
  });
  dashboard.appendChild(dropdown);
}



function showFormBlackList(idCompany) {
  const main = document.querySelector('main');
  console.log(`company id : ${idCompany}`);
  main.innerHTML = `
    <div class="container mt-5">
      <h1>Formulaire de blacklist d'une entreprise</h1>
      <form id="refusal-form">
        <h3> Raisons du blacklist </h3>
        <div class="form-group">
          <label for="reason1">Raison :</label>
            <input type="text" class="form-control" id="reason" name="reason" placeholder="Entrez la raison du blacklist" required>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Envoyer</button>
      </form>
    </div>
  `;
  const form = document.getElementById('refusal-form');
  form.addEventListener('submit', (e) => blacklistCompany(e, idCompany));
}

async function blacklistCompany(e, idCompany) {
  e.preventDefault();
  const reasonBlackList = document.querySelector('input[name="reason"]').value;
  console.log(`reasonBlackList : ${reasonBlackList}`);
  const token = getToken();
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify({ reasonBlackList }),
  };

  try {
    const response = await fetch(`http://localhost:3000/companies/blacklist/${idCompany}`, options);
    if (!response.ok) {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
    window.location.reload();
  } catch (error) {
    console.error('Error setting refusal reason :', error);
  }
}

export default viewDashBoard;