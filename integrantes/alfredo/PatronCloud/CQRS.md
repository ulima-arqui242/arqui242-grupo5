
# Trabajo: Implementación de Patrones Cloud

# Patron: CQRQS

## 1. Problema

El patrón CQRS aborda el problema de la **ineficiencia y complejidad en las aplicaciones** que gestionan operaciones de lectura y escritura sobre los mismos datos utilizando el mismo modelo. En sistemas tradicionales (CRUD), usar el mismo modelo para lectura y escritura puede generar cuellos de botella, afectando el rendimiento, especialmente en aplicaciones con un gran volumen de datos o una alta frecuencia de consultas.

**Ejemplo del problema**:
En aplicaciones con muchas consultas de lectura simultánea, como dashboards o sistemas de reporting, usar un único modelo puede ocasionar problemas de concurrencia y escalabilidad. Las lecturas pueden interferir con las escrituras, causando bloqueos y degradación del rendimiento.

**Problema específico que resuelve CQRS**:
El patrón CQRS separa los modelos de lectura y escritura, permitiendo optimizar cada uno de manera independiente. De esta forma, se evita que la complejidad de las operaciones de lectura afecte el rendimiento o la consistencia de las operaciones de escritura, y viceversa.

## 2. Solución

El patrón CQRS propone la **separación de responsabilidades** entre lectura y escritura. Esto se realiza mediante dos modelos diferentes: uno para **comandos** (escritura) y otro para **consultas** (lectura), optimizando ambos para sus respectivas tareas.

- **Modelo de Comando**: Se ocupa de modificar el estado del sistema (insertar, actualizar, eliminar).
- **Modelo de Consulta**: Se encarga de las operaciones de lectura, optimizadas para la velocidad de acceso.

### ¿Cómo funciona?
- Los **comandos** son operaciones que alteran el estado del sistema (crear, actualizar, eliminar) y deben garantizar la consistencia de los datos.
- Las **consultas** están diseñadas para ser rápidas y eficientes, separando la lógica de acceso a datos para evitar bloqueos causados por las operaciones de escritura.

### Complemento con otras plataformas:
El patrón CQRS se usa en combinación con otros patrones como **Event Sourcing**, particularmente útil en sistemas distribuidos. Por ejemplo, **Microsoft Azure** y **AWS** ofrecen arquitecturas que soportan CQRS, permitiendo lecturas optimizadas y comandos en sistemas distribuidos de alta carga.

## 3. Casos de Aplicación

### E-commerce
En plataformas de comercio electrónico, las operaciones de lectura (mostrar productos, inventarios, etc.) son mucho más frecuentes que las de escritura (actualizar inventarios o realizar compras). Separar las consultas y los comandos permite que el sistema escale eficientemente, mejorando la experiencia del usuario.

### Aplicaciones Financieras
En sistemas bancarios que manejan transacciones y reportes en tiempo real, CQRS es útil para procesar lecturas de datos históricos y actualizaciones simultáneas sin generar cuellos de botella.

### Plataformas IoT
Aplicaciones que manejan grandes cantidades de datos de dispositivos IoT pueden usar CQRS para gestionar el procesamiento de datos en tiempo real, permitiendo lecturas y escrituras eficientes.

## 4. Aplicación en el Proyecto Turi

### Beneficios del uso de CQRS en Turi:

#### 4.1 **Mejora en el rendimiento**:
   - **Operaciones de lectura intensiva**: En Turi, los usuarios realizan frecuentes consultas, como la búsqueda de destinos, reseñas y recomendaciones. CQRS permitiría separar estas consultas de las operaciones de escritura (como la actualización de itinerarios o creación de reseñas), optimizando la velocidad de las lecturas.
   - **Escalabilidad de consultas**: Separar las lecturas y escrituras en Turi permitiría usar bases de datos optimizadas para consultas rápidas, como una base orientada a lectura para mostrar destinos o reseñas, mejorando la experiencia del usuario.

