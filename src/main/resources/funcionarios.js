import { API_URLS } from './config.js';

const getFuncionarios = async () => {
    const response = await fetch(API_URLS.funcionarios);
    return await response.json();
};

const addFuncionario = async (funcionario) => {
    const response = await fetch(API_URLS.funcionarios, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(funcionario)
    });
    return await response.json();
};

const updateFuncionario = async (id, funcionario) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(funcionario)
    });
    return await response.json();
};

const deleteFuncionario = async (id) => {
    await fetch(`${API_URL}/${id}`, {
        method: 'DELETE'
    });
};

const form = document.getElementById('funcionarioForm');
const funcionariosList = document.getElementById('funcionariosList');

const clearForm = () => {
    form.reset();
    document.getElementById('funcionarioId').value = '';
};

const fillForm = (funcionario) => {
    document.getElementById('funcionarioId').value = funcionario.id;
    document.getElementById('nome').value = funcionario.nome;
    document.getElementById('cpf').value = funcionario.cpf;
    document.getElementById('rg').value = funcionario.rg;
    document.getElementById('dataNascimento').value = funcionario.dataNascimento.split('T')[0];
    document.getElementById('cargo').value = funcionario.cargo;
    document.getElementById('email').value = funcionario.email;
    document.getElementById('telefone').value = funcionario.telefone;
};

const createTableRow = (funcionario) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${funcionario.nome}</td>
        <td>${funcionario.cpf}</td>
        <td>${funcionario.cargo}</td>
        <td>${funcionario.email}</td>
        <td>${funcionario.telefone}</td>
        <td>
            <button class="btn-edit" onclick="editFuncionario(${funcionario.id})">Editar</button>
            <button class="btn-delete" onclick="removeFuncionario(${funcionario.id})">Excluir</button>
        </td>
    `;
    return tr;
};

const loadFuncionarios = async () => {
    const funcionarios = await getFuncionarios();
    funcionariosList.innerHTML = '';
    funcionarios.forEach(funcionario => {
        funcionariosList.appendChild(createTableRow(funcionario));
    });
};

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const funcionario = {
        nome: document.getElementById('nome').value,
        cpf: document.getElementById('cpf').value,
        rg: document.getElementById('rg').value,
        dataNascimento: document.getElementById('dataNascimento').value,
        cargo: document.getElementById('cargo').value,
        email: document.getElementById('email').value,
        telefone: document.getElementById('telefone').value
    };

    const id = document.getElementById('funcionarioId').value;

    try {
        if (id) {
            await updateFuncionario(id, funcionario);
        } else {
            await addFuncionario(funcionario);
        }
        clearForm();
        await loadFuncionarios();
    } catch (error) {
        console.error('Erro ao salvar funcionário:', error);
        alert('Erro ao salvar funcionário');
    }
});

const editFuncionario = async (id) => {
    const funcionarios = await getFuncionarios();
    const funcionario = funcionarios.find(f => f.id === id);
    if (funcionario) {
        fillForm(funcionario);
    }
};

const removeFuncionario = async (id) => {
    if (confirm('Tem certeza que deseja excluir este funcionário?')) {
        try {
            await deleteFuncionario(id);
            await loadFuncionarios();
        } catch (error) {
            console.error('Erro ao excluir funcionário:', error);
            alert('Erro ao excluir funcionário');
        }
    }
};

loadFuncionarios();
