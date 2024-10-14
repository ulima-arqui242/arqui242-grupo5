const bcrypt = require('bcryptjs');

const users = [
    { id: 1, username: 'admin', password: bcrypt.hashSync('adminpass', 8), role: 'admin' },
    { id: 2, username: 'user', password: bcrypt.hashSync('userpass', 8), role: 'user' }
];

module.exports = users;
