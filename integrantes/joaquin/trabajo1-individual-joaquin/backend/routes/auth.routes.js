const { login } = require('../controllers/auth.controller');

module.exports = function(app) {
    app.post('/api/auth/login', login);
};
