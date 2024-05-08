import { clearPage } from '../../utils/render';

const modifySubject = async () => {
  clearPage();
  renderForm();
}

function renderForm() {
    const main = document.querySelector('main');
    main.innerHTML += `
    <div class="container">
        <form id="modifySubject-form">
            <div class="form-group">
                <label for="sujet">Sujet</label>
                <input type="text" class="form-control" id="sujet" placeholder="Entrez le sujet">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
    `;

    const form = document.getElementById('modifySubject-form');

    form.addEventListener('submit', saveModification);
}

async function saveModification(e) {
    e.preventDefault();
    const sujet = document.getElementById('sujet').value;
    const urlParams = new URLSearchParams(window.location.search);
    const internshipId = urlParams.get('internshipId');
    console.log('sujet : ', sujet);
    console.log('internshipId : ', internshipId);

    // Vérifiez si le stage existe
    const checkResponse = await fetch(`http://localhost:3000/internship/${internshipId}`);
    if (!checkResponse.ok) {
        alert("Le stage n'existe pas !");
        return;
    }

    const options = {
        method: 'POST',
        body: JSON.stringify({
            sujet,
        }),
        headers: {
            'Content-Type': 'application/json',
        },
    };

    try {
        const response = await fetch(`http://localhost:3000/internship/createOrModify/${internshipId}`,
        options);

    if(response.ok) {
        window.location.href = "/users";
    } else {
        throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
    }
    } catch (error) {
        alert("Le stage n'existe pas !");
        console.log(error);
    }  
}


export default modifySubject;
