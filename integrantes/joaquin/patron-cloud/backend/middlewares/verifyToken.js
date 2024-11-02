const jwt = require('jsonwebtoken');
const { JWT_SECRET } = require('../config');

module.exports = (req, res, next) => {
    let token = req.headers['authorization'];
    if (!token) return res.status(403).json({ message: 'Token no proporcionado' });

    // Remove 'Bearer ' from token string
    token = token.split(' ')[1];

    jwt.verify(token, JWT_SECRET, (err, decoded) => {
        if (err) return res.status(401).json({ message: 'Token inválido o expirado' });
        req.user = decoded;
        next();
    });
};
