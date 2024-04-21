import Chart from 'chart.js/auto';
import 'chartjs-plugin-datalabels';
import {clearPage} from "../../utils/render";
import {getToken} from "../../utils/user";

const viewDashBoard = async () => {
  clearPage();
  const schoolYear = getCurrentAcademicYear();
  await showPieChart(schoolYear);
  // await createDropdownYear();
  const companies = await fetchCompanies();
  await allCompanies(companies);
}

function getCurrentAcademicYear() {
  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth();
  let schoolYear;
  if (currentMonth >= 9) {
    schoolYear = `${currentYear}-${currentYear + 1}`;
  } else {
    schoolYear = `${currentYear - 1}-${currentYear}`;
  }
  return schoolYear;

}
async function showPieChart(schoolYear) {
  const totalStudents = await fetchStudentsWithInternship(schoolYear) + await fetchStudentsWithoutInternship(schoolYear);
  const main = document.querySelector('main');
  main.innerHTML += `
            <div class="row w-75 mx-auto">
                <p class="text-center">Année académique : ${schoolYear}</p>
                <p class="text-lg-center">Total : ${totalStudents} étudiants</p>
            </div>
             `;
  const studentsWithInternship = await fetchStudentsWithInternship(schoolYear);
  const studentsWithoutInternship = await fetchStudentsWithoutInternship(schoolYear);
  drawPieChart(studentsWithInternship, studentsWithoutInternship);
}

// async function createDropdownYear() {
//   // todo
//   // add event listener pour chaque option
//
//   // Créer l'élément select
//  /* const select = document.createElement('select');
//   select.id = 'schoolYear';
//
//   // Définir les années académiques
//   const schoolYears = ['2021-2022', '2022-2023', '2023-2024', '2024-2025'];
//
//   // Créer une option pour chaque année académique
//   schoolYears.forEach(year => {
//     const option = document.createElement('option');
//     option.value = year;
//     option.text = year;
//     // Ajouter l'attribut 'selected' à l'année académique 2023-2024
//     if (year === '2023-2024') {
//       option.selected = true;
//     }
//     select.appendChild(option);
//   });
//   // Ajouter l'élément select à la page
//   document.querySelector('main').appendChild(select);
// */
//   const main = document.querySelector('main');
//   let html;
//
//   main.innerHTML = `
//   <select id="schoolYear">
//
//   </select>
//   `;
//   const schoolYears = ['2021-2022', '2022-2023', '2023-2024', '2024-2025'];
//   schoolYears.forEach(sy => {
//     if (sy === '2023-2024') {
//       html += `<option value="${sy}" selected>${sy}</option>`
//     }else {
//       html += `<option value="${sy}">${sy}</option>`
//     }
//   })
//   const div = document.querySelector("#schoolYear");
//   div.innerHTML = html;
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


function drawPieChart(studentsWithInternship, studentsWithoutInternship) {
  const main = document.querySelector('main');
  main.innerHTML += `
            <div class="row w-75 mx-auto">
              <div class="col w-75" >
                <canvas id="myChart"></canvas>
              </div>
            </div>
  `;

  const pieCanvas = document.getElementById('myChart');

  // eslint-disable-next-line
  new Chart(pieCanvas, {
    type: 'pie',
    data: {
      labels: ['Ont un stage', 'Pas de stage'],
      datasets: [{
        data: [studentsWithInternship, studentsWithoutInternship],
        backgroundColor: ['#40234b', '#62a2a4']
      }]
    },
    options: {
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
  const tabDiv = document.createElement('div');
  tabDiv.innerHTML = `
        <table class="table table-bordered mt-5">
          <thead>
            <tr>
              <th scope="col">Nom <button class="sort-button" data-column="tradeName" value="tradeName">&#x25BC;</button><button class="sort-button" data-column="tradeName" value="-tradeName">&#x25B2;</button></th>
              <th scope ="col">Appelation <button class="sort-button" data-column="designation" value="designation">&#x25BC;</button><button class="sort-button" data-column="designation" value="-designation">&#x25B2;</button></th>
              <th scope="col">Numéro de téléphone <button class="sort-button" data-column="meansOfCommunication" value="communication">&#x25BC;</button><button class="sort-button" data-column="meansOfCommunication" value="-communication">&#x25B2;</button></th>
              <th scope="col">Nombre d'étudiants pris en stage <button class="sort-button" data-column="numberOfStudents" value="numberOfStudents">&#x25BC;</button><button class="sort-button" data-column="numberOfStudents" value="-numberOfStudents">&#x25B2;</button></th>
              <th scope="col">Black listée <button class="sort-button" data-column="blackListed" value="blackListed">&#x25BC;</button><button class="sort-button" data-column="blackListed" value="-blackListed">&#x25B2;</button></th>
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

}

export default viewDashBoard;