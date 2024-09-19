# ARQUITECTURAS DE APLICACIONES MÓVILES: Nativas vs Híbridas vs Multiplataforma

## Desarollo conceptual
### 1. Arquitecturas de aplicaciones

El desarrollo de aplicaciones móviles se encuentra en constante evolución y crecimiento, por lo que diversas discusiones emergen. Una de estas discusiones a raíz de un mundo de desarrollo de aplicaciones móviles saturado es: ¿Cuál es la mejor arquitectura móvil a implementar? ¿Debe ser una app móvil, híbrida o multiplataforma?. Para este cuestionamiento no existe una respuesta exacta, pues depende mucho de la organización, los líderes de negocios y los desarrolladores a cargo. Lo que se adapta mejor a una organización, no necesariamente es lo que necesite otra.

Bajo esta primicia, es primordial comprender lo que es cada de una de estas arquitecturas a nivel conceptual, sus ventajes y desventajas, y los frameworks existentes.



**1.1. Nativas**  
Las aplicaciones nativas están desarrolladas para una plataforma móvil específica usando el lenguaje de programación de dicha plataforma. Permiten que el funcionamiento sea bueno porque están optimizados para el sistema operativo del dispositivo, tanto a nivel de software como de hardware (cámara, sensores, geolocalización). Además, permiten que la UX a nivel de look and feel sea más fluida.

Principalmente las aplicaciones nativas están desarrolladas para funcionar en Android y iOS, permitiendo un algo nivel de adecación a la plataforma. 
- Android: Java o Kotlin.
- iOS: Objective-C o Swift.

#### Ventajas
- **Rendimiento**
  - Brindan rapidez y alto rendimiento porque están optimizadas para la plataforma para la que se desarrolla.
- **Menors bugs**
  - Al tener control de un proyecto destinado a una sola plataforma, se tiene mejor control de todos los aspectos de la app y permite reducir los bugs en comparación a utiliza aplicaciones híbridas o multiplataforma.
- **Calidad de UX**
  - Permiten realizar interfaces más intuitivas y fluidas con la ayuda de frameworks que siguen los lineamientos de la plataforma
- **Accesibilidad a opciones nativas**
  - Pueden acceder e interactar con todas las funcionalidades del dispositivo, teniendo un mejor control de ellos al ser nativo y basado para funcionar en su sistema operativo.
- **Seguridad**
  - Se tiene acceso a características built-in que permiten implmentar seguridad en las aplicaciones como la detección de fraude y encriptación de datos, además de estar menos vulnerable que apps híbridas que trabajan muchas veces sobre WebViews.
#### Desventajas
- **Código no reutilizable**
  - Al ser desarrolladas para una plataforma específica, se requiere desarrollar la misma app pero para otras plataformas en caso se requiera. 
    - Ejem: Si desarrollo una app Android y luego deseo también hacerla para iOS, se debe hacer la aplicación nativamente para iOS desde cero.
- **Costos de mantenimiento**
  - Los costos suelen ser mayores para aplicaciones nativas.
- **Costo de tiempo**
  - Si se requieren aplicaciones nativas para distintas plataformas, se debe llevar a cabo un desarrollo separado para cada una, lo cual incrementa el tiempo de desarrollo y también impacta en los costos monetarios del mismo.
- 

**1.2. Híbridas**  
Las aplicaciones híbridas están desarrolladas utilizando tecnologías web como HTML, CSS y Javascript, esto permite que se desarrollen aplicaciones para múltiples plataformas con un solo desarrollo. Además, tienen dependencia en un navegador integrado.

#### Ventajas
- **Desarrollo rápido**
  - a
#### Desventajas

**1.3. Multiplataforma**

## 2. Consideraciones técnicas


## 3. Demo




### Fuentes
- https://www.microsoft.com/en-us/power-platform/products/power-apps/topics/app-development/native-vs-cross-platform-apps
- https://www.inspiringapps.com/blog/120/app-development-native-vs-hybrid-vs-cross-platform/
- https://www.netsolutions.com/insights/native-vs-hybrid-vs-cross-platform/
- https://movadex.com/blog/article/native-vs-hybrid-vs-cross-platform-vs-pwa-a-comprehensive-comparison
- https://turingpoint.de/en/blog/native-or-hybrid-app-which-is-more-secure/