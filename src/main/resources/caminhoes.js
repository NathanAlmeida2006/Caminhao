const API_URL = 'http://localhost:8081/api/caminhoes';

const getCaminhoes = async () => {
    const response = await fetch(API_URL);
    return await response.json();
};

const addCaminhao = async (caminhao) => {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(caminhao)
    });
    return await response.json();
};

const updateCaminhao = async (id, caminhao) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(caminhao)
    });
    return await response.json();
};

const deleteCaminhao = async (id) => {
    await fetch(`${API_URL}/${id}`, {
        method: 'DELETE'
    });
};

const form = document.getElementById('caminhaoForm');
const caminhoesList = document.getElementById('caminhoesList');

const clearForm = () => {
    form.reset();
    document.getElementById('caminhaoId').value = '';
};

const fillForm = (caminhao) => {
    document.getElementById('caminhaoId').value = caminhao.id;
    document.getElementById('placa').value = caminhao.placa;
    document.getElementById('modelo').value = caminhao.modelo;
    document.getElementById('cor').value = caminhao.cor;
    document.getElementById('fabricante').value = caminhao.fabricante;
    document.getElementById('numeroChassis').value = caminhao.numeroChassis;
    document.getElementById('capacidadeCarga').value = caminhao.capacidadeCarga;
};

const createTableRow = (caminhao) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${caminhao.placa}</td>
        <td>${caminhao.modelo}</td>
        <td>${caminhao.cor}</td>
        <td>${caminhao.fabricante}</td>
        <td>${caminhao.numeroChassis}</td>
        <td>${caminhao.capacidadeCarga}</td>
        <td>
            <button class="btn-edit" onclick="editCaminhao(${caminhao.id})">Editar</button>
            <button class="btn-delete" onclick="removeCaminhao(${caminhao.id})">Excluir</button>
        </td>
    `;
    return tr;
};

const loadCaminhoes = async () => {
    const caminhoes = await getCaminhoes();
    caminhoesList.innerHTML = '';
    caminhoes.forEach(caminhao => {
        caminhoesList.appendChild(createTableRow(caminhao));
    });
};

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const caminhao = {
        placa: document.getElementById('placa').value,
        modelo: document.getElementById('modelo').value,
        cor: document.getElementById('cor').value,
        fabricante: document.getElementById('fabricante').value,
        numeroChassis: document.getElementById('numeroChassis').value,
        capacidadeCarga: parseFloat(document.getElementById('capacidadeCarga').value)
    };

    const id = document.getElementById('caminhaoId').value;

    try {
        if (id) {
            await updateCaminhao(id, caminhao);
        } else {
            await addCaminhao(caminhao);
        }
        clearForm();
        await loadCaminhoes();
    } catch (error) {
        console.error('Erro ao salvar caminhão:', error);
        alert('Erro ao salvar caminhão');
    }
});

const editCaminhao = async (id) => {
    const caminhoes = await getCaminhoes();
    const caminhao = caminhoes.find(c => c.id === id);
    if (caminhao) {
        fillForm(caminhao);
    }
};

const removeCaminhao = async (id) => {
    if (confirm('Tem certeza que deseja excluir este caminhão?')) {
        try {
            await deleteCaminhao(id);
            await loadCaminhoes();
        } catch (error) {
            console.error('Erro ao excluir caminhão:', error);
            alert('Erro ao excluir caminhão');
        }
    }
};

loadCaminhoes();