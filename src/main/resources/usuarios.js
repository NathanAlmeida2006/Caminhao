const API_URL = 'http://localhost:8081/api/usuarios';

const getUsuarios = async () => {
    const response = await fetch(API_URL);
    return await response.json();
};

const addUsuario = async (usuario) => {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(usuario)
    });
    return await response.json();
};

const updateUsuario = async (id, usuario) => {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(usuario)
    });
    return await response.json();
};

const deleteUsuario = async (id) => {
    await fetch(`${API_URL}/${id}`, {
        method: 'DELETE'
    });
};

const form = document.getElementById('usuarioForm');
const usuariosList = document.getElementById('usuariosList');

const clearForm = () => {
    form.reset();
    document.getElementById('usuarioId').value = '';
};

const fillForm = (usuario) => {
    document.getElementById('usuarioId').value = usuario.id;
    document.getElementById('nome').value = usuario.nome;
    document.getElementById('email').value = usuario.email;
    document.getElementById('cargo').value = usuario.cargo;
    document.getElementById('senha').value = '';
};

const createTableRow = (usuario) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${usuario.nome}</td>
        <td>${usuario.email}</td>
        <td>${usuario.cargo}</td>
        <td>
            <button class="btn-edit" onclick="editUsuario(${usuario.id})">Editar</button>
            <button class="btn-delete" onclick="removeUsuario(${usuario.id})">Excluir</button>
        </td>
    `;
    return tr;
};

const loadUsuarios = async () => {
    const usuarios = await getUsuarios();
    usuariosList.innerHTML = '';
    usuarios.forEach(usuario => {
        usuariosList.appendChild(createTableRow(usuario));
    });
};

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const usuario = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        senha: document.getElementById('senha').value,
        cargo: document.getElementById('cargo').value
    };

    const id = document.getElementById('usuarioId').value;

    try {
        if (id) {
            await updateUsuario(id, usuario);
        } else {
            await addUsuario(usuario);
        }
        clearForm();
        await loadUsuarios();
    } catch (error) {
        console.error('Erro ao salvar usuário:', error);
        alert('Erro ao salvar usuário');
    }
});

const editUsuario = async (id) => {
    const usuarios = await getUsuarios();
    const usuario = usuarios.find(u => u.id === id);
    if (usuario) {
        fillForm(usuario);
    }
};

const removeUsuario = async (id) => {
    if (confirm('Tem certeza que deseja excluir este usuário?')) {
        try {
            await deleteUsuario(id);
            await loadUsuarios();
        } catch (error) {
            console.error('Erro ao excluir usuário:', error);
            alert('Erro ao excluir usuário');
        }
    }
};

loadUsuarios();