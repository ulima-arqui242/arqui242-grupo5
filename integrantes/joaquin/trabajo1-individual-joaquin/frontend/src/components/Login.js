import React, { useState } from "react";
import AuthService from "./AuthService";
import { useNavigate } from "react-router-dom";  // Nuevo hook

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();  // Inicializa el hook de navegación

    const handleLogin = (e) => {
        e.preventDefault();

        AuthService.login(username, password).then(
            () => {
                navigate("/profile");  // Navega a /profile tras el login exitoso
                window.location.reload();  // Opcional: recargar la página
            },
            error => {
                setMessage("Credenciales incorrectas");
            }
        );
    };

    return (
        <div className="login-container">
            <form onSubmit={handleLogin}>
                <div>
                    <label>Usuario</label>
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
                <div>
                    <label>Contraseña</label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <button type="submit">Iniciar sesión</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default Login;
