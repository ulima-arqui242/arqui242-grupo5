from flask import Blueprint, jsonify

status_blueprint = Blueprint('status', __name__)

@status_blueprint.route('/status', methods=['GET'])
def status_check():
    return jsonify({"status": "El servidor est� operativo"}), 200
