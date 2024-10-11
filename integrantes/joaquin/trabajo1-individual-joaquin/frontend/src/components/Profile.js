import React, { useEffect, useState } from "react";
import UserService from "./UserService";
import AuthService from "./AuthService";
import { useNavigate } from "react-router-dom"; // Hook para redirección

const Profile = () => {
    const [content, setContent] = useState("");
    const currentUser = AuthService.getCurrentUser();  // Obtén el usuario actual desde localStorage
    const navigate = useNavigate();

    useEffect(() => {
        // Si no hay usuario autenticado, redirige al login
        if (!currentUser) {
            navigate("/login");
            return;
        }

        // Si es admin, obtiene datos del admin board, si no, del user board
        if (currentUser.role === "admin") {
            UserService.getAdminBoard().then(
                response => {
                    setContent(response.data.message);
                },
                error => {
                    setContent(error.response && error.response.data.message);
                }
            );
        } else {
            UserService.getUserBoard().then(
                response => {
                    setContent(response.data.message);
                },
                error => {
                    setContent(error.response && error.response.data.message);
                }
            );
        }
    }, [currentUser, navigate]);

    if (!currentUser) {
        return null;  // Devuelve null para evitar renderizar algo si no hay usuario
    }

    return (
        <div className="profile-container">
            <h2>Perfil de {currentUser.role === "admin" ? "Administrador" : "Usuario"}</h2>
            <p>Bienvenido, {currentUser.username}!</p>
            <p>{content}</p>
        </div>
    );
};

export default Profile;
