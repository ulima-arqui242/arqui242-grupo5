# BASE DE DATOS NEWSQL

## 1. Desarollo conceptual

### **¿Qué es NewSQL?**
NewSQL es una clase de sistemas de gestión de bases de datos relacionales diseñados para proporcionar la escalabilidad de los sistemas NoSQL para cargas de trabajo de lectura y escritura de procesamiento de transacciones en línea a la vez que mantiene las garantías ACID de los sistemas de bases de datos tradicionales relacionales, ofreciendo una solución moderna donde las empresas pueden manejar grandes volúmenes de datos en tiempo real, sin tener que sacrificar la coherencia o la confiabilidad. Estas bases de datos van desde serializables hasta consistencia estricta, ofreciendo un mejor nivel de consistencia que sistemas SQL combinado con una excelente escalabilidad. Además, se diseñó para abordar las limitaciones de las bases de datos relacionales tradicionales cuando se enfrentan a cargas de trabajo de alta concurrencia y grandes volúmenes de datos.

Estas bases de datos son sistemas complejos que consisten en:
1. **Capa de enrutamiento**: Actúa como un director de tráfico, enviando cada solicitud al lugar correcto dentro de la base de datos. Los datos se replican en varios servidores y este componente decide a cuál se debe enviar cada petición, usualmente basándose en un rango.
2. **Capa de cómputo**: Es como un procesador de solicitudes, similar a una API pero más flexible. Puede escalarse de forma independiente ajustándose a la cantidad de trabajo que tenga en cada momento.
3. **Administrador de transacciones**: Es el cerebro de la operación que se encarga de mantener la consistencia de los datos. Coordina todas las lecturas y escrituras, asegurándose de que todo esté siempre actualizado. Para esto, utiliza protocolos especiales que permiten a todos los servidores ponerse de acuerdo sobre el estado actual.
4. **Capa de almacenamiento**: Al ser independiente de la capa de cómputo, el almacenamiento puede escalarse de forma separada. En lugar de discos locales, se utilizan sistemas distribuidos que gestionan la replicación y el crecimiento de los datos de manera autónoma.
5. **Registro de transacciones**: Es como un diario donde se anota cada cambio que se realiza en la base de datos. Este registro se distribuye entre todos los servidores, asegurando que todos tengan la misma versión de la historia.
6. **Trabajos en segundo plano**: Son tareas de mantenimiento que se ejecutan de forma continua, como optimizar el espacio de almacenamiento, dividir los datos en partes más pequeñas o ajustar automáticamente la capacidad de ciertos componentes.

### **Ventajas**
- **Escalabilidad horizontal**: Pueden crecer agregando más nodos al clúster, lo que permite manejar grandes volúmenes de datos y altas tasas de transacciones.
- **Alto rendimiento**: Optimizadas para manejar cargas de trabajo de alta concurrencia y ofrecer tiempos de respuesta bajos.
- **Flexibilidad**: Pueden adaptarse a diferentes tipos de datos y modelos de datos al igual que las bases de datos NoSQL.
- **Consistencia**: Garantizan la consistencia de los datos a través de mecanismos de consenso y réplica al igual que las bases de datos SQL.
- **Transaccionalidad**: Soporta transacciones ACID, lo que es fundamental para muchas aplicaciones empresariales.
- **Soporte SQL**: Ofrecen un lenguaje de consulta similar a SQL, lo que facilita la migración y el desarrollo de aplicaciones.

### **Desventajas**
- **Alta latencia**: Suelen tener una latencia más alta que las NoSQL debido a que requieren el uso de un protocolo de consenso, lo que requiere tiempo adicional.
- **Tecnologías complejas**: Su funcionamiento interno de las bases de datos implica conceptos complejos como protocolos de consenso, transacciones distribuidas, almacenamiento distribuido y registros distribuidos. Estos requieren conocimientos y experiencia adicionales.
- **Falta de popularidad**: Bases de datos de código abierto como TiDB, YDB y CockroachDB son menos populares que las bases de datos SQL o NoSQL tradicionales. En consecuencia, resulta complicado encontrar ingenieros cualificados y familiarizados con este tipo de bases de datos.
- **Complejidad:** Su arquitectura distribuida y la necesidad de gestionar réplicas y consenso hacen que sean sistemas más complejos de administrar y configurar que las bases de datos relacionales tradicionales.
- **Madurez:** Han evolucionado rápidamente, pero algunas tecnologías NewSQL aún se consideran relativamente nuevas y pueden carecer de la madurez y soporte.

