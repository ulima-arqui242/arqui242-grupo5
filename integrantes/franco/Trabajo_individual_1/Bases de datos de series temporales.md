# BASES DE DATOS DE SERIES TEMPORALES

## 1. ¿Qué es una base de datos de series temporales?
Una base de datos de series temporales (TSDB, por sus siglas en inglés) está diseñada para almacenar y gestionar datos que varían con el tiempo. Cada registro incluye una **marca de tiempo**, un **valor** y, a menudo, **etiquetas** que añaden contexto. Estas bases de datos permiten analizar rápidamente patrones, detectar anomalías y monitorear eventos en tiempo real, lo cual es ideal para aplicaciones de IoT, análisis financiero y monitoreo de sistemas.

## 2. Características Principales

Las bases de datos de series temporales (TSDB) están diseñadas específicamente para manejar datos que cambian a lo largo del tiempo. Algunas de sus características más importantes son:

- **Consultas optimizadas por tiempo**: Las TSDB permiten realizar búsquedas rápidas y eficientes basadas en intervalos de tiempo específicos. Esto significa que puedes fácilmente recuperar y analizar datos de, por ejemplo, las últimas 24 horas o la última semana, sin ralentizar el sistema.

- **Compresión de datos**: Estas bases de datos utilizan técnicas avanzadas de compresión, como la **codificación por delta**, **compresión RLE (Run-Length Encoding)**, **codificación XOR** y **compresión basada en esquemas**. Estas técnicas permiten almacenar grandes cantidades de datos de manera eficiente, ocupando menos espacio en disco y manejando mejor series de datos que cambian constantemente.

- **Alta velocidad de ingestión de datos**: Las TSDB están optimizadas para recibir datos a gran velocidad y en tiempo real. Esto permite manejar grandes volúmenes de datos que llegan constantemente (por ejemplo, datos de sensores o métricas de rendimiento) sin comprometer el rendimiento del sistema.


## 3. Casos de Uso Comunes
Las bases de datos de series temporales se utilizan en diversos escenarios:
- **Monitorización de infraestructura**: Por ejemplo, seguimiento de métricas de rendimiento como el uso de CPU y memoria en servidores.
- **Internet de las Cosas (IoT)**: Almacenamiento de lecturas de sensores (temperatura, humedad, etc.) en dispositivos distribuidos.
- **Finanzas**: Análisis de precios de acciones y datos de mercado que cambian en intervalos cortos.

## 4. Principales Bases de Datos de Series Temporales

### 4.1. InfluxDB
- **Descripción**: Una de las TSDB más populares, diseñada específicamente para el monitoreo y análisis en tiempo real.
- **Ventajas**: Alta velocidad en la ingestión de datos, consultas optimizadas (InfluxQL), y herramientas de visualización integradas.
- **Uso ideal**: IoT, monitorización de aplicaciones y sistemas.

### 4.2. TimescaleDB
- **Descripción**: Basada en PostgreSQL, añade capacidades de series temporales a una base de datos SQL tradicional.
- **Ventajas**: Soporte completo para SQL, escalabilidad horizontal, y familiaridad para quienes conocen PostgreSQL.
- **Uso ideal**: Aplicaciones que requieren análisis temporal avanzado y beneficios de una base de datos relacional.

### 4.3. Prometheus
- **Descripción**: Muy utilizado en DevOps y sistemas de monitorización, Prometheus recoge métricas en intervalos definidos.
- **Ventajas**: Integración sencilla con sistemas de alerta, adecuado para monitoreo de infraestructura.
- **Uso ideal**: Monitorización de sistemas y aplicaciones en contenedores.

## 5. Comparación Rápida
| Característica       | InfluxDB        | TimescaleDB       | Prometheus     |
|----------------------|-----------------|-------------------|----------------|
| **Consultas**       | InfluxQL        | SQL               | PromQL         |
| **Optimización**    | Alta velocidad  | PostgreSQL + TSDB | Monitorización |
| **Casos de Uso**    | IoT, métricas   | Finanzas, IoT     | DevOps         |
| **Licencia**        | Open-source     | Open-source       | Open-source    |

## 6. Link Demo
[Demo](https://www.youtube.com/watch?v=urZxMHaaRYI)
## 7. Conclusiones
Las bases de datos de series temporales ofrecen una solución eficiente para manejar datos que varían con el tiempo. InfluxDB, TimescaleDB y Prometheus son opciones sólidas para diferentes escenarios y necesidades. Elegir la base de datos adecuada depende del tipo de datos, el volumen y los requisitos de rendimiento específicos de la aplicación.
