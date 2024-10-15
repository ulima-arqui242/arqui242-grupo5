# BASES DE DATOS DE SERIES TEMPORALES

## 1. ¿Qué es una base de datos de series temporales?
Una base de datos de series temporales (TSDB, por sus siglas en inglés) está diseñada para almacenar y gestionar datos que varían con el tiempo. Cada registro incluye una **marca de tiempo**, un **valor** y, a menudo, **etiquetas** que añaden contexto. Estas bases de datos permiten analizar rápidamente patrones, detectar anomalías y monitorear eventos en tiempo real, lo cual es ideal para aplicaciones de IoT, análisis financiero y monitoreo de sistemas.

## 2. Características Principales
Las TSDB ofrecen ventajas específicas que las hacen ideales para datos que cambian con el tiempo:
- **Optimización para consultas de tiempo**: Facilitan búsquedas rápidas dentro de rangos de tiempo específicos.
- **Compresión de datos**: Utilizan técnicas avanzadas para reducir el almacenamiento necesario.
- **Alta velocidad en la ingestión de datos**: Pueden manejar grandes volúmenes de datos en tiempo real sin afectar el rendimiento.

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

## 6. Demo Práctica: Ingreso y Consulta de Datos con InfluxDB

Para esta demo, usaremos **InfluxDB** y **Python** para simular la inserción y consulta de datos de temperatura. 

### 6.1 Requisitos Previos
- **InfluxDB**: Instalado localmente o en un contenedor Docker.
- **Python**: Con la librería `influxdb-client` instalada.

### 6.2. Código de Ejemplo
```python
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
import random
import time
```
# Configuración
```
token = "tu_token"
org = "tu_organización"
bucket = "mi_bucket"
url = "http://localhost:8086"
client = InfluxDBClient(url=url, token=token, org=org)
write_api = client.write_api(write_options=SYNCHRONOUS)
```
# Generar y enviar datos
```
for _ in range(10):
    point = Point("temperature").field("value", random.uniform(20.0, 30.0))
    write_api.write(bucket=bucket, org=org, record=point)
    time.sleep(1)
```

### 6.3. Explicación del Código
Este código genera datos de temperatura simulados y los envía a InfluxDB. Cada registro incluye:
- **Nombre de la medición**: `temperature`
- **Valor**: Un número aleatorio entre 20 y 30 grados.
- **Tiempo**: La marca de tiempo se asigna automáticamente.

### 6.4. Consulta de Datos
Después de insertar los datos, puedes usar el siguiente código para realizar una consulta en InfluxDB:
```python
query_api = client.query_api()
query = f'from(bucket:"{bucket}") |> range(start: -1h)'
result = query_api.query(org=org, query=query)

for table in result:
    for record in table.records:
        print(f"Time: {record.get_time()}, Value: {record.get_value()}")
```
## 7. Link Demo
[Demo]()
## 8. Conclusiones
Las bases de datos de series temporales ofrecen una solución eficiente para manejar datos que varían con el tiempo. InfluxDB, TimescaleDB y Prometheus son opciones sólidas para diferentes escenarios y necesidades. Elegir la base de datos adecuada depende del tipo de datos, el volumen y los requisitos de rendimiento específicos de la aplicación.
