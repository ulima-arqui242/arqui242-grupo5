# GATEWAY ROUTING

## 1. Problema

En el contexto de una arquitectura de microservicios, se suelen tener múltiples servicios de backend que se especializa en manejar parte de las funcionalidades del sistema. Para ello, se requiere de un mecanismo central de gestión de las interacciones del front con los servicios porque, en caso de no tenerlo, los clientes que consumen dichos servicios tienen que conocer los detalles de todos los endpoints, lo que aumenta la complejidad de mantenimiento, la escalabilidad y flexibilidad en cambios de la arquitectura. Además, puede darse el caso de que se requieran diferentes formas de autenticación o transformaciones de datos para algunos servicios en específico, lo cual complicaría las interacciones y generaría en muchos casos sobrecarga en cada uno de los servicios para manejar tareas que son repetitivas.

## 2. Solución

Gateway Routing propone una solución creando un punto único de acceso a través de un API Gateway, la cual actúa como intermediario entre el cliente y los microservicios. Su principal responsabilidad es enrutar las solicitudes al servicio adecuado basándose en rutas definidas mediante los endpoints, por reglas de negocio, encabezados o parámetros de la solicitud. Además, permite aplicar políticas de seguridad como autenticación, balanceo de carga, transformación de datos y gestión de tráfico de las solicitudes.

El patrón ofrece una desacoplamiento entre los clientes y los microservicios backend, ya que los clientes no necesitan conocer las direcciones específicas de cada servicio, lo cual mejora la flexibilidad para actualizar y cambiar servicios sin afectar la experiencia del usuario. También centraliza funciones como autenticación (políticas de seguridad en capas mediante OAuth2, JWT o API Keys), validación de esquemas, compresión de datos, conversión de formatos de respuesta (JSON a XML) y transformación de request/response. Esto reduce la duplicación de código, ya que estos mecanismos no tienen que implementarse en cada microservicio.

Otra ventaje de utiliza una API Gateway es que esta puede optimizar la distribución de tráfico entre instancias de microservicios, incluir políticas de balanceo de carga y hacer caching de respuestas frecuentes, de esta forma se logra un uso más eficiente de los recursos y mejora la capacidad de respuesta de los microservicios.

Finalmente, otro punto importante es que mantiene registro de información detallada sobre cada solicitud como el tiempo de respuesta, el estado y las rutas recorridas (incluyendo las más comúnmente usadas). Esto facilita la detección de problemas en tiempo real mediante métricas, esta información se puede obtener mediante un sistema de logs.  

> Existen plataformas como Kong, AWS API Gateway, NGINX, y Azure API Management que ofrecen soluciones para implementar este patrón.

## 3. Casos de Aplicación

**a. E-commerce:** En una tienda online con múltiples servicios como el de gestión de productos, carrito de compras, sistema de pagos y módulo de recomendaciones de productos, el API Gateway puede enrutar las solicitudes de productos al microservicio de catálogo, las solicitudes de pagos al módulo de transacciones y las de recomendaciones a un servicio basado en IA.  

**b. Bancos y Fintechs:** Pueden enrutar solicitudes hacia microservicios que manejan pagos, transferencias y consultas de saldo de la cuenta, cada uno con reglas de autenticación específicas según lo que requiera el negocio.

## 4. Aplicación en Turi

En el caso de Turi se ha planteado utilizar Kong como API Gateway para el enrutamiento de solicitudes a los microservicios. Este permite tener una arquitectura extensible mediante plugins que gestionan desde la autenticación hasta el monitoreo, lo que lo convierte en una solución escalable y flexible para manejar arquitecturas de microservicios.

En el diagrama de Mapeo de elementos se pueden ver los diferentes servicios que Turi está manejando  

![Mapeo de elementos](/3/3.4/MapeoEntreElementos.png)

En lugar de hacer peticiones dirigiéndose directamente a los microservicios, Kong actúa como por intermediario que enruta al servicio backend adecuado. El flujo para ello sería el siguiente:
1. El cliente realiza una solicitud HTTP/HTTPS a Kong.
2. Kong examina la solicitud y en base a las rutas definidas, la redirige al servicio backend adecuado.
3. Kong puede aplicar autenticación, validación o transformación a la solicitud.
4. Una vez procesada, la solicitud se reenvía al backend correspondiente, que responde a través de Kong de vuelta al frontend.
5. **ADICIONAL:** Kong puede aplicar transformaciones adicionales a la respuesta antes de enviarla de vuelta al cliente.

## DEMO - IMPLEMENTACIÓN

Para esta demo se utilizará Kong como API Gateway utilizando Docker, PostgreSQL para guardar sus configuraciones y Node.js para la creación de los microservicios de prueba.


### 1. Crear un proyecto para el backend usand Node.js

Primero, se creó una carpeta "backend" y luego se ejecutó el siguiente comando dentro de dicha carpeta para crear el proyecto Node

```
npm init
```

Luego, se instaló Express, el cual se utilizará para configurar el backend y todos los microservicios a utilizar en esta demo. Para ello, se utilizó el siguiente comando:

```
npm i express
```

Finalmente, tenemos el proyecto base creado:  

![Paso 1](/integrantes/alejandra/patron-cloud/images/paso1.png)

### 2. Configurar una red en Docker para poder utilizar Kong
```
docker network create kong-net
```

