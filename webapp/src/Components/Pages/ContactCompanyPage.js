/* eslint-disable no-console */
import {
  getAuthenticatedUser,
  isAuthenticated
} from '../../utils/auths';
import {clearPage} from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import {getToken} from "../../utils/user";


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
  const authenticatedUser = getAuthenticatedUser();
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.id = 'contactCompanyForm'; // Add an ID for easier styling
  form.className = 'col-lg-4 mx-auto'; // Set the form class to match the fixed HTML structure
  form.style.marginTop = '100px'; // Set the top margin using inline styles

  console.log(`authenticatedUser -----> : ${authenticatedUser?.user}`)
  const authenticatedUserId = authenticatedUser?.user?.id;
  console.log(`authenticatedUserId -----> : ${authenticatedUserId}`);
  const prof = authenticatedUser?.user?.role;
  console.log(`---------------> role : ${prof}`);

  const tradeName = document.createElement('input');
  tradeName.type = 'text';
  tradeName.name = 'Enterprise_name';
  tradeName.className = 'form-control mb-2';
  tradeName.id = 'knownEnterpriseName';
  tradeName.placeholder = "Nom de l'entreprise";

  const textCenterDiv1 = document.createElement('div');
  textCenterDiv1.className = 'text-center';

  const submit1 = document.createElement('button');
  submit1.type = 'submit';
  submit1.className = 'btn btn-primary btn-block btn-light myButton';
  submit1.textContent = 'Envoyer';

  const companiesDiv = document.createElement('div');
  companiesDiv.id = 'companiesDiv';


  let companies = [];

// Fetch the list of companies when the page loads
  window.addEventListener('load', async () => {
    const response = await fetch('http://localhost:3000/companies/getEnterprises');
    companies = await response.json();
  });




  const a = document.createElement('a');
  a.textContent = "OU si l'entreprise n'est pas connue";
  a.style.marginBottom = '10px';
  a.style.display = 'block';
  a.style.textAlign = 'center';

  const unknownName = document.createElement('input');
  unknownName.type = 'text';
  unknownName.name = 'Unknown_trade_name';
  unknownName.className = 'form-control mb-2';
  unknownName.id = 'unknownEnterpriseTradeName';
  unknownName.placeholder = "Nom de l'entreprise";

  const designation = document.createElement('input');
  designation.type = 'text';
  designation.name = 'Unknown_designation';
  designation.className = 'form-control mb-2';
  designation.id = 'unknownEnterpriseDesignation';
  designation.placeholder = "Appellation de l'entreprise (si nécessaire)";

  const address = document.createElement('input');
  address.type = 'text';
  address.name = 'Unknown_address';
  address.className = 'form-control mb-2';
  address.id = 'unknownEnterpriseAddress';
  address.placeholder = "Adresse de l'entreprise";

  const city = document.createElement('input');
  city.type = 'text';
  city.name = 'Unknown_city';
  city.className = 'form-control mb-2';
  city.id = 'unknownEnterpriseCity';
  city.placeholder = "Ville de l'entreprise";

  const phoneNumber = document.createElement('input');
  phoneNumber.type = 'text';
  phoneNumber.name = 'Unknown_phone_number';
  phoneNumber.className = 'form-control mb-2';
  phoneNumber.id = 'unknownEnterprisePhoneNumber';
  phoneNumber.placeholder = "Email ou numéro de téléphone de l'entreprise";


  const textCenterDiv = document.createElement('div');
  textCenterDiv.className = 'text-center';

  const submit = document.createElement('button');
  submit.type = 'submit';
  submit.className = 'btn btn-primary btn-block btn-light myButton';
  submit.textContent = 'Envoyer';

  form.appendChild(tradeName);
  form.appendChild(companiesDiv);
  textCenterDiv1.appendChild(submit1);
  form.appendChild(textCenterDiv1);
  form.appendChild(a);
  form.appendChild(unknownName);
  form.appendChild(designation);
  form.appendChild(address);
  form.appendChild(city);
  form.appendChild(phoneNumber);
  textCenterDiv.appendChild(submit);
  form.appendChild(textCenterDiv);

  main.appendChild(form);
  form.addEventListener('submit', onAddCompany);



  tradeName.addEventListener('input', async (event) => {
    console.log('Input changed:', event.target.value);
    const token = getToken();
    // Fetch the list of companies when the input changes
    const response = await fetch('http://localhost:3000/companies/getEnterprises', {
      headers: {
        Authorization: token
      }
    });
    companies = await response.json();

    // Filter the list of companies based on the input value
    const searchTerm = event.target.value.toLowerCase();
    const filteredCompanies = companies.filter(company => company.tradeName.toLowerCase().includes(searchTerm));

    // Clear the current list
    companiesDiv.innerHTML = '';

    // Add the filtered companies to the companiesDiv
    filteredCompanies.forEach(company => {
      const p = document.createElement('p');
      if (company.designation) {
        // If it does, display both the trade name and the designation
        p.textContent = `${company.tradeName} : ${company.designation}`;
      } else {
        // If it doesn't, just display the trade name
        p.textContent = company.tradeName;
      }
      if (company.isBlackListed) {
        // If it is, append a notification to the text content
        p.textContent += ' (Cette entreprise est blacklistée)';
      }
      companiesDiv.appendChild(p);
    });
  });



  submit1.addEventListener('click', async (event) => {
    event.preventDefault();
    const token = getToken();
    try {
      const tradeNameInput = document.getElementById('knownEnterpriseName');
      const enterpriseName = tradeNameInput.value.trim();

      const response = await fetch('http://localhost:3000/contacts/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token
        },
        body: JSON.stringify({
          user: authenticatedUser?.user,
          // userId: authenticatedUserId,
          tradeName: enterpriseName // Send the input value as 'tradeName' field
        })
      });

      if (response.ok) {
        console.log(`authenticatedUserId -----> ${authenticatedUserId}`);
        console.log(`enterpriseName -------> ${enterpriseName}`);
        // Enterprise submission successful, handle response accordingly
        const centerDiv = document.createElement('div');
        centerDiv.classList.add('text-center');

        // Create a success message element
        const successMessage = document.createElement('div');
        successMessage.textContent = `Le contact avec "${enterpriseName}" a été ajouté correctement !`;
        successMessage.classList.add('success-message'); // Add a class for styling if needed

        // Append the success message to the centerDiv
        centerDiv.appendChild(successMessage);

        // Append the centerDiv to the form or any other appropriate container
        form.appendChild(centerDiv);

        // You might want to clear the input field or do any other necessary cleanup
        tradeNameInput.value = '';
      } else {
        // Enterprise submission failed, handle error response
        console.error('Enterprise submission failed:', response.statusText);
      }
    } catch (error) {
      console.error('Error submitting enterprise:', error);
    }
  });

}





