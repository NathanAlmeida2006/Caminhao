const API_URL = 'http://localhost:8081/api/usuarios/login';

const loginForm = document.getElementById('loginForm');
const errorMessage = document.getElementById('errorMessage');

loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const credentials = {
        email: document.getElementById('email').value,
        senha: document.getElementById('senha').value
    };

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token);
            window.location.href = 'index.html';
        } else {
            errorMessage.textContent = 'Email ou senha inválidos';
        }
    } catch (error) {
        errorMessage.textContent = 'Erro ao conectar ao servidor';
        console.error('Erro:', error);
    }
});