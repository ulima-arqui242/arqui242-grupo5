import time
from collections import defaultdict

# Configuraci�n de l�mite de solicitudes
RATE_LIMIT = 5  # N�mero m�ximo de solicitudes permitidas
TIME_WINDOW = 60  # Tiempo en segundos
BLOCK_DURATION = 120  # Tiempo de bloqueo si se excede el l�mite (en segundos)

# Diccionarios para rastrear solicitudes y bloqueos
request_logs = defaultdict(list)
blocked_clients = {}

def is_rate_limited(client_ip):
    current_time = time.time()

    # Verificar si el cliente est� bloqueado
    if client_ip in blocked_clients:
        block_time = blocked_clients[client_ip]
        if current_time - block_time < BLOCK_DURATION:
            return {"allowed": False, "message": "IP bloqueada temporalmente. Int�ntalo m�s tarde."}
        else:
            del blocked_clients[client_ip]  # Desbloquear al cliente despu�s del periodo de bloqueo

    # Filtrar solicitudes en la ventana de tiempo
    request_logs[client_ip] = [timestamp for timestamp in request_logs[client_ip] if current_time - timestamp < TIME_WINDOW]

    if len(request_logs[client_ip]) < RATE_LIMIT:
        request_logs[client_ip].append(current_time)
        return {"allowed": True, "message": "Acceso permitido"}
    else:
        blocked_clients[client_ip] = current_time
        return {"allowed": False, "message": "L�mite de solicitudes excedido. IP bloqueada temporalmente."}
