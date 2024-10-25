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