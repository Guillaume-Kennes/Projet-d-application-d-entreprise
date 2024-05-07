import { clearPage } from '../../utils/render';
import Navigate from '../Router/Navigate';

const internship = async () => {
  clearPage();
  renderForm();
};

async function renderForm() {
    const main = document.querySelector('main');
    main.innerHTML += 
    `
    <div class="container">
        <form id="internship-form">
            <div class="form-group">
                <label for="sujet">Sujet</label>
                <input type="text" class="form-control" id="sujet" placeholder="Entrez le sujet">
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" class="form-control" id="date">
            </div>
            <div class="form-group">
                <label for="responsable">Responsable</label>
            </div>
            <button type="submit" class="btn btn-primary">Créer un stage</button>
        </form>
    </div>
    `;

    const supervisorsTable = await allSupervisors();
    main.innerHTML += supervisorsTable

    const form = document.getElementById('internship-form');

    form.addEventListener('submit', saveInternship);
}

async function allSupervisors() {
    const main = document.querySelector('main');

    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = await fetch("http://localhost:3000/internshipSupervisor/viewAll", 
        options);
    
    try {
        if (!response.ok) throw new Error(`fetch error :  ${response.status} : ${response.statusText}`)

        const supervisors = await response.json();

        const renderSupervisors = (supervisor) => {
        const supervisorRows = supervisor.map(sup => `
            <tr>
                <td><input type="radio" value="${sup.id}" name="supervisor"></td>
                <td>${sup.firstName}</td>
                <td>${sup.lastName}</td>
                <td>${sup.phoneNumber}</td>
                <td>${sup.email ? sup.email : '/'}</td>
                <td>${sup.company}</td>
            </tr>
            `);

            main.innerHTML += `
            <table class="table table-bordered">
            <thead>
                <tr>
                    <th></th>
                    <th>Prénom</th>
                    <th>Nom</th>
                    <th>Téléphone</th>
                    <th>Email</th>
                    <th>Entreprise</th>
                </tr>
            </thead>
            <tbody>
                ${supervisorRows.join('')}
            </tbody>
            </table>
            <button type="submit" class="btn btn-primary" id="CreateSupervisor">Créer un maître de stage</button>
            <br>
            `;

        };
        // Appeler la fonction renderSupervisors avec les superviseurs obtenus
        renderSupervisors(supervisors);
    } catch (error) {
        console.log("Erreur");
        console.error('Une erreur est survenue : ', error);
    }
}

async function saveInternship(e) {
    e.preventDefault();

    // Récupérer l'ID du superviseur sélectionné
    const supervisorRadios = document.getElementsByName('supervisor');
    let selectedSupervisorId;
    supervisorRadios.forEach((radio) => {
        if (radio.checked) {
            selectedSupervisorId = radio.value;
        }
    });


    const sujet = document.getElementById('sujet').value;
    const date = document.getElementById('date').value;
    const responsable = selectedSupervisorId;
    const urlParams = new URLSearchParams(window.location.search);
    const contactId = urlParams.get('contactId');

    if (!date || !responsable) {
        // Afficher le pop-up si la date ou le responsable est null
        alert("Le champ Date et le Responsable sont obligatoires !");
        return; // Arrêter l'exécution de la fonction
    }

    console.log("sujet : ", sujet);
    console.log("date : ", date);
    console.log("responsable : ", responsable);
    console.log("contactId : ", contactId);
    const button = document.getElementById('CreateSupervisor');
    button.addEventListener("click", () => Navigate(`/createSupervisor?contactId=${contactId}`));
    

    const options = {
        method: 'POST',
        body: JSON.stringify({
            contactId,
            sujet,
            date,
            responsable,
        }),
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = await fetch(`http://localhost:3000/internship/create`, options);

    if(!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
}


export default internship;
