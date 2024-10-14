// archivo: descuento.js

function calcularDescuento(precio, porcentaje) {
  if (precio < 0 || porcentaje < 0 || porcentaje > 100) {
    throw new Error('Los valores de precio y porcentaje deben ser positivos, y el porcentaje debe estar entre 0 y 100');
  }
  return precio - (precio * (porcentaje / 100));
}

module.exports = calcularDescuento;
