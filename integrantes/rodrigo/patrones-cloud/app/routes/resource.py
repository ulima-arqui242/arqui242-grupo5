from flask import Blueprint, request, jsonify
from app.utils.rate_limit import is_rate_limited

resource_blueprint = Blueprint('resource', __name__)

@resource_blueprint.route('/resource', methods=['GET'])
def rate_limited_resource():
    client_ip = request.remote_addr
    result = is_rate_limited(client_ip)

    if result["allowed"]:
        return jsonify({"message": "Acceso concedido a la API"}), 200
    else:
        return jsonify({"error": result["message"]}), 429
