
# Demo de Health Endpoint Monitoring

## Pasos para ejecutar la demo

1. navega al directorio `health-endpoint-monitoring-demo`.
2. Construye y ejecuta los contenedores con:
   ```
   docker-compose up --build
   ```
3. Accede al microservicio en `http://localhost:3000/health`.
4. Accede a Prometheus en `http://localhost:9090`.
5. Accede a Grafana en `http://localhost:3001` (usuario y contraseña por defecto: `admin`).

## Descripción
Esta demo muestra cómo implementar el patrón de monitoreo de endpoints de salud en un microservicio y monitorearlo con Prometheus y Grafana.