### **Casos de uso**
NewSQL es una solución intermedia que combina lo mejor de los mundos SQL y NoSQL, siendo ideal para aplicaciones que demandan altas transacciones y consistencia como banca y e-commerce. Además, facilita el análisis de grandes volúmenes de datos gracias a su escalabilidad y soporte para consultas SQL avanzadas. Para decidir si utilizar este tipo de motores de base de datos, se debe tener en cuenta que los mejores casos para utilizarlos son si se requieren:
- **Alto rendimiento y baja latencia** como en sistemas de recomendación y juegos en línea.
- **Escalabilidad horizontal** como en aplicaciones con grandes volúmenes de datos y un gran número de usuarios concurrentes.
- **Transaccionalidad y consistencia** en el caso de aplicaciones de banca, sistemas de reservas, e-commerce, etc.
- **Flexibilidad** en aplicaciones que requieren adaptarse a diferentes tipos de datos y modelos de datos.

## 2. Consideraciones técnicas

Para efectos de esta demo, se utilizará CockroachBD como base de datos NewSQL. Es importante considerar también que todo el desarrollo se hará usando el sistema operativo Windows.

### 1. Instalación de CockroachBD
Se instalará la versión 24.2.4 de CockroachBD al ser la última versión estable utilizable. Para ello, se puede descargar en el siguiente enlace:
https://www.cockroachlabs.com/docs/releases/v24.2#v24-2-4

Al estar en Windows, se descargará el siguiente .zip con el ejecutable al que se debe acceder posterioramente mediante línea de comando:  

![Download  CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/download.png)

Se debe descomprimir la carpeta .zip en donde se quiera colocar el ejecutable de CockroachBD, considerar que en dicha ruta se va a acceder para interactuar con la base de datos, así que procurar que esté en un carpeta con permisos.

### 2. Instalar NodeJS
Se necesitará NodeJS para el pequeño backend que se armará para la demo. Se puede instalar la última versión de Node en el siguiente enlace:
https://nodejs.org/en

Para esta demo, se utilizará la versión ```v21.5.0```

### 3. Iniciar un nodo de CockroachBD

Primero, se debe hacer ```cd``` a la carpeta en donde se encuentra el ```cockroach.exe```. Luego, se debe ejecutar el siguiente comando para inicializar un único nodo de CockroachBD:

```powershell
.\cockroach.exe start-single-node --insecure --listen-addr=localhost:26257 --store=cockroach-data
```
![Initialization  CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/initialization.png)

Una vez se ha inicializado el nodo, no se debe cerrar dicha cmd.

### 4. Abrir el cliente SQL de CockroachBD

Una vez inicializado, se debe abrir el cliente SQL en el puerto de localhost que se definió en el primer comando. En este caso, se usa el puerto ```26257``` que es el puerto de CockroachBD por defecto. Para correr el siguiente comando, abrir otro cmd aparte del que tiene la inicialización ejecutándose.

```powershell
.\cockroach.exe sql --insecure --host=localhost:26257
```

![Open SQL Client  CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/open-sql-client.png)  

### 5. Crear la base de datos 

Dentro de la misma línea de comando que se ha ejecutado en el paso previo, dentro del localhost:26257 se debe ejecutar el siguiente comando para la creación de la base de datos a utilizar, en este caso se llamará ```demo``` y también se creará la tabla ```users``` que contendrá los siguientes datos:
1. id
2. name
3. email
4. created_at (fecha y hora de creación)

```sql
CREATE DATABASE demo;
USE demo;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name STRING NOT NULL,
    email STRING NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT current_timestamp()
);

```

![Create BD and User table CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/create-bd-table.png)  

### 6. Crear backend
Se debe crear primero una carpeta en donde se quiere crear el proyecto del backend. En mi caso, la carpeta se llama "backend". Dentro de esta carpeta se debe correr el siguiente comando para inicializar un proyecto de NodeJS:

```
npm init -y
```

Luego, considerar que se debe instalar Express y PG (node-postgres), así que dentro del proyecto ya creado, se debe correr el siguiente comando:
```
npm install express pg
```

### 7. Crear conexión a la BD

Utilizando 'Pool' de la librería ```pg``` se crea una instancia de pool de la base de datos que creamos pasos adelante llamada ```demo```, además de indicar el puesto y host que serían ```26257``` y ```localhost``` como también ya se indicó en pasos anteriores

Nombre del archivo: ```db.js```

```js
const { Pool } = require('pg');

const pool = new Pool({
  user: 'root',
  host: 'localhost',
  database: 'demo',
  port: 26257,
});

module.exports = pool;
```

### 8. Iniciar el servidor y endpoints

Para poder iniciar el servidor del pequeño backend creado en el puerto ```3000```, se debe agregar la siguiente porción de código en un archivo ```server.js```

