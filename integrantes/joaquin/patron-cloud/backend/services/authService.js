const bcrypt = require('bcryptjs');
const circuitBreaker = require('../utils/circuitBreaker');

const mockUser = {
    username: 'usuarioEjemplo',
    password: bcrypt.hashSync('contraseñaSegura', 10) // Contraseña encriptada
};

async function authenticate(username, password) {
    if (username === mockUser.username && await bcrypt.compare(password, mockUser.password)) {
        return { username: mockUser.username };
    } else {
        throw new Error('Credenciales incorrectas');
    }
}

exports.login = (username, password) => {
    return circuitBreaker.fire(authenticate, username, password);
};
