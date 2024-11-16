// archivo: descuento.test.js

const calcularDescuento = require('./descuento');

test('Debe calcular correctamente el descuento del 10% de un precio de 100', () => {
  const precio = 100;
  const porcentaje = 10;
  const resultadoEsperado = 90;

  const resultado = calcularDescuento(precio, porcentaje);

  expect(resultado).toBe(resultadoEsperado);
});

test('Debe devolver el precio original si el porcentaje es 0', () => {
  const precio = 100;
  const porcentaje = 0;
  const resultadoEsperado = 100;

  const resultado = calcularDescuento(precio, porcentaje);

  expect(resultado).toBe(resultadoEsperado);
});

test('Debe devolver 0 si el porcentaje es 100', () => {
  const precio = 100;
  const porcentaje = 100;
  const resultadoEsperado = 0;

  const resultado = calcularDescuento(precio, porcentaje);

  expect(resultado).toBe(resultadoEsperado);
});

test('Debe lanzar un error si el precio es negativo', () => {
  const precio = -100;
  const porcentaje = 10;

  expect(() => calcularDescuento(precio, porcentaje)).toThrow('Los valores de precio y porcentaje deben ser positivos, y el porcentaje debe estar entre 0 y 100');
});

test('Debe lanzar un error si el porcentaje es mayor a 100', () => {
  const precio = 100;
  const porcentaje = 110;

  expect(() => calcularDescuento(precio, porcentaje)).toThrow('Los valores de precio y porcentaje deben ser positivos, y el porcentaje debe estar entre 0 y 100');
});

test('Debe lanzar un error si el porcentaje es negativo', () => {
  const precio = 100;
  const porcentaje = -10;

  expect(() => calcularDescuento(precio, porcentaje)).toThrow('Los valores de precio y porcentaje deben ser positivos, y el porcentaje debe estar entre 0 y 100');
});
