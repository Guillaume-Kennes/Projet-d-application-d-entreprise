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
    console.log('sujet : ', sujet);

    const options = {
        method: 'POST',
        body: JSON.stringify({
            sujet,
        }),
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = await fetch(`http://localhost:3000/internship/createOrModify`,
        options);

if(!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);
}









export default modifySubject;