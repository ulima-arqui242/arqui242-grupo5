
# HEALTH ENDPOINT MONITORING

## 1. Problema

En una arquitectura basada en microservicios, es fundamental garantizar que todos los servicios que componen el sistema estén operando correctamente para mantener la funcionalidad y la calidad del servicio. Sin un mecanismo adecuado de monitoreo, detectar fallos y problemas de rendimiento se convierte en un reto, ya que los equipos de desarrollo y operación deben verificar manualmente el estado de cada servicio. Esta falta de automatización complica la capacidad de respuesta ante incidentes, aumentando el tiempo de inactividad y afectando la experiencia del usuario final. Además, sin un monitoreo adecuado, es difícil obtener información precisa sobre el rendimiento y la salud general del sistema.

## 2. Solución

El patrón Health Endpoint Monitoring propone que cada microservicio implemente un endpoint específico de salud, el cual puede ser interrogado por un sistema de monitoreo central para determinar si el servicio está funcionando correctamente. Este endpoint permite a las aplicaciones de monitoreo enviar solicitudes a cada servicio y evaluar si responden dentro de los parámetros esperados. Los endpoints de salud proporcionan información sobre el estado de la conexión a bases de datos, disponibilidad de servicios externos y otros recursos críticos, permitiendo una detección rápida de fallos y facilitando la toma de decisiones automatizadas o manuales para mitigar problemas.

Los beneficios de este patrón incluyen:

- **Monitoreo centralizado:** La capacidad de unificar la supervisión de todos los servicios a través de un dashboard que muestra el estado de salud de cada uno.
- **Detección temprana de problemas:** Al verificar constantemente los endpoints de salud, se pueden detectar problemas antes de que afecten significativamente al usuario final.
- **Automatización de respuestas:** Integrar un sistema de alerta que notifique a los equipos responsables o inicie mecanismos automáticos de recuperación.
- **Flexibilidad en la implementación:** Permite definir criterios de salud a medida según los requisitos específicos de cada servicio.

El endpoint de salud suele devolver información en formato JSON que indica si el servicio está saludable ("status": "UP") o no ("status": "DOWN"), junto con detalles sobre los chequeos realizados.

> Herramientas como Prometheus, Grafana, New Relic y soluciones de monitoreo en la nube como AWS CloudWatch y Azure Monitor son útiles para implementar y gestionar este patrón.

## 3. Casos de Aplicación

**a. Aplicaciones de banca y finanzas:** Los servicios que procesan transacciones, consultas de saldo y verificaciones de crédito pueden usar endpoints de salud para asegurarse de que están operativos, reduciendo la posibilidad de fallos en operaciones críticas.

**b. Plataformas de streaming de video:** Verificar que los servicios encargados de la distribución de contenido, autenticación de usuarios y control de calidad de video estén activos para mantener una experiencia de usuario fluida.

**c. Comercio electrónico:** Monitorear la disponibilidad de servicios de catálogo, carrito de compras, pagos y logística para asegurar que la plataforma opere sin interrupciones.

## 4. Aplicación en Turi

En el caso de Turi, se ha optado por implementar el patrón Health Endpoint Monitoring para asegurar la operatividad de los microservicios que gestionan funcionalidades críticas como la autenticación de usuarios, la generación de itinerarios y la consulta de recomendaciones. El endpoint de salud para cada microservicio evalúa la conexión a la base de datos, el estado de servicios externos integrados y la carga actual del servicio. La información obtenida se centraliza en un dashboard donde el equipo de soporte técnico puede monitorear el estado general y recibir alertas ante cualquier anomalía.

El flujo de monitoreo es el siguiente:

1. El sistema de monitoreo (por ejemplo, Prometheus) envía solicitudes periódicas a los endpoints de salud de cada microservicio.
2. El endpoint de salud verifica el estado de sus dependencias internas y responde con un JSON indicando el estado del servicio.
3. Las respuestas se recogen y se visualizan en un dashboard de Grafana, que permite ver el historial de estados y enviar alertas si un servicio está en estado "DOWN".
4. En caso de detección de un fallo, se pueden activar mecanismos automáticos de escalado o enviar notificaciones al equipo responsable.

## DEMO - IMPLEMENTACIÓN

### 1. Crear un proyecto para el backend usando Node.js

Primero, se crea una carpeta "backend" y se ejecuta el siguiente comando dentro de dicha carpeta para inicializar el proyecto Node.js:

```
npm init
```

Luego, se instala Express para configurar el backend y los microservicios:

```
npm i express
```

### 2. Crear un microservicio con un endpoint de salud

Dentro del proyecto, se crea un archivo `server.js` con el siguiente código para un microservicio básico que incluya un endpoint de salud:

```javascript
const express = require('express');
const app = express();
const PORT = process.env.PORT || 3000;

app.get('/health', (req, res) => {
    const healthCheck = {
        uptime: process.uptime(),
        message: 'OK',
        timestamp: Date.now()
    };
    try {
        res.status(200).send(healthCheck);
    } catch (error) {
        healthCheck.message = error;
        res.status(503).send(healthCheck);
    }
});

app.listen(PORT, () => {
    console.log(`Service is running on http://localhost:${PORT}`);
});
```

### 3. Configurar Docker para ejecutar el microservicio

Se crea un archivo `Dockerfile` para contenerizar el microservicio:

```dockerfile
FROM node:14
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD [ "node", "server.js" ]
```

### 4. Construir y ejecutar el contenedor

Se ejecutan los siguientes comandos para construir la imagen y levantar el contenedor:

```
docker build -t health-service .
docker run -d --name health-service -p 3000:3000 health-service
```

### 5. Verificar el endpoint de salud

Para verificar que el servicio está funcionando correctamente, se envía una solicitud al endpoint de salud:

```
curl -i http://localhost:3000/health
```

La respuesta debe mostrar un JSON indicando el estado del servicio.

### 6. Integrar Prometheus para monitoreo

Se configura Prometheus para hacer scraping del endpoint de salud:

```yaml
scrape_configs:
  - job_name: 'health-service'
    static_configs:
      - targets: ['host.docker.internal:3000']
```

### 7. Visualizar resultados en Grafana

Se conecta Grafana a Prometheus para visualizar el estado de los microservicios y configurar alertas en caso de fallo.