```js
const express = require('express');
const pool = require('./db');

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto ${PORT}`);
});
```

Luego, se pueden crear endpoints dentro del mismo archivo para:
1. Crear usuario
2. Ver todos los usuarios
3. Editar datos de un usuario
4. Eliminar un usuario
   
```js
// Crear usuario
app.post('/users', async (req, res) => {
  const { name, email } = req.body;
  try {
    const result = await pool.query(
      'INSERT INTO users (name, email) VALUES ($1, $2) RETURNING *',
      [name, email]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
});

// Traer todos los usuarios
app.get('/users', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM users');
    res.json(result.rows);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Actualizar usuario
app.put('/users/:id', async (req, res) => {
  const { id } = req.params;
  const { name, email } = req.body;
  try {
    const result = await pool.query(
      'UPDATE users SET name = $1, email = $2 WHERE id = $3 RETURNING *',
      [name, email, id]
    );
    res.json(result.rows[0]);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
});

// Eliminar usuario
app.delete('/users/:id', async (req, res) => {
  const { id } = req.params;
  try {
    await pool.query('DELETE FROM users WHERE id = $1', [id]);
    res.status(204).json({message: 'User deleted successfully'});
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});
```

Una vez creado el backend, levantamos en servidor con el siguiente comando dentro de la carpeta donde se enceuntra el archivo .js creado: 
```
node server.js
```

### 9. Consultas de prueba

Una vez creado el servicio, podemos realziar operaciones en la base de datos. Por ejemplo, para agregar nuevos usuarios podemos mediante Postman ejecutar el siguiente POST:

![Add user CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/add-user.png)

También podemos consultar los usuarios creados con la siguiente petición GET:  

![All users CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/all-users.png)

Otra operación es la de actualización de algún dato de uno de los usuarios que tenemos creados en la BD con la operación PUT:  

![Update user CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/update-user.png)

Por último, podemos realizar una operación DELETE para eliminar con el id a algún usuario de la BD:  

![Delete user CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/delete-user.png)


## PERO, ¿CÓMO PODEMOS AGREGARLE DISTRIBUCIÓN Y/O RÉPLICAS CON DIFERENTES NODOS?

Para poder incluir replicaciones y distribución entre nodos de CockroachBD, se debe crear un nodo multi-clúster. Para ello, hay que seguir los siguientes pasos:

### 10. Configurar el clúster multi-nodo

Primero se necesita configurar múltiples instancias de CockroachDB en diferentes puertos para efectos de esta demo en entorno local. Para ello, de manera similar al **paso 3** se debe inicializar un nodo de CockroachBD y asignarlo a un puerto y localhost diferente. En este caso, se crearán 3 nodos de la siguiente manera:

En una terminal dentro de la carpeta donde se encuentra el archivo ```cockroach.exe``` previamente descomprimido, se debe correr el siguiente comando para crear un nodo que será el ```node1```

**Terminal 1:**
```powershell
.\cockroach.exe start --insecure --listen-addr=localhost:26257 --http-addr=localhost:8080 --store=node1 --join=localhost:26257
```

Una vez creado el ```node1```, es un paso muy importante el inicializar el clúster y puesto que el resto de nodos están enlazados con ```node1```, se debe iniciar el host dentro del ```localhost:26257```destinado al nodo 1. Para ello, se debe abrir otra terminal dentro de la carpeta donde está el archivo ```cockroach.exe``` y ejecutar el siguiente comando:
```powershell
.\cockroach.exe init --insecure --host=localhost:26257
```

Se repite el mismo paso para los nodos ```node2``` y ```node3```, cada uno en una terminal diferente.
> NO SE REPITE LA INICIALIZACIÓN DEL CLÚSTER

**Terminal 2:**
```powershell
.\cockroach.exe start --insecure --listen-addr=localhost:26258 --http-addr=localhost:8081 --store=node2 --join=localhost:26257
```

**Terminal 3:**
```powershell
.\cockroach.exe start --insecure --listen-addr=localhost:26259 --http-addr=localhost:8082 --store=node3 --join=localhost:26257

```

> **IMPORTANTE:** No cerrar las terminales o se terminará la inicialización de los nodos

Una vez se crearon los nodos, se puede verificar el clúster con el siguiente comando dentro de una terminal aparte de las que se usan para levantar los nodos:

```powershell
.\cockroach.exe node status --insecure --host=localhost:26257
```
![Clúster CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/cluster.png)  


### SE PUEDE VER LA CONSOLA DE ADMINISTRACIÓN DE CockroachDB DESDE EL NAVEGADOR
http://localhost:8080/#/overview/list  

![Administrador CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/vista-administrador.png)  


### 11. Verificar la configuración de distribución en la BD

CockroachDB distribuye automáticamente los datos en rangos y réplicas. Para ello, se debe seguir la siguiente configuración inicial de la BD BD. Primero, igual que en el **paso 4** se debe abrir la conexión con el cliente SQL para poder realizar operaciones SQL en la BD.

```powershell
.\cockroach.exe sql --insecure --host=localhost:26257
```

Luego, se debe crear la BD ```demo``` y la tabla ```users``` como en el paso **paso 5**.

```sql
CREATE DATABASE demo;
USE demo;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name STRING NOT NULL,
    email STRING NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT current_timestamp()
);
```

Una vez ya se hizo la configuración inicial, se puede verificar la distribución actual en la tabla ```users``` con el siguiente comando dentro del cliente SQL:
```sql
SHOW RANGES FROM TABLE users;
```
![Verificación inicial de distribución CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/distribucion1.png)

Por defecto, vemos que CockroachBD maneja 3 réplicas. Sin embargo, podemos hacer ajustes para cambiar dicho número de la siguiente manera:
```sql
ALTER RANGE default CONFIGURE ZONE USING num_replicas = 5;
```  

Ejecutando el siguiente comando podemos ver que se cambiaron el número de réplicas por default:  

```sql
SHOW ZONE CONFIGURATION FOR RANGE default;
```
![Default num_replicas CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/replicas-changed.png)  


### 12. Verificar la distribución entre nodos

Así como iniciamos el cliente SQL en el ```node1``` con el puerto ```26257```, podemos inicializar el cliente para el ```node2```:

```powershell
.\cockroach.exe sql --insecure --host=localhost:26258
```

Ahora, podemos comprobar que tanto en el ```node1``` como en el ```node2``` se están manejando los mismos datos en la tabla de ```users```. La consulta se hace de la siguiente manera para ambos casos:

```sql
SELECT * FROM demo.users;
```

Podemos ver que en ambos nodos se está manejando la misma información:

## node1
![Users en node1 CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/users-node1.png) 

## node2
![Users en node2 CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/users-node2.png)

### 13. Verificar tolerancia a fallos

Para probar que el clúster puede tolerar fallos, probemos deteniendo el proceso del ```node2```. Para ello, ```ctrl + c``` dentro de la terminal que tiene el nodo inicializado para detener su ejecución.

Podemos ver que al momento de tratar de consultar por los usuarios en el ```node2```, este ya no se encuentra activo por lo que salta un error:
![node2 lost CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/node2-lost.png)

Sin embargo, si verificamos el ```node1``` y ```node3```, estos siguen activos y funcionando sin ningún problema, por lo que se puede ejecutar correctamente la petición SQL para ver todos los usuarios en la tabla:

### node1
![node1 CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/node1-when-node2-unabled.png)

### node3
![node3 CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/node3-when-node2-unabled.png)


Ahora, si reiniciamos el ```node2``` podemos ver que se reequilibra automáticamente para tener la misma información que el resto de nodos. Para ello, antes de reiniciar el nodo 2 se agregará un nuevo usuario.  

![New user without node2 CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/new-user-.without-node2.png)

A continuación, reiniciamos el ```node2``` y consultamos por la tabla ```users```. Como podemos ver en la foto, se requilibró la data correctamente al nodo pese a que estuvo de baja al momento de agregar al nuevo usuario. Con esto podemos comprobar que se está haciendo una correcta replicación de la data entre los 3 nodos creados.  

![Reinitialize node2 CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/node2-after-reinitialization.png)

### EXTRA: Crear backup


```sql
BACKUP TO 'nodelocal://1/backups/users_backup';
```

![Create backup CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/create-backup.png)  

Vemos que se creó correctamente el backup dentro del nodo 1:  

![Created backup CockroachBD](/integrantes/alejandra/trabajo-individual-2/images/created-backup.png)


## 3. Demo

Link de la demo: https://youtu.be/OHXGO_Jic-k

### Fuentes
- https://amarchenko.dev/blog/2024-03-13-new-sql/
- https://www.dremio.com/wiki/newsql/
- https://medium.com/@m15318368191/what-is-newsql-18c8cabdc66
- https://www.deloitte.com/es/es/services/consulting/blogs/todo-tecnologia/del-SQL-NoSQLl-hasta-modernas-bases-datos-newSQL.html#:~:text=NewSQL%20busca%20principalmente%20proporcionar%20herramientas,consistencia%20y%20usabilidad%20de%20SQL
- https://www.linkedin.com/pulse/bases-de-datos-sql-vs-nosql-newsql-jos%C3%A9-tello-villalobos/
- https://www.cockroachlabs.com/