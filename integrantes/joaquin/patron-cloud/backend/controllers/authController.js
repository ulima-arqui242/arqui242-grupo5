const authService = require('../services/authService');
const { JWT_SECRET } = require('../config');
const jwt = require('jsonwebtoken');

exports.login = async (req, res) => {
    const { username, password } = req.body;
    try {
        const user = await authService.login(username, password);
        const token = jwt.sign({ username: user.username }, JWT_SECRET, { expiresIn: '1h' });
        res.status(200).json({ message: 'Login exitoso', token });
    } catch (error) {
        res.status(error.statusCode || 401).json({ message: error.message });
    }
};

exports.protected = (req, res) => {
    res.status(200).json({ message: `Bienvenido, ${req.user.username}. Este es un recurso protegido.` });
};
