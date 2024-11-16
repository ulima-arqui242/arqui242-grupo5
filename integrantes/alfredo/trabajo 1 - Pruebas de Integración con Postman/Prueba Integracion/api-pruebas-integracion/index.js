const express = require('express');
const jwt = require('jsonwebtoken');
const bodyParser = require('body-parser');
const stripe = require('stripe')('sk_test_51Q9euLRrjH8QcF0q5Hqh9Gpt29ofMJDSTB33H2sNn83gEQMwrvXxRq4F8Ja0OQOiVEY6Xvs0XTO5PsyIQwkTqii500d3IszDUs'); // Reemplaza con tu clave secreta de Stripe
const axios = require('axios'); // Para las solicitudes HTTP a Facebook

const app = express();
app.use(bodyParser.json());

const SECRET_KEY = 'mi_clave_secreta';

// Registro de usuario (Simulación)
app.post('/api/register', (req, res) => {
  const { username, password } = req.body;
  if (username && password) {
    res.status(201).json({ message: 'Usuario registrado exitosamente', username });
  } else {
    res.status(400).json({ message: 'Datos inválidos' });
  }
});

// Login de usuario y generación de token
app.post('/api/login', (req, res) => {
  const { username, password } = req.body;
  if (username === 'testUser' && password === 'password123') {
    const token = jwt.sign({ username }, SECRET_KEY, { expiresIn: '1h' });
    res.status(200).json({ token });
  } else {
    res.status(401).json({ message: 'Credenciales inválidas' });
  }
});

// Endpoint protegido
app.get('/api/protected', (req, res) => {
  const token = req.headers['authorization']?.split(' ')[1];
  if (!token) {
    return res.status(403).json({ message: 'Token requerido' });
  }

  jwt.verify(token, SECRET_KEY, (err, user) => {
    if (err) {
      return res.status(403).json({ message: 'Token inválido' });
    }
    res.status(200).json({ message: 'Acceso permitido', user });
  });
});

// Stripe: Endpoint para crear un PaymentIntent
app.post('/create-payment-intent', async (req, res) => {
  const { amount } = req.body;

  try {
    // Crea un PaymentIntent con el monto especificado
    const paymentIntent = await stripe.paymentIntents.create({
      amount: amount,
      currency: 'usd',
      payment_method_types: ['card']
    });

    res.json({ clientSecret: paymentIntent.client_secret });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Facebook: Endpoint para obtener informacion de Facebook
app.post('/facebook-login', async (req, res) => {
   const { access_token } = req.body; // En lugar de recibir el code, recibes el access_token directamente
 
   try {
     // Usar el access_token para obtener la información del usuario
     const userResponse = await axios.get('https://graph.facebook.com/me', {
       params: {
         access_token: access_token,
         fields: 'name,email'
       }
     });
 
     res.json(userResponse.data);
   } catch (error) {
     res.status(500).json({ error: error.message });
   }
 });
 

// Iniciar el servidor
app.listen(3000, () => {
  console.log('Servidor ejecutándose en http://localhost:3000');
});
