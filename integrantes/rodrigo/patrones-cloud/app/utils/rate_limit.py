import time
from collections import defaultdict

# Configuración de límite de solicitudes
RATE_LIMIT = 5  # Número máximo de solicitudes permitidas
TIME_WINDOW = 60  # Tiempo en segundos
BLOCK_DURATION = 120  # Tiempo de bloqueo si se excede el límite (en segundos)

# Diccionarios para rastrear solicitudes y bloqueos
request_logs = defaultdict(list)
blocked_clients = {}

def is_rate_limited(client_ip):
    current_time = time.time()

    # Verificar si el cliente está bloqueado
    if client_ip in blocked_clients:
        block_time = blocked_clients[client_ip]
        if current_time - block_time < BLOCK_DURATION:
            return {"allowed": False, "message": "IP bloqueada temporalmente. Inténtalo más tarde."}
        else:
            del blocked_clients[client_ip]  # Desbloquear al cliente después del periodo de bloqueo

    # Filtrar solicitudes en la ventana de tiempo
    request_logs[client_ip] = [timestamp for timestamp in request_logs[client_ip] if current_time - timestamp < TIME_WINDOW]

    if len(request_logs[client_ip]) < RATE_LIMIT:
        request_logs[client_ip].append(current_time)
        return {"allowed": True, "message": "Acceso permitido"}
    else:
        blocked_clients[client_ip] = current_time
        return {"allowed": False, "message": "Límite de solicitudes excedido. IP bloqueada temporalmente."}
