const express = require('express');
const app = express();
const { PORT } = require('./config');
const authController = require('./controllers/authController');
const verifyToken = require('./middlewares/verifyToken');

app.use(express.json());

app.post('/login', authController.login);
app.get('/protected', verifyToken, authController.protected);

app.listen(PORT, () => {
    console.log(`Servidor ejecutándose en http://localhost:${PORT}`);
});
