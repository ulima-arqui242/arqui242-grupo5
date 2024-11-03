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
