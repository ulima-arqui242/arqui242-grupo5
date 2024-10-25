
# Trabajo: Implementación de Patrones Cloud

# Patron: CQRQS

## Instrucciones Generales

### 1. **Elección de Tema**: CQRS

   - Seleccione uno de los patrones de cloud del catálogo de Microsoft: [Microsoft Cloud Patterns](https://learn.microsoft.com/en-us/azure/architecture/patterns/).
   - Asegúrese de registrar su elección en el listado de grupos publicado en Blackboard para evitar duplicaciones de temas.

### 2. **Desarrollo del Patrón**:

#### 1.1 Problema

El patrón CQRS aborda el problema de la **ineficiencia y complejidad en las aplicaciones** que gestionan operaciones de lectura y escritura sobre los mismos datos utilizando el mismo modelo. En sistemas tradicionales (CRUD), usar el mismo modelo para lectura y escritura puede generar cuellos de botella, afectando el rendimiento, especialmente en aplicaciones con un gran volumen de datos o una alta frecuencia de consultas.

**Ejemplo del problema**:
En aplicaciones con muchas consultas de lectura simultánea, como dashboards o sistemas de reporting, usar un único modelo puede ocasionar problemas de concurrencia y escalabilidad. Las lecturas pueden interferir con las escrituras, causando bloqueos y degradación del rendimiento.

**Problema específico que resuelve CQRS**:
El patrón CQRS separa los modelos de lectura y escritura, permitiendo optimizar cada uno de manera independiente. De esta forma, se evita que la complejidad de las operaciones de lectura afecte el rendimiento o la consistencia de las operaciones de escritura, y viceversa.

#### 2. Solución

El patrón CQRS propone la **separación de responsabilidades** entre lectura y escritura. Esto se realiza mediante dos modelos diferentes: uno para **comandos** (escritura) y otro para **consultas** (lectura), optimizando ambos para sus respectivas tareas.

- **Modelo de Comando**: Se ocupa de modificar el estado del sistema (insertar, actualizar, eliminar).
- **Modelo de Consulta**: Se encarga de las operaciones de lectura, optimizadas para la velocidad de acceso.

#### ¿Cómo funciona?
- Los **comandos** son operaciones que alteran el estado del sistema (crear, actualizar, eliminar) y deben garantizar la consistencia de los datos.
- Las **consultas** están diseñadas para ser rápidas y eficientes, separando la lógica de acceso a datos para evitar bloqueos causados por las operaciones de escritura.

#### Complemento con otras plataformas:
El patrón CQRS se usa en combinación con otros patrones como **Event Sourcing**, particularmente útil en sistemas distribuidos. Por ejemplo, **Microsoft Azure** y **AWS** ofrecen arquitecturas que soportan CQRS, permitiendo lecturas optimizadas y comandos en sistemas distribuidos de alta carga.

#### 3. Casos de Aplicación

#### E-commerce
En plataformas de comercio electrónico, las operaciones de lectura (mostrar productos, inventarios, etc.) son mucho más frecuentes que las de escritura (actualizar inventarios o realizar compras). Separar las consultas y los comandos permite que el sistema escale eficientemente, mejorando la experiencia del usuario.

#### Aplicaciones Financieras
En sistemas bancarios que manejan transacciones y reportes en tiempo real, CQRS es útil para procesar lecturas de datos históricos y actualizaciones simultáneas sin generar cuellos de botella.

#### Plataformas IoT
Aplicaciones que manejan grandes cantidades de datos de dispositivos IoT pueden usar CQRS para gestionar el procesamiento de datos en tiempo real, permitiendo lecturas y escrituras eficientes.

#### 4. Aplicación en el Proyecto Turi

#### Beneficios del uso de CQRS en Turi:

2.4.1 **Mejora en el rendimiento**:
   - **Operaciones de lectura intensiva**: En Turi, los usuarios realizan frecuentes consultas, como la búsqueda de destinos, reseñas y recomendaciones. CQRS permitiría separar estas consultas de las operaciones de escritura (como la actualización de itinerarios o creación de reseñas), optimizando la velocidad de las lecturas.
   - **Escalabilidad de consultas**: Separar las lecturas y escrituras en Turi permitiría usar bases de datos optimizadas para consultas rápidas, como una base orientada a lectura para mostrar destinos o reseñas, mejorando la experiencia del usuario.

2.4.2 **Manejo eficiente de personalización**:
   - **Recomendaciones en tiempo real**: Turi ofrece recomendaciones personalizadas basadas en las preferencias del usuario y su ubicación. Con CQRS, las recomendaciones pueden calcularse en el backend sin afectar la experiencia de lectura de datos en tiempo real, asegurando una experiencia fluida.

2.4.3 **Flexibilidad para el crecimiento**:
   - **Actualización del itinerario**: El módulo de itinerarios personalizables en Turi permite que los usuarios agreguen, editen y eliminen actividades frecuentemente. CQRS manejaría eficientemente estas actualizaciones sin bloquear la lectura de los datos actuales del itinerario.
   - **Escalabilidad futura**: Con la expansión de Turi y el incremento de usuarios, CQRS permitiría escalar el sistema fácilmente, gestionando un mayor volumen de operaciones de consulta y escritura de forma separada.

#### Consideraciones clave para implementar CQRS en Turi:
   - **Consistencia eventual**: Es posible que las lecturas no reflejen de inmediato los cambios realizados en las escrituras, lo que puede generar breves inconsistencias en los datos mostrados a los usuarios. Esto debe gestionarse cuidadosamente para no afectar negativamente la experiencia del usuario.
   - **Complejidad adicional**: Implementar CQRS agrega complejidad a la arquitectura del sistema, requiriendo mayor esfuerzo en el mantenimiento y desarrollo de los modelos de consulta y comandos.

Con CQRS, Turi puede manejar eficientemente las operaciones de consulta intensiva, como la visualización de destinos y reseñas, mientras gestiona las frecuentes actualizaciones de itinerarios y personalizaciones del usuario de forma independiente. Esto mejora el rendimiento y facilita la escalabilidad a medida que la plataforma crece.

3. **Desarrollo de Código y Demo**:
   - Implemente una solución que utilice el patrón seleccionado en un caso real o en un escenario de ejemplo bien definido.
   - Documente el caso real y detalle el proceso de implementación, asegurándose de describir cada paso realizado.
   - (Opcional) Prepare una demo en video donde muestre el funcionamiento de la implementación, explicando brevemente su funcionamiento.

4. **Entrega**:
   - Sobre su página personal en el repositorio de Github del grupo debe agregar una sección titulada "Patrones Cloud".
   - Puede desarrollar el código en el mismo repo o en un repositorio externo.
   - Si realiza el video, súbalo a una plataforma de su elección (YouTube, Vimeo, etc.) y comparta el enlace en la documentación.