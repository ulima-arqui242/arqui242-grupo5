const express = require('express');
const app = express();
const PORT = process.env.PORT || 3001;

app.get('/users', (req, res) => {
  res.json([{ id: 1, name: 'Wonwoo' }, { id: 2, name: 'Mingyu' }]);
});

app.get('/', (req, res) => {
  res.json({ message: "Ok" });
});

app.listen(PORT, () => console.log(`Servicio de usuarios en el puerto ${PORT}`));
