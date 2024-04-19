import Chart from 'chart.js/auto';
import {clearPage} from "../../utils/render";
import {getToken} from "../../utils/user";

const viewDashBoard = async () => {
  clearPage();
  const schoolYear = '2023-2024';
  const studentsWithInternship = await fetchStudentsWithInternship(schoolYear);
  const studentsWithoutInternship = await fetchStudentsWithoutInternship(schoolYear);
  drawPieChart(studentsWithInternship, studentsWithoutInternship);
  const companies = await fetchCompanies();
  await allCompanies(companies);
}

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

let myChart;

function drawPieChart(studentsWithInternship, studentsWithoutInternship) {

  const main = document.querySelector('main');
  main.innerHTML = `
    <div style="position:absolute; top:60px; left:10px; width:300px; height:300px; margin-left: 35%">
      <canvas id="myChart"></canvas>
    </div>
  `;
  const pieCanvas = document.getElementById('myChart');
  if (myChart) {
    myChart.destroy();
  }
  // eslint-disable-next-line
  myChart = new Chart(pieCanvas, {
    type: 'pie',
    data: {
      labels: ['Avec stage', 'Sans stage'],
      datasets: [{
        data: [studentsWithInternship, studentsWithoutInternship],
        backgroundColor: ['#40234b', '#62a2a4']
      }]
    },
    options: {
      title: {
        display: true,
        text: 'Répartition des étudiants avec et sans stage'
      },
      responsive: true,
      maintainAspectRatio: false,
    }
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
  await setCompanyRow(companies);
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
  const rowsHTML = await Promise.all(companies.map(async (company) => {
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
    }));
  body.innerHTML = rowsHTML.join('');

  body.querySelectorAll('tr').forEach(row => {
    row.addEventListener('click', () => {
      const companyId = row.dataset.id;
      window.location.href = `/ContactsDetailsPage?id=${companyId}`;
    });
  });
}

export default viewDashBoard;