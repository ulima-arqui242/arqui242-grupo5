const { Pool } = require('pg');

const pool = new Pool({
  user: 'root',
  host: 'localhost',
  database: 'demo',
  port: 26257,
});

module.exports = pool;
