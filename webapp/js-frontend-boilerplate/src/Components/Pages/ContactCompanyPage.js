/* eslint-disable no-console */
import {
  isAuthenticated
} from '../../utils/auths';
import {clearPage} from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const ContactCompanyPage = () => {
  clearPage();
  if (!isAuthenticated()) {
    // If not authenticated, redirect to the login page
    window.location.href = '/login';
    return; // Prevent further execution of the component
  }
  renderContactCompanyForm();
};

function renderContactCompanyForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.id = 'contactCompanyForm'; // Add an ID for easier styling
  form.className = 'col-lg-4 mx-auto'; // Set the form class to match the fixed HTML structure
  form.style.marginTop = '100px'; // Set the top margin using inline styles

  const name = document.createElement('input');
  name.type = 'text';
  name.name = 'Enterprise_name';
  name.className = 'form-control mb-2';
  name.id = 'knownEnterpriseName';
  name.placeholder = "Nom de l'entreprise";

  const a = document.createElement('a');
  a.textContent = "OU si l'entreprise n'est pas connue";
  a.style.marginBottom = '10px';
  a.style.display = 'block';
  a.style.textAlign = 'center';

  const unknownName = document.createElement('input');
  unknownName.type = 'text';
  unknownName.name = 'Unknown_enterprise_name';
  unknownName.className = 'form-control mb-2';
  unknownName.id = 'unknownEnterpriseName';
  unknownName.placeholder = "Nom de l'entreprise";

  const designation = document.createElement('input');
  designation.type = 'text';
  designation.name = 'Unknown_enterprise_name';
  designation.className = 'form-control mb-2';
  designation.id = 'unknownEnterpriseName';
  designation.placeholder = "Appellation de l'entreprise (si nécessaire)";

  const adress = document.createElement('input');
  adress.type = 'text';
  adress.name = 'Enterprise_name';
  adress.className = 'form-control mb-2';
  adress.id = 'knownEnterpriseName';
  adress.placeholder = "Adresse de l'entreprise";

  const phoneNumber = document.createElement('input');
  phoneNumber.type = 'text';
  phoneNumber.name = 'Enterprise_name';
  phoneNumber.className = 'form-control mb-2';
  phoneNumber.id = 'knownEnterpriseName';
  phoneNumber.placeholder = "Email ou numéro de téléphone de l'entreprise";


  const textCenterDiv = document.createElement('div');
  textCenterDiv.className = 'text-center';

  const submit = document.createElement('button');
  submit.type = 'submit';
  submit.className = 'btn btn-primary btn-block btn-light myButton';
  submit.textContent = 'Envoyer';

  form.appendChild(name);
  form.appendChild(a);
  form.appendChild(unknownName);
  form.appendChild(designation);
  form.appendChild(adress);
  form.appendChild(phoneNumber);
  textCenterDiv.appendChild(submit);
  form.appendChild(textCenterDiv);

  main.appendChild(form);
  form.addEventListener('submit', onSubmit);
}



async function onSubmit(e) {
  e.preventDefault();

  const unknownName = document.querySelector('#unknownName').value;
  const designation = document.querySelector('#designation').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({
      unknownName,
      designation,
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`http://localhost:3000/auths/contact`, options);
  console.log(response);

  if (!response.ok) throw new Error(`fetch error : ${response.status} : ${response.statusText}`);

  const authenticatedUser = await response.json();

  console.log('Authenticated user : ', authenticatedUser);

  // setAuthenticatedUser(authenticatedUser);

  Navbar();

  Navigate('/');
}

export default ContactCompanyPage;