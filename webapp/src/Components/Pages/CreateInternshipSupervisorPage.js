import { clearPage } from '../../utils/render';
import Navigate from '../Router/Navigate';

const createSupervisor = async () => {
  clearPage();
  renderForm();
};

function renderForm() {
    const main = document.querySelector('main');
    main.innerHTML += `
    <div class="container">
        <form id="createSupervisor-form">
            <div class="form-group">
                <label for="nom">Nom</label>
                <input type="text" class="form-control" id="name" placeholder="Entrez le nom" required>
            </div>
            <div class="form-group">
                <label for="prenom">Prénom</label>
                <input type="text" class="form-control" id="firstname" placeholder="Entrez le prénom" required>
            </div>
            <div class="form-group">
                <label for="telephone">Numéro de téléphone</label>
                <input type="tel" class="form-control" id="phone" placeholder="Entrez le numéro de téléphone" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" placeholder="Entrez l'email">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
    `;

    const form = document.getElementById('createSupervisor-form');

    form.addEventListener('submit', saveSupervisor);
}

async function saveSupervisor(e) {
  e.preventDefault();
  const name = document.getElementById('name').value;
  const firstname = document.getElementById('firstname').value;
  const phone = document.getElementById('phone').value;
  const email = document.getElementById('email').value;

  const urlParams = new URLSearchParams(window.location.search);
  const companyId = urlParams.get('companyId');
  const contactId = urlParams.get('contactId');

  console.log("name : ", name);
  console.log("firstname : ", firstname);
  console.log("phone : ", phone);
  console.log("email : ", email);
  console.log("companyId :", companyId);
  console.log("contactId :", contactId);

   // Vérifier si les champs obligatoires sont vides
   if (!name || !firstname || !phone) {
    // eslint-disable-next-line no-alert
    alert("Tous les champs obligatoires doivent être remplis !");
    return;
}

  const options = {
    method: 'POST',
    body: JSON.stringify({
      name,
      firstname,
      phone,
      email,
      companyId,
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  try {
    const response = await fetch(`http://localhost:3000/internshipSupervisor/create`, options);

    if(response.ok) {
      // Redirection vers "/createInternship" après un envoi réussi
      Navigate(`/createInternship?companyId=${companyId}&contactId=${contactId}`);
    } else {
      throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
  } catch (error) {
    // Affichage d'un message d'erreur dans un pop-up en cas d'échec
    alert("Une erreur est survenue lors de la création du superviseur !");
    console.error("Erreur :", error);
  }
}

export default createSupervisor;