#### 4.2 **Manejo eficiente de personalización**:
   - **Recomendaciones en tiempo real**: Turi ofrece recomendaciones personalizadas basadas en las preferencias del usuario y su ubicación. Con CQRS, las recomendaciones pueden calcularse en el backend sin afectar la experiencia de lectura de datos en tiempo real, asegurando una experiencia fluida.

#### 4.3 **Flexibilidad para el crecimiento**:
   - **Actualización del itinerario**: El módulo de itinerarios personalizables en Turi permite que los usuarios agreguen, editen y eliminen actividades frecuentemente. CQRS manejaría eficientemente estas actualizaciones sin bloquear la lectura de los datos actuales del itinerario.
   - **Escalabilidad futura**: Con la expansión de Turi y el incremento de usuarios, CQRS permitiría escalar el sistema fácilmente, gestionando un mayor volumen de operaciones de consulta y escritura de forma separada.

#### Consideraciones clave para implementar CQRS en Turi:
   - **Consistencia eventual**: Es posible que las lecturas no reflejen de inmediato los cambios realizados en las escrituras, lo que puede generar breves inconsistencias en los datos mostrados a los usuarios. Esto debe gestionarse cuidadosamente para no afectar negativamente la experiencia del usuario.
   - **Complejidad adicional**: Implementar CQRS agrega complejidad a la arquitectura del sistema, requiriendo mayor esfuerzo en el mantenimiento y desarrollo de los modelos de consulta y comandos.

Con CQRS, Turi puede manejar eficientemente las operaciones de consulta intensiva, como la visualización de destinos y reseñas, mientras gestiona las frecuentes actualizaciones de itinerarios y personalizaciones del usuario de forma independiente. Esto mejora el rendimiento y facilita la escalabilidad a medida que la plataforma crece.

## 5. *Desarrollo de Código y Demo*:

### Descripción General

En esta demo, vamos a implementar un sistema de gestión de pedidos para una aplicación de comercio electrónico. El sistema contará con:

- **Operaciones de comandos (escritura)** para crear, actualizar y cancelar pedidos.
- **Operaciones de consultas (lectura)** para ver los detalles de un pedido y el historial de pedidos de un usuario.
- **Simulación de consistencia eventual** entre las bases de datos de comandos y de consultas mediante un retraso en la sincronización.

Usaremos el patrón CQRS para **separar las operaciones de lectura y escritura**, mejorando la escalabilidad y el rendimiento del sistema.

#### Requisitos Previos
1. **PostgreSQL**: Asegúrate de tener PostgreSQL instalado y configurado.
2. **Python**: Instala Python y las dependencias necesarias, como **SQLAlchemy** para la gestión de la base de datos.

   ```bash
      pip install sqlalchemy psycopg2
   ```

   ![SqlAlchemy](/integrantes/alfredo/PatronCloud/images/InstallSql.png)
---

### Estructura del código

Para mantener las operaciones de lectura y escritura separadas, vamos a organizar el código en dos módulos principales:

1. **Módulo de Comandos**: Maneja las operaciones de escritura (crear, actualizar, cancelar pedidos).
2. **Módulo de Consultas**: Maneja las operaciones de lectura (consultar detalles del pedido, ver historial de pedidos).

Cada módulo interactuará con una base de datos separada para reflejar el modelo de CQRS. En este caso, usaremos **PostgreSQL** para la base de datos de comandos y **PostgreSQL** en modo de solo lectura para la base de datos de consultas (simulando un modelo de consistencia eventual).

---
### Implementación del código

1. **Configurar las Bases de Datos en PostgreSQL**

   Crearemos dos bases de datos PostgreSQL para simular la arquitectura CQRS:

   - **orders_commands**: base de datos para manejar las operaciones de escritura.
   - **orders_queries**: base de datos para manejar las operaciones de lectura.
  
   #### Crear las bases de datos
   Accedemos a PostgreSQL y ejecutamos los siguientes comandos:

   ```sql
   CREATE DATABASE orders_commands;
   CREATE DATABASE orders_queries;
   ```

   ![Databases](/integrantes/alfredo/PatronCloud/images/CreacionDatabase.png)
   
