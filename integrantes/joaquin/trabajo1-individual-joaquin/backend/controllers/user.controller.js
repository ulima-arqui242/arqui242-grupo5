exports.userBoard = (req, res) => {
    res.status(200).send({ message: 'Datos de usuario autenticado' });
};

exports.adminBoard = (req, res) => {
    res.status(200).send({ message: 'Datos del administrador' });
};
