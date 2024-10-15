# Instrucciones para Construir y Ejecutar InfluxDB con Docker

Este proyecto incluye un Dockerfile para construir una imagen de InfluxDB y ejecutarla en un contenedor.

## Requisitos
- **Docker** instalado en tu sistema.

## Instrucciones

### 1. Construir la Imagen de Docker
En el directorio donde se encuentra el archivo `Dockerfile`, ejecuta el siguiente comando para construir la imagen:
```bash
docker build -t my-influxdb .
```
Este comando crea una imagen de Docker llamada my-influxdb basada en las instrucciones del Dockerfile.

### 2. Ejecutar el Contenedor
Una vez que la imagen está construida, usa el siguiente comando para ejecutar el contenedor:
```bash	
docker run -d -p 8086:8086 --name influxdb_container my-influxdb
```

- `-d`: Ejecuta el contenedor en segundo plano (modo detached).
- `-p 8086:8086`: Asigna el puerto **8086** del contenedor al puerto **8086** de tu máquina local.
- `--name influxdb_container`: Asigna el nombre `influxdb_container` al contenedor.
- `my-influxdb`: Nombre de la imagen que acabas de crear.

### Acceder a InfluxDB
Después de ejecutar el contenedor, puedes acceder a InfluxDB en tu navegador en: [http://localhost:8086](http://localhost:8086)
### 3. Ejecutar el Script `experimento.py`
Una vez que el contenedor de InfluxDB está en funcionamiento, puedes ejecutar el script `demo.py` para insertar y consultar datos en InfluxDB.

1. Asegúrate de que el archivo `experimento.py` contiene la configuración correcta para conectarse a InfluxDB (token, org y bucket).
2. En la terminal, navega al directorio donde se encuentra `experimento.py` y ejecuta:
   ```bash
   python experimento.py
   ```