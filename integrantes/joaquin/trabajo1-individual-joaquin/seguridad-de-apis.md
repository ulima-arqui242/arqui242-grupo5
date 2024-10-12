# SEGURIDAD DE API'S: Autentificación y Autorización
---
![Figura1](/trabajo1-individual-joaquin/what-is-api-security.avif)

## Desarrollo Conceptual
---
### 1. Definiciones
### 1.1. Seguridad de APIs
La **seguridad de APIs** se refiere a las prácticas y tecnologías utilizadas para proteger las interfaces de programación de aplicaciones (APIs) contra accesos no autorizados, abuso de recursos y otros tipos de ataques. Las APIs son los puntos de acceso a la lógica de una aplicación, por lo que una API insegura puede permitir que un atacante acceda a datos sensibles o comprometa el sistema.

**Problemas que Previene**:
- **Fuga de Datos**: Previene que usuarios no autorizados accedan a información confidencial.
- **Abuso de Servicios**: Protege los recursos de la aplicación de ser abusados o explotados.
- **Ataques Automatizados**: Detecta y bloquea intentos de abuso masivo por parte de bots o scripts automatizados.

### 1.2. Autentificación
La autenticación es el proceso de verificar la identidad de un usuario o sistema. Esto implica asegurarse de que la persona que intenta acceder a un sistema es realmente quien dice ser. Esto se logra mediante credenciales, como contraseñas, tarjetas de identificación, tokens o biometría.

### 1.3. Autorización
La autorización se refiere al proceso de conceder o denegar permisos a un usuario autenticado para acceder a ciertos recursos o realizar determinadas acciones en un sistema. Esto se basa en políticas definidas que dictan qué puede hacer cada usuario según su rol, atributos o políticas predefinidas.

### 2. Tecnologías y Métodos
#### 2.1. Tecnologías de Autenticación

#### 2.1.1. JSON Web Tokens (JWT)
**Descripción**: JWT es un estándar abierto (RFC 7519) que define un formato compacto y autónomo para transmitir información de manera segura entre partes como un objeto JSON. La información se puede verificar y confiar, ya que está firmada digitalmente.

- **Caso de Uso**: Ideal para aplicaciones web y móviles donde se requiere que los usuarios se autentiquen y se mantengan autenticados entre solicitudes.

- **Ventajas**:
  - **Portabilidad**: Los tokens pueden ser enviados a través de URL, parámetros POST o en encabezados HTTP.
  - **Autenticidad**: Se pueden firmar usando HMAC o RSA, lo que asegura que el contenido no ha sido alterado.
  - **Sin estado**: No se requiere almacenar información de sesión en el servidor.

- **Desventajas**:
  - **Vulnerabilidad a ataques XSS**: Si se almacenan en localStorage, los tokens pueden ser robados por scripts maliciosos.
  - **Revocación**: No hay un mecanismo estándar para revocar tokens antes de su expiración.

#### 2.1.2. OAuth 2.0
**Descripción**: Un protocolo de autorización que permite a las aplicaciones de terceros obtener acceso limitado a un servicio HTTP en nombre de un usuario.

- **Caso de Uso**: Usado comúnmente en aplicaciones que necesitan acceder a recursos de terceros, como acceder a cuentas de Google o Facebook.

- **Ventajas**:
  - **Delegación de Acceso**: Los usuarios pueden otorgar acceso a sus recursos sin compartir credenciales.
  - **Flexibilidad**: Soporta diferentes flujos de autorización, lo que permite adaptarse a distintas necesidades de seguridad.

- **Desventajas**:
  - **Complejidad**: La implementación puede ser complicada y propensa a errores si no se maneja adecuadamente.
  - **Seguridad**: La configuración incorrecta puede dar lugar a vulnerabilidades, como ataques de redirección.

#### 2.1.3. OpenID Connect
**Descripción**: Una capa de identidad construida sobre OAuth 2.0 que permite a los clientes verificar la identidad del usuario basado en la autenticación realizada por un servidor de autorización.

- **Caso de Uso**: Ideal para aplicaciones que requieren autenticación de usuario, como plataformas de redes sociales.

- **Ventajas**:
  - **Integración Simple**: Permite a los desarrolladores integrar autenticación con menos esfuerzo.
  - **Interoperabilidad**: Funciona bien con aplicaciones que utilizan OAuth 2.0.

- **Desventajas**:
  - **Dependencia de Terceros**: Si el proveedor de identidad falla, la autenticación del usuario también falla.
  - **Gestión de Sesiones**: La gestión de sesión puede complicarse si el estado de la sesión no se mantiene adecuadamente.

#### 2.2 Métodos de Autorización

#### 2.2.1. Role-Based Access Control (RBAC)
**Descripción**: Un método de control de acceso que asigna permisos a roles en lugar de a individuos. Los usuarios se asignan a roles, y los roles definen qué recursos pueden acceder.

- **Caso de Uso**: Común en aplicaciones empresariales donde diferentes usuarios tienen diferentes niveles de acceso.

- **Ventajas**:
  - **Simplicidad**: Fácil de entender y administrar, especialmente en organizaciones grandes.
  - **Escalabilidad**: Permite gestionar permisos de manera eficiente al agregar o quitar roles.

