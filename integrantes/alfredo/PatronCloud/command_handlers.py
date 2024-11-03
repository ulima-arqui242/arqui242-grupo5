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
