const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const config = require('../config/auth.config');
const users = require('../models/user.model');

exports.login = (req, res) => {
    const { username, password } = req.body;
    const user = users.find(u => u.username === username);
    
    if (!user) {
        return res.status(404).send({ message: 'Usuario no encontrado' });
    }

    // Verifica la contraseña
    const passwordIsValid = bcrypt.compareSync(password, user.password);
    if (!passwordIsValid) {
        return res.status(401).send({ message: 'Contraseña incorrecta' });
    }

    // Genera el token JWT
    const token = jwt.sign({ id: user.id, username: user.username, role: user.role }, config.secret, { expiresIn: 86400 });
    res.status(200).send({

        token: token
    });
};