2. **Configurar SQLAlchemy para Conectarse a Ambas Bases de Datos**
   
   Creamos un archivo llamado `db_setup.py` para definir la configuración de las bases de datos y el modelo de datos.

   ```python
   # db_setup.py
   from sqlalchemy import create_engine, Column, Integer, String, DateTime
   from sqlalchemy.ext.declarative import declarative_base
   from sqlalchemy.orm import sessionmaker
   import datetime

   Base = declarative_base()

   # Configuración de la base de datos
   COMMANDS_DATABASE_URI = 'postgresql://postgres:password@localhost/orders_commands'
   commands_engine = create_engine(COMMANDS_DATABASE_URI)
   CommandSession = sessionmaker(bind=commands_engine)

   QUERIES_DATABASE_URI = 'postgresql://postgres:password@localhost/orders_queries'
   queries_engine = create_engine(QUERIES_DATABASE_URI)
   QuerySession = sessionmaker(bind=queries_engine)

   class Order(Base):
      __tablename__ = 'orders'

      id = Column(Integer, primary_key=True)
      user_id = Column(Integer)
      product_name = Column(String)
      status = Column(String)
      created_at = Column(DateTime, default=datetime.datetime.utcnow)

      def __repr__(self):
         return f"<Order(id={self.id}, user_id={self.user_id}, product_name='{self.product_name}', status='{self.status}', created_at='{self.created_at}')>"

   # Crear las tablas en ambas bases de datos
   Base.metadata.create_all(commands_engine)
   Base.metadata.create_all(queries_engine)

   ```

   Reemplaza `user` y `password` con tus credenciales de PostgreSQL. Este archivo define:

- El modelo de la tabla `orders`, con columnas para `id`, `user_id`, `product_name`, `status`, y `created_at`.
- La creación de tablas en ambas bases de datos (`orders_commands` y `orders_queries`).

3. **Implementar el Módulo de Comandos (Escritura)**

   El **módulo de comandos** manejará la lógica para crear, actualizar y cancelar pedidos en la base de datos de comandos. Después de cada operación, añadiremos un proceso de sincronización para actualizar la base de datos de consultas, simulando consistencia eventual.

   **Código del Módulo de Comandos**
   
   Crea un archivo llamado `command_handlers.py`

   ```python
   # command_handlers.py
   from db_setup import Order, CommandSession, QuerySession
   import time

   class OrderCommandHandler:
      def __init__(self):
         self.session = CommandSession()

      def create_order(self, user_id, product_name):
         new_order = Order(user_id=user_id, product_name=product_name, status="Pending")
         self.session.add(new_order)
         self.session.commit()
         print(f"Pedido creado: {new_order}")
         self.sync_with_query_database(new_order)

      def update_order_status(self, order_id, new_status):
         order = self.session.query(Order).filter_by(id=order_id).first()
         if order:
               order.status = new_status
               self.session.commit()
               print(f"Estado del pedido actualizado: {order}")
               self.sync_with_query_database(order)
         else:
               print("Pedido no encontrado")

      def cancel_order(self, order_id):
         order = self.session.query(Order).filter_by(id=order_id).first()
         if order:
               order.status = "Cancelled"
               self.session.commit()
               print(f"Pedido cancelado: {order}")
               self.sync_with_query_database(order)
         else:
               print("Pedido no encontrado")

      def sync_with_query_database(self, order):
         """Simulación de consistencia eventual mediante retraso"""
         time.sleep(2)  # Simula un retraso para reflejar la consistencia eventual
         query_session = QuerySession()
         existing_order = query_session.query(Order).filter_by(id=order.id).first()
         if existing_order:
               existing_order.status = order.status
         else:
               new_order_copy = Order(
                  id=order.id,
                  user_id=order.user_id,
                  product_name=order.product_name,
                  status=order.status,
                  created_at=order.created_at
               )
               query_session.add(new_order_copy)
         query_session.commit()
         query_session.close()
         print("Base de datos de consultas actualizada.")
   ```
