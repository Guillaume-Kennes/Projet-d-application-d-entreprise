import { clearPage } from '../../utils/render';

const viewAllSupervisors = async () => {
  clearPage();
  allSupervisors();
};

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
                <td>${sup.firstName}</td>
                <td>${sup.lastName}</td>
                <td>${sup.phoneNumber}</td>
                <td>${sup.email}</td>
                <td>${sup.tradeName}</td>
                <td>${sup.designation}</td>
            </tr>
            `);

            main.innerHTML = `
            <table class="table table-bordered">
            <tbody>
                ${supervisorRows.join('')}
            </tbody>
            </table>
            `;
        };

        // Appeler la fonction renderSupervisors avec les superviseurs obtenus
        renderSupervisors(supervisors);
    } catch (error) {
        console.log("Erreur");
        console.error('Une erreur est survenue : ', error);
    }
}

export default viewAllSupervisors;
