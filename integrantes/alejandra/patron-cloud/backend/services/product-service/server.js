const express = require('express');
const app = express();
const PORT = process.env.PORT || 3002;

app.get('/products', (req, res) => {
  res.json([{ id: 1, name: 'Attacca' }, { id: 2, name: 'Face The Sun' }]);
});

app.get('/', (req, res) => {
  res.json({ message: "Ok" });
});

app.listen(PORT, () => console.log(`Servicio de productos en el puerto ${PORT}`));
