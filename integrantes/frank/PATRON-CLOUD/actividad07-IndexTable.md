
# Trabajo: Implementación de Patrones Cloud

# Patron: Index Table

## Instrucciones Generales

1. **Elección de Tema**: Index Table

   - Seleccione uno de los patrones de cloud del catálogo de Microsoft: [Microsoft Cloud Patterns](https://learn.microsoft.com/en-us/azure/architecture/patterns/).
   - Asegúrese de registrar su elección en el listado de grupos publicado en Blackboard para evitar duplicaciones de temas.

2. **Desarrollo del Patrón**:
   Para el patrón seleccionado, realice un análisis detallado que incluya los siguientes puntos:
   
   - **Problema**: En muchos almacenes de datos, la información se organiza usando una clave primaria, lo que permite localizar datos de manera eficiente. Sin embargo, cuando se necesita realizar una consulta por un campo diferente (por ejemplo, "ciudad" en lugar de "ID de cliente"), la búsqueda se vuelve lenta, ya que no hay un índice para ese campo.
   - **Solución**: El patrón propone crear manualmente tablas de índice organizadas por las claves secundarias necesarias para las consultas. Existen tres estrategias comunes:
      -  Desnormalización completa: Duplicar los datos en cada tabla de índice, organizados por claves diferentes (por ejemplo, ciudad, apellido). Esto mejora el rendimiento de las consultas, pero aumenta el espacio de almacenamiento y la complejidad de mantenimiento cuando los datos cambian frecuentemente.
      - Tablas de índice normalizadas: En lugar de duplicar los datos, las tablas de índice contienen referencias a los datos originales utilizando la clave primaria (tabla de hechos). Esto reduce el almacenamiento, pero implica dos búsquedas: primero en la tabla de índice y luego en la tabla de hechos.
      - Desnormalización parcial: Se duplican solo los campos más consultados en las tablas de índice, mientras que otros datos se mantienen en la tabla de hechos. Esto balancea el rendimiento y el espacio de almacenamiento, reduciendo la carga de mantenimiento.
   - **Casos de Aplicación**: Este patrón es útil cuando se trabaja con grandes volúmenes de datos y consultas frecuentes por campos no primarios, especialmente en aplicaciones de análisis de datos, sistemas distribuidos o bases de datos NoSQL como DynamoDB, que no tienen soporte para índices secundarios nativos.
   Por ejemplo, en sistemas de comercio electrónico, donde las consultas pueden buscar por categorías, ciudades o rangos de precios, las tablas de índice permiten localizar productos o usuarios sin tener que recorrer toda la base de datos.
   - **Aplicación en su Trabajo de Grupo**: En Turi, el patrón Cloud **Index Table** puede ser utilizado para mejorar significativamente el rendimiento en la búsqueda y recuperación de información en módulos que manejan grandes cantidades de datos, como el módulo de reviews y el blog de destinos turísticos. A medida que los usuarios suben reseñas y comentarios, y otros consultan esta información, las consultas sobre los datos pueden volverse lentas, especialmente si se basan en atributos no primarios, como calificación, fecha de publicación, o tipo de lugar. El patrón Index Table permite crear tablas de índice especializadas que contienen sólo las claves necesarias para acceder a los registros de la tabla principal, organizadas según estos atributos específicos.
   Por ejemplo, se puede crear una tabla de índice que contenga las calificaciones de las reseñas junto con su ID correspondiente, lo que permite que las consultas de los usuarios que buscan los lugares mejor valorados se ejecuten mucho más rápido al no tener que recorrer toda la tabla de reviews. Del mismo modo, otra tabla de índice podría almacenar los registros ordenados por fecha, facilitando la búsqueda de las reseñas más recientes.
   Este patrón también garantiza que la plataforma sea más escalable, ya que al aumentar el volumen de datos, las búsquedas seguirán siendo eficientes sin comprometer el tiempo de respuesta. 
3. **Desarrollo de Código y Demo**:
   - Implemente una solución que utilice el patrón seleccionado en un caso real o en un escenario de ejemplo bien definido.
   - Documente el caso real y detalle el proceso de implementación, asegurándose de describir cada paso realizado.
   - (Opcional) Prepare una demo en video donde muestre el funcionamiento de la implementación, explicando brevemente su funcionamiento.

4. **Entrega**:
   - Sobre su página personal en el repositorio de Github del grupo debe agregar una sección titulada "Patrones Cloud".
   - Puede desarrollar el código en el mismo repo o en un repositorio externo.
   - Si realiza el video, súbalo a una plataforma de su elección (YouTube, Vimeo, etc.) y comparta el enlace en la documentación.
