# -*- coding: utf-8 -*-

from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
import random
import time
import datetime

# Configuracion de InfluxDB
token = "e6t4PwnVFD3ENrh6XM0NYCxjfxcF8sQIH-vMfIj4WMv48m9dLhqgPLs1lsIUUf-B1I8Tfb5Ajaaru86_TOtITQ=="
org = "ArquiSoft"
bucket = "demo-bucket"
url = "http://localhost:8086"

client = InfluxDBClient(url=url, token=token, org=org)
write_api = client.write_api(write_options=SYNCHRONOUS)
query_api = client.query_api()

# Genera datos simulados para varias metricas y los envia a InfluxDB
for i in range(10):
    temperature = random.uniform(20.0, 30.0)
    humidity = random.uniform(30.0, 60.0)
    pressure = random.uniform(1010.0, 1025.0)
    
    # Crea puntos para cada metrica
    point_temp = Point("environment").tag("type", "temperature").field("value", temperature).time(datetime.datetime.utcnow())
    point_humidity = Point("environment").tag("type", "humidity").field("value", humidity).time(datetime.datetime.utcnow())
    point_pressure = Point("environment").tag("type", "pressure").field("value", pressure).time(datetime.datetime.utcnow())
    
    # Envia los puntos a InfluxDB
    write_api.write(bucket=bucket, org=org, record=[point_temp, point_humidity, point_pressure])
    
    print(f"Inserted: Temperature={temperature}C, Humidity={humidity}%, Pressure={pressure}hPa")
    time.sleep(1)

# Consulta para calcular el promedio de los ultimos 5 minutos para cada metrica
query = f'''
from(bucket:"{bucket}") 
  |> range(start: -5m) 
  |> filter(fn: (r) => r._measurement == "environment")
  |> group(columns: ["type"])
  |> mean(column: "_value")
  |> yield(name: "mean")
'''
result = query_api.query(org=org, query=query)

print("\nPromedio de los ultimos 5 minutos para cada metrica:")
for table in result:
    for record in table.records:
        print(f"Type: {record['type']}, Average Value: {record['_value']}")
