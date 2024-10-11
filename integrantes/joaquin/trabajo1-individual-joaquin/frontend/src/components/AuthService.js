import axios from "axios";
import { jwtDecode } from 'jwt-decode'

const API_URL = "http://localhost:5000/api/auth/";

class AuthService {
    login(username, password) {
        return axios.post(API_URL + "login", { username, password })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem("token", JSON.stringify(response.data.token))
                    localStorage.setItem("user", JSON.stringify(jwtDecode(response.data.token)));  // Guarda el token en localStorage
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");  // Elimina el token de localStorage
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem("user"));  // Obtiene el token almacenado
    }
}

export default new AuthService();
