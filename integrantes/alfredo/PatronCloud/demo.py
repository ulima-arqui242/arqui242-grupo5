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