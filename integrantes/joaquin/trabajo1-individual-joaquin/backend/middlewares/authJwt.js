const jwt = require('jsonwebtoken');
const config = require('../config/auth.config');

verifyToken = (req, res, next) => {
    let token = req.headers['authorization'];
    
    if (!token) {
        return res.status(403).send({ message: 'Token no proporcionado' });
    }

    token = token.split(' ')[1]; // Extrae el token de "Bearer <token>"
    jwt.verify(token, config.secret, (err, decoded) => {
        if (err) {
            return res.status(401).send({ message: 'Token no válido' });
        }
        req.userId = decoded.id;
        req.userRole = decoded.role;
        next();
    });
};

isAdmin = (req, res, next) => {
    if (req.userRole !== 'admin') {
        return res.status(403).send({ message: 'Acceso denegado, no eres administrador' });
    }
    next();
};

const authJwt = {
    verifyToken,
    isAdmin
};
module.exports = authJwt;
