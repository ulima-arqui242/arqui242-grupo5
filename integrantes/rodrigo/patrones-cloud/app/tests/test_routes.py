import unittest
from app.main import app

class TestRoutes(unittest.TestCase):
    def setUp(self):
        self.app = app.test_client()
        self.app.testing = True

    def test_resource_access(self):
        response = self.app.get('/resource')
        self.assertIn(response.status_code, [200, 429])  # Puede ser exitoso o limitado

    def test_status_check(self):
        response = self.app.get('/status')
        self.assertEqual(response.status_code, 200)
        self.assertIn('El servidor está operativo', response.data)

if __name__ == '__main__':
    unittest.main()