async function onAddCompany(e) {
  e.preventDefault();

  const tradeName = document.querySelector('#unknownEnterpriseTradeName').value;
  const designation = document.querySelector('#unknownEnterpriseDesignation').value;
  const address = document.querySelector('#unknownEnterpriseAddress').value;
  const city = document.querySelector('#unknownEnterpriseCity').value;
  const meansOfCommunication = document.querySelector('#unknownEnterprisePhoneNumber').value;
  const token = getToken();

  const options = {
    method: 'POST',
    body: JSON.stringify({
      tradeName,
      designation,
      address,
      city,
      meansOfCommunication,
    }),
    headers: {
      'Content-Type': 'application/json',
      Authorization: token
    },
  };

  try {
    const response = await fetch('http://localhost:3000/companies/add', options);

    if (response.ok) {
      // Create a container div for text center alignment
      const centerDiv = document.createElement('div');
      centerDiv.classList.add('text-center');

      // Create a success message element
      const successMessage = document.createElement('div');
      successMessage.textContent = `L'entreprise "${tradeName}" a été ajoutée correctement !`;
      successMessage.classList.add('success-message'); // Add a class for styling if needed

      // Append the success message to the centerDiv
      centerDiv.appendChild(successMessage);

      // Append the centerDiv to the form or any other appropriate container
      const main = document.querySelector('main');
      main.appendChild(centerDiv);

      // Clear input fields or perform any other necessary cleanup
      document.getElementById('unknownEnterpriseTradeName').value = '';
      document.getElementById('unknownEnterpriseDesignation').value = '';
      document.getElementById('unknownEnterpriseAddress').value = '';
      document.getElementById('unknownEnterpriseCity').value = '';
      document.getElementById('unknownEnterprisePhoneNumber').value = '';

      // You might want to add additional logic here, such as reloading data or updating UI
    } else {
      // Handle error response
      console.error('Company submission failed:', response.statusText);
    }
  } catch (error) {
    console.error('Error submitting company:', error);
  }

  // You can include further logic here as needed
  Navbar();
}


export default ContactCompanyPage;