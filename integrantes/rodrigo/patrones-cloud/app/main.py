from flask import Flask
from app.routes.resource import resource_blueprint
from app.routes.status import status_blueprint

app = Flask(__name__)

# Registrar blueprints
app.register_blueprint(resource_blueprint)
app.register_blueprint(status_blueprint)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
