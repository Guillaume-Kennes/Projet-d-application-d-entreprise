import {clearPage} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";

const companyContacts = async () => {
  clearPage();
  const idCompany = getIdCompanyFromUrl();
  await showContacts(idCompany);
};

async function showContacts(id) {
  const main = document.querySelector('main');
  console.log(getAuthenticatedUser().token);
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: getAuthenticatedUser().token,
    },
  };

  const response = await fetch(`http://localhost:3000/companies/${id}`, options);
  if (!response.ok) {
    throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
  }

  try {
    const contacts = await response.json();

    const renderContacts = (contacts1) => {
      const contactRows = contacts1.map(contact => `
          <tr>
            <td>${`${contact.inscriptionUE.student.firstName} ${contact.inscriptionUE.student.lastName}`}</td>
            <td>${contact.state}</td>
            ${contact.reasonForRefusal ? `<td>${contact.reasonForRefusal}</td>` : `<td>Contact non-refusé</td>`}
            ${contact.meetingPlace ? `<td>${contact.meetingPlace}</td>` : `<td>Contact pas encore pris</td>`}
          </tr>
        `);

      main.innerHTML = `
          <table class="table table-bordered">
            <thead>
              <tr>
                <th scope="col">Etudiant</th>
                <th scope="col">Etat</th>
                <th scope="col">Raison de refus éventuelle</th>
                <th scope="col">Lieu de rencontre éventuel</th>
              </tr>
            </thead>
            <tbody>
              ${contactRows.join('')}
            </tbody>
          </table>
        `;
    };

    renderContacts(contacts);

  } catch (error) {
    console.log("Erreur");
    alert(
        'Vous ne possédez pas les droits pour accéder à cette ressource. Seulement les professeurs ou administratifs peuvent y accéder');
    console.error('Une erreur est survenue : ', error);
  }

}

function getIdCompanyFromUrl() {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get('companyId');
}

export default companyContacts;
