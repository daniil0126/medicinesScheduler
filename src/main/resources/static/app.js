const API_BASE_URL = '/api/v1/medicines';

const cardsContainer = document.getElementById('cards');
const emptyMessage = document.getElementById('emptyMessage');
const modalOverlay = document.getElementById('modalOverlay');
const createForm = document.getElementById('createForm');

const openFormBtn = document.getElementById('openFormBtn');
const addLink = document.getElementById('addLink');

const medicineName = document.getElementById('medicineName');
const medicineDescription = document.getElementById('medicineDescription');
const dailyUsageFrequency = document.getElementById('dailyUsageFrequency');

function openModal(e) {
    if (e) e.preventDefault();

    modalOverlay.hidden = false;
    medicineName.focus();
}

function closeModal() {
    modalOverlay.hidden = true;
    createForm.reset();
}

openFormBtn.addEventListener('click', openModal);
addLink.addEventListener('click', openModal);

async function loadMedicines() {
    try {
        const response = await fetch(API_BASE_URL);

        if (!response.ok) {
            throw new Error(`Ошибка сервера ${response.status}`);
        }

        const medicines = await response.json();

        renderCards(medicines);
    } catch (error) {
        console.error("Ошибка сервера:", error);
    }
}

createForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
        medicineName: medicineName.value,
        medicineDescription: medicineDescription.value,
        dailyUsageFrequency: parseInt(dailyUsageFrequency.value, 10)
    };

    try {
        const response = await fetch(`${API_BASE_URL}/create_medicine`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            throw new Error("Ошибка при создании: " + response.status);
        }

        closeModal();
        await loadMedicines();

    } catch (error) {
        console.error("Ошибка при сохранении:", error);
        alert("Ошибка при сохранении: " + error.message);
    }
});

async function handleTakeMedicine(medId) {
    const card = cardsContainer.querySelector(
        `.med-card[data-id="${medId}"]`
    );

    if (!card) {
        return;
    }

    const button = card.querySelector('.takeBtn');

    button.disabled = true;

    try {
        const response = await fetch(`${API_BASE_URL}/${medId}/take`, {
            method: 'POST'
        });

        if (!response.ok) {
            throw new Error(`Ошибка сервера: ${response.status}`);
        }

        const updatedMedicine = await response.json();

        const taken = updatedMedicine.takenCount;
        const daily = updatedMedicine.dailyUsageFrequency;

        const isCompleted = taken >= daily;
        const progress = Math.min((taken / daily) * 100, 100);

        card.querySelector('.med-count').textContent =
            `${taken} / ${daily}`;

        card.querySelector('.progress-fill').style.width =
            `${progress}%`;

        card.classList.toggle('completed', isCompleted);

        button.disabled = isCompleted;

    } catch (error) {
        console.error("Ошибка при принятии лекарства:", error);

        button.disabled = false;

        alert("Не удалось отметить лекарство как принятое");
    }
}

async function deleteMedicine(medId) {
    const elementToDelete = cardsContainer.querySelector(
        `.med-card[data-id="${medId}"]`
    );

    if (!elementToDelete) {
        return;
    }

    const confirmation = confirm(
        "Вы уверены, что хотите удалить это лекарство?"
    );

    if (!confirmation) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/${medId}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error(
                `Не удалось удалить элемент: ${response.status}`
            );
        }

        elementToDelete.remove();

        const cards = cardsContainer.querySelectorAll('.med-card');

        if (cards.length === 0) {
            emptyMessage.hidden = false;
        }

    } catch (err) {
        console.error("Ошибка при удалении:", err);
        alert("Не удалось удалить лекарство");
    }
}

function renderCards(medicines) {
    cardsContainer
        .querySelectorAll('.med-card')
        .forEach(el => el.remove());

    if (!medicines || medicines.length === 0) {
        emptyMessage.hidden = false;
        return;
    }

    emptyMessage.hidden = true;

    medicines.forEach(med => {
        const name = med.medicineName;
        const desc = med.medicineDescription || '';
        const id = med.medicineId;
        const daily = med.dailyUsageFrequency;
        const taken = med.takenCount;

        const isCompleted = taken >= daily;
        const progress = Math.min((taken / daily) * 100, 100);

        const card = document.createElement("div");

        card.dataset.id = id;
        card.className = `med-card ${isCompleted ? 'completed' : ''}`;

        card.innerHTML = `
            <div class="card-header">
                <span class="med-title">${name}</span>
                <span class="med-count">${taken} / ${daily}</span>
            </div>

            ${desc ? `<div class="med-desc">${desc}</div>` : ''}

            <div class="progress-bar">
                <div
                    class="progress-fill"
                    style="width: ${progress}%;">
                </div>
            </div>

            <div class="card-actions">
                <button
                    class="btn takeBtn"
                    onclick="handleTakeMedicine(${id})"
                    ${isCompleted ? 'disabled' : ''}
                >
                    Принять
                </button>

                <button
                    class="btn deleteBtn"
                    onclick="deleteMedicine(${id})"
                >
                    Удалить
                </button>
            </div>
        `;

        cardsContainer.appendChild(card);
    });
}

document.addEventListener('DOMContentLoaded', loadMedicines);