### 3. Iniciar un contenedor en PostgreSQL y configurarlo
Se define un usuario, el nombre de la BD y una contraseña con el siguiente comando:
```
docker run -d --name kong-database --network kong-net -p 5432:5432 -e POSTGRES_USER=kong -e POSTGRES_DB=kong -e POSTGRES_PASSWORD=kong postgres:latest
```
Luego, se realiza una configuración donde
- **KONG_DATABASE:** El tipo de DB a utilizar
- **KONG_PG_HOST:** El nombre del contenedor de PostgreSQL en Docker que se comunica con el "kong-net" network
- **KONG_PG_PASSWORD:** La contraseña que se seteó en el paso anterior para el contenedor de PostgreSQL

También se podría agregar la siguiente información en el caso de ser un _enterpise_:
- **KONG_PASSWORD:** Contraseña del administrador con el SuperUsuario para la Gateway con Kong.

```
docker run --rm --network kong-net -e KONG_DATABASE=postgres -e KONG_PG_HOST=kong-database -e KONG_PG_PASSWORD=kong kong kong migrations bootstrap
```
### 4. Iniciar Kong

```
docker run -d --name kong --network kong-net -e KONG_DATABASE=postgres -e KONG_PG_HOST=kong-database -e KONG_PG_PASSWORD=kong -e KONG_PROXY_ACCESS_LOG=/dev/stdout -e KONG_ADMIN_ACCESS_LOG=/dev/stdout -e KONG_PROXY_ERROR_LOG=/dev/stderr -e KONG_ADMIN_ERROR_LOG=/dev/stderr -e KONG_ADMIN_LISTEN=0.0.0.0:8001 -p 8000:8000 -p 8443:8443 -p 8001:8001 -p 8444:8444 kong
```

## CONTENEDORES CREADOS Y ACTIVOS EN DOCKER DESKTOP:
![Containers](/integrantes/alejandra/patron-cloud/images/containers.png)

### 5. Crear los proyectos para los microservicios
Luego de inciar Kong, se crearon 2 microservicios básicos en el proyecto del backend utilizando Express. Estos microservicios creados fueron "user-service" y "product-services" para efecto de la demo.

Primero, después de crear las carpetas dentro de "backend" para cada uno de los microservicios, se procede a crear un proyecto de Node.js dentro de cada una. Por ejemplo, dentro de la carpeta de "user-service" se deben correr los siguientes comandos para crear el proyecto e instalar Express.
```
npm init -y
npm i express
```
El proceso se debería repetir para cada uno de los microservicios que utilizarán la misma tecnología.

![Crear proyecto de microservicios](/integrantes/alejandra/patron-cloud/images/microservicios-proyectos.png)

### 6. Crear los microservicios y endpoint básico

Dentro de cada microservicios se creó un archivo ```server.js``` donde se realizó una configuración básica para utilizar puertos diferentes para cada microservicios y una ruta básica para un endpoint pequeño de prueba

### user-service:  
![User service code](/integrantes/alejandra/patron-cloud/images/user-service.png)

### products-service:  

![Products service code](/integrantes/alejandra/patron-cloud/images/products-service.png)

Finalmente, dentro de los respectivos directorios se debe ejecutar el siguiente comando para verificar que están ejecutándose los microservicios en sus respectivos puertos locales correctamente:

```
node server.js
```

#### Resultado:  

![Microservices localhost working](/integrantes/alejandra/patron-cloud/images/localhost-microservices.png)

### 7. Agregar los servicios a Kong

Una vez ya configurados y funcionando correctamente los microservicios, se debe configurar Kong para que tenga dentro de sus configuraciones las rutas de cada uno de ellos. Para ello, se deben correr los siguientes comandos:

```
curl -i -X POST http://localhost:8001/services/ --data name=user-service --data url=http://host.docker.internal:3001
```

![Configuracion users pt1](/integrantes/alejandra/patron-cloud/images/configuracion-users-kong.png)

```
curl -i -X POST http://localhost:8001/services/user-service/routes --data paths[]=/user-service
```

![Configuracion users pt2](/integrantes/alejandra/patron-cloud/images/configuracion-users-kong-pt2.png)

De manera similar, con el microservicio de productos se debe realizar igual con los siguientes comandos:
```
curl -i -X POST http://localhost:8001/services/ --data name=product-service --data url=http://host.docker.internal:3002  

curl -i -X POST http://localhost:8001/services/product-service/routes --data paths[]=/product-service
```

### 8. Verificar la creación de servicios y rutas en Kong

Para verificar que se han creado correctamente los servicios y rutas, y que Kong las reconoce dentro de su network, podemos seguir los siguientes pasos apuntando al puerto 8001 donde se encuentran todas las configuraciones de Kong  

Para ver los servicios creados y reconocidos por Kong:
```
curl -i http://localhost:8001/services 
```
![Servicios creados](/integrantes/alejandra/patron-cloud/images/created-services.png)

Para ver las rutas creadas y reconocidas por Kong:
```
curl -i http://localhost:8001/routes 
```
![Rutas creadas](/integrantes/alejandra/patron-cloud/images/created-routes.png)

### 9. Respuesta de microservicios mediante Kong

Para ver que se traen correctamente los endpoints de cada microservicio mediante las rutas que hemos definido para ellos, se debe apuntar al puerto 8000. Para esta demo, se siguen las siguientes rutas para el caso del microservicio **user-service**:

```
curl -i http://localhost:8000/user-service/  

curl -i http://localhost:8000/user-service/users
```

![Respuesta user-service](/integrantes/alejandra/patron-cloud/images/res-users.png)

Para el servicio de **product-service**:

![Respuesta product-service](/integrantes/alejandra/patron-cloud/images/res-products.png)