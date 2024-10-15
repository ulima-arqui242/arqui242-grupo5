# BASES DE DATOS DE SERIES TEMPORALES

## 1. �Qu� es una base de datos de series temporales?
Una base de datos de series temporales (TSDB, por sus siglas en ingl�s) est� dise�ada para almacenar y gestionar datos que var�an con el tiempo. Cada registro incluye una **marca de tiempo**, un **valor** y, a menudo, **etiquetas** que a�aden contexto. Estas bases de datos permiten analizar r�pidamente patrones, detectar anomal�as y monitorear eventos en tiempo real, lo cual es ideal para aplicaciones de IoT, an�lisis financiero y monitoreo de sistemas.

## 2. Caracter�sticas Principales
Las TSDB ofrecen ventajas espec�ficas que las hacen ideales para datos que cambian con el tiempo:
- **Optimizaci�n para consultas de tiempo**: Facilitan b�squedas r�pidas dentro de rangos de tiempo espec�ficos.
- **Compresi�n de datos**: Utilizan t�cnicas avanzadas para reducir el almacenamiento necesario.
- **Alta velocidad en la ingesti�n de datos**: Pueden manejar grandes vol�menes de datos en tiempo real sin afectar el rendimiento.

## 3. Casos de Uso Comunes
Las bases de datos de series temporales se utilizan en diversos escenarios:
- **Monitorizaci�n de infraestructura**: Por ejemplo, seguimiento de m�tricas de rendimiento como el uso de CPU y memoria en servidores.
- **Internet de las Cosas (IoT)**: Almacenamiento de lecturas de sensores (temperatura, humedad, etc.) en dispositivos distribuidos.
- **Finanzas**: An�lisis de precios de acciones y datos de mercado que cambian en intervalos cortos.

## 4. Principales Bases de Datos de Series Temporales

### 4.1. InfluxDB
- **Descripci�n**: Una de las TSDB m�s populares, dise�ada espec�ficamente para el monitoreo y an�lisis en tiempo real.
- **Ventajas**: Alta velocidad en la ingesti�n de datos, consultas optimizadas (InfluxQL), y herramientas de visualizaci�n integradas.
- **Uso ideal**: IoT, monitorizaci�n de aplicaciones y sistemas.

### 4.2. TimescaleDB
- **Descripci�n**: Basada en PostgreSQL, a�ade capacidades de series temporales a una base de datos SQL tradicional.
- **Ventajas**: Soporte completo para SQL, escalabilidad horizontal, y familiaridad para quienes conocen PostgreSQL.
- **Uso ideal**: Aplicaciones que requieren an�lisis temporal avanzado y beneficios de una base de datos relacional.

### 4.3. Prometheus
- **Descripci�n**: Muy utilizado en DevOps y sistemas de monitorizaci�n, Prometheus recoge m�tricas en intervalos definidos.
- **Ventajas**: Integraci�n sencilla con sistemas de alerta, adecuado para monitoreo de infraestructura.
- **Uso ideal**: Monitorizaci�n de sistemas y aplicaciones en contenedores.

## 5. Comparaci�n R�pida
| Caracter�stica       | InfluxDB        | TimescaleDB       | Prometheus     |
|----------------------|-----------------|-------------------|----------------|
| **Consultas**       | InfluxQL        | SQL               | PromQL         |
| **Optimizaci�n**    | Alta velocidad  | PostgreSQL + TSDB | Monitorizaci�n |
| **Casos de Uso**    | IoT, m�tricas   | Finanzas, IoT     | DevOps         |
| **Licencia**        | Open-source     | Open-source       | Open-source    |

## 6. Demo Pr�ctica: Ingreso y Consulta de Datos con InfluxDB

Para esta demo, usaremos **InfluxDB** y **Python** para simular la inserci�n y consulta de datos de temperatura. 

### 6.1 Requisitos Previos
- **InfluxDB**: Instalado localmente o en un contenedor Docker.
- **Python**: Con la librer�a `influxdb-client` instalada.

### 6.2. C�digo de Ejemplo
```python
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
import random
import time
```
# Configuraci�n
```
token = "tu_token"
org = "tu_organizaci�n"
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

### 6.3. Explicaci�n del C�digo
Este c�digo genera datos de temperatura simulados y los env�a a InfluxDB. Cada registro incluye:
- **Nombre de la medici�n**: `temperature`
- **Valor**: Un n�mero aleatorio entre 20 y 30 grados.
- **Tiempo**: La marca de tiempo se asigna autom�ticamente.

### 6.4. Consulta de Datos
Despu�s de insertar los datos, puedes usar el siguiente c�digo para realizar una consulta en InfluxDB:
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
Las bases de datos de series temporales ofrecen una soluci�n eficiente para manejar datos que var�an con el tiempo. InfluxDB, TimescaleDB y Prometheus son opciones s�lidas para diferentes escenarios y necesidades. Elegir la base de datos adecuada depende del tipo de datos, el volumen y los requisitos de rendimiento espec�ficos de la aplicaci�n.
