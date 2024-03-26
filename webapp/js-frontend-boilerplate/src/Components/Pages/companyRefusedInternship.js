import * as bootstrap from 'bootstrap';
import { clearPage } from '../../utils/render';

const companyRefusedInternship = async () => {
    clearPage();
    renderForm();
};

function renderForm() {
    const main = document.querySelector('main');
    main.innerHTML +=
        `
        <div class="modal fade" id="contactModal" tabindex="-1" aria-labelledby="contactModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="contactModalLabel" style="color: black">Refus d'un stage</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="mb-3">
                                <label for="contact-name" class="form-label" style="color: black">Nom de l'entreprise</label>
                                <input type="text" class="form-control" id="contact-name">
                            </div>
                            <div class="mb-3">
                                <label for="contact-reason" class="form-label" style="color: black">Quelles sont les raisons du refus de l'entreprise ?</label>
                                <textarea class="form-control" id="contact-reason" rows="3"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                        <button type="button" class="btn btn-primary">Envoyer</button>
                    </div>
                </div>
            </div>
        </div>
    `


    const myModal = new bootstrap.Modal(document.getElementById('contactModal'), {})
    myModal.show();
}


export default companyRefusedInternship;