- **Desventajas**:
  - **Rigidez**: Puede volverse complicado si se requieren permisos granulares que no se ajusten a los roles predefinidos.
  - **Sobrecarga**: Puede resultar en una gestión de permisos innecesariamente complicada si se crean demasiados roles.

#### 2.2.2. Attribute-Based Access Control (ABAC)
**Descripción**: Un modelo que utiliza atributos (características) de los usuarios, recursos y el entorno para determinar los permisos de acceso.

- **Caso de Uso**: Útil en aplicaciones que requieren decisiones de acceso basadas en múltiples factores, como la ubicación del usuario o el tiempo.

- **Ventajas**:
  - **Flexibilidad**: Permite políticas de acceso más detalladas y específicas basadas en atributos.
  - **Adaptabilidad**: Se puede modificar fácilmente a medida que cambian las necesidades de seguridad.

- **Desventajas**:
  - **Complejidad**: La implementación puede ser compleja y requiere un enfoque más riguroso para la gestión de políticas.
  - **Rendimiento**: Puede impactar en el rendimiento si hay una gran cantidad de atributos a evaluar.

#### 2.2.3. Policy-Based Access Control (PBAC)

**Descripción**:  
Un enfoque que permite definir políticas de acceso que pueden ser evaluadas en tiempo real. Se pueden considerar múltiples condiciones y contextos para decidir el acceso.

**Casos de Uso**:
- Aplicaciones empresariales donde las decisiones de acceso necesitan ser dinámicas y adaptables a condiciones cambiantes.

**Ventajas**:
- **Adaptabilidad**: Las políticas pueden adaptarse a cambios en el entorno o requisitos de negocio.
- **Centralización**: Permite la gestión centralizada de políticas de acceso.

**Desventajas**:
- **Complejidad**: La definición y gestión de políticas pueden ser complicadas.
- **Dependencia**: Puede depender de un sistema externo para la evaluación de políticas, lo que podría introducir latencia.

## Toma de Decisiones
---
### Factores Clave
Al elegir tecnologías de autenticación y métodos autorización, se deben considerar varios factores:

- **Requisitos de Seguridad**: Si la aplicación maneja datos sensibles, se debe optar por métodos robustos como JWT con HTTPS y OAuth 2.0.
  
- **Escalabilidad**: Para aplicaciones que se espera que crezcan, se debe considerar RBAC o ABAC para manejar eficientemente los permisos.

- **Facilidad de Implementación**: Para un desarrollo rápido, tecnologías como OpenID Connect o servicios de terceros pueden ser preferibles.

- **Control sobre la Seguridad**: Si se desea un control total sobre la gestión de autenticación y autorización, implementar JWT y RBAC o ABAC localmente puede ser más adecuado.

## Consideraciones Técnicas
---
> El escenario de esta demo consiste en implementar un sistema de seguridad para APIs que involucra los procesos de autenticación y autorización. El objetivo es que, mediante la autenticación, los usuarios puedan acceder al sistema de manera segura, mientras que la autorización controla el acceso a los recursos según los permisos asignados. Para la sección de autentificación se uso la tecnología de JWTs previamente explicada, mientras que para autorización se uso la táctica de RBAC que a su vez hace uso de JWTs.

### 1. Requisitos Previos

Para efectos del presente trabajo, se requerirán de las siguientes instalaciones:
### 1.1 Front-End / Back-End
#### Paso 1: Instalar VScode
https://code.visualstudio.com/download 
#### Paso 2: Instalar NodeJS
https://nodejs.org/en/
#### Paso 3: Entrar al proyecto
Abrir la carpeta del "frontend" en una pestaña de VScode y en otra la carpeta "backend".
#### Paso 4: Instalar dependencias
Para instalar las dependencias del proyecto se debe abrir una terminal por carpeta y ejecutar el comando en ambas, este comando instala las paquetes usados tanto en el frontend y backend de acuerdo al package.json.
```
npm install 
```

### 2. Ejecutar proyecto
Para ejectuar el proyecto se deberá realizar los siguientes comandos en las ambas terminales. Recordar que la locación del terminal debe apuntar a la carpeta root de cada uno de las partes de la platafoma (frontend/backend).

**SOLO PARA EL FRONTEND**
```
npm start 
```
**SOLO PARA EL BACKEND**
```
node server.js
```

## Demo
---
Link de la demo: 

## Fuentes
---
- ¿Qué es la seguridad de API? <https://www.akamai.com/es/glossary/what-is-api-security>
- JSON Web Token (JWT) - Auth0 by Okta <https://jwt.io/introduction>
- OAuth 2.0 - Auth0 by Okta <https://auth0.com/es/intro-to-iam/what-is-oauth-2>
- OpenID Connect - OpenID Foundation <https://openid.net/connect/>
- Role-Based Access Control (RBAC) - NIST <https://csrc.nist.gov/publications/detail/sp/800-162/final>
- Attribute-Based Access Control (ABAC) - NIST <https://csrc.nist.gov/projects/attribute-based-access-control>
- Policy-Based Access Control (PBAC) - Immuta <https://www.immuta.com/blog/what-is-policy-based-access-control/>