#### **Explicación**: 
- **create_order**: Crea un nuevo pedido y lo sincroniza con la base de datos de consultas.
- **update_order_status**: Actualiza el estado de un pedido y sincroniza el cambio.
- **cancel_order**: Cancela un pedido y sincroniza el estado.
- **sync_with_query_database**: Simula la consistencia eventual copiando o actualizando el pedido en la base de datos de consultas después de un retraso (`time.sleep(2)`).


4. **Implementar el Módulo de Consultas**

   El **módulo de consultas** obtendrá los detalles de los pedidos desde la base de datos `orders_queries`, que se actualiza con cierto retraso.

   **Código del Módulo de Consultas**

   Crea un archivo llamado `query_handlers.py`:

   ```python
   # query_handlers.py
   from db_setup import Order, QuerySession

   class OrderQueryHandler:
      def __init__(self):
         self.session = QuerySession()

      def get_order_details(self, order_id):
         order = self.session.query(Order).filter_by(id=order_id).first()
         if order:
               print(f"Detalles del pedido: {order}")
               return order
         else:
               print("Pedido no encontrado")

      def get_user_order_history(self, user_id):
         orders = self.session.query(Order).filter_by(user_id=user_id).all()
         print(f"Historial de pedidos del usuario {user_id}:")
         for order in orders:
               print(order)
         return orders
   ```

  - **get_order_details**: Recupera y muestra los detalles de un pedido específico.
- **get_user_order_history**: Recupera y muestra el historial de pedidos de un usuario.
5. **Ejecución de la Demo**

   Crea un archivo llamado demo.py para ejecutar la demo de CQRS y demostrar la consistencia eventual:
   
   ```python
   # demo.py
   from command_handlers import OrderCommandHandler
   from query_handlers import OrderQueryHandler
   import time

   command_handler = OrderCommandHandler()
   query_handler = OrderQueryHandler()

   # Crear un nuevo pedido
   print("\nCreando un nuevo pedido:")
   command_handler.create_order(user_id=1, product_name="Laptop")

   # Consultar el pedido inmediatamente después de crearlo (simula inconsistencia)
   print("\nConsultando el pedido antes de sincronizar:")
   query_handler.get_order_details(order_id=1)

   # Esperar para permitir la sincronización (simulando consistencia eventual)
   print("\nEsperando sincronización...")
   time.sleep(3)

   # Consultar el pedido nuevamente después de la sincronización
   print("\nConsultando el pedido después de la sincronización:")
   query_handler.get_order_details(order_id=1)

   # Actualizar el estado del pedido y verificar consistencia eventual
   print("\nActualizando estado del pedido a 'Shipped':")
   command_handler.update_order_status(order_id=1, new_status="Shipped")

   print("\nConsultando el estado del pedido antes de sincronizar:")
   query_handler.get_order_details(order_id=1)

   print("\nEsperando sincronización...")
   time.sleep(3)

   print("\nConsultando el estado del pedido después de la sincronización:")
   query_handler.get_order_details(order_id=1)
   ```
---

![Demo](/integrantes/alfredo/PatronCloud/images/DemoLog.png)
#### Explicación de la demo:

1. **Crear Pedido**: Se crea un pedido en la base de datos de comandos y se sincroniza con la base de datos de consultas después de un retraso.
2. **Consultar Pedido**: Se consulta el pedido en la base de datos de consultas antes y después de la sincronización, demostrando la consistencia eventual.
3. **Actualizar Pedido**: Se cambia el estado del pedido, y el retraso en la sincronización refleja la consistencia eventual en el modelo de consultas.
