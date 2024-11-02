const CircuitBreaker = require('opossum');

const circuitBreakerOptions = {
    timeout: 5000,
    errorThresholdPercentage: 50,
    resetTimeout: 10000
};

const breaker = new CircuitBreaker((fn, ...args) => fn(...args), circuitBreakerOptions);

breaker.on('open', () => console.log('Circuito abierto: No se permite el login temporalmente.'));
breaker.on('halfOpen', () => console.log('Circuito semiabierto: Probando el servicio de login.'));
breaker.on('close', () => console.log('Circuito cerrado: El servicio de login está disponible nuevamente.'));

module.exports = breaker;
