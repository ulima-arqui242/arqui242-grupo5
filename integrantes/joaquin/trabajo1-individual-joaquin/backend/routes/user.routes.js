const { userBoard, adminBoard } = require('../controllers/user.controller');
const { verifyToken, isAdmin } = require('../middlewares/authJwt');

module.exports = function(app) {
    app.get('/api/test/user', [verifyToken], userBoard);
    app.get('/api/test/admin', [verifyToken, isAdmin], adminBoard);
};
