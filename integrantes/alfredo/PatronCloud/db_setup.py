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
