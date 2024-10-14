import React from "react";
import { Link } from "react-router-dom";
import AuthService from "./AuthService";

const Navbar = () => {
    const user = AuthService.getCurrentUser();

    const handleLogout = () => {
        AuthService.logout();
        window.location.reload();
    };

    return (
        <nav>
            <Link to="/">Inicio</Link>
            {user ? (
                <>
                    <Link to="/profile">Perfil</Link>
                    {user.role === "admin" && (
                        <>
                            <Link to="/admin-dashboard">Panel Admin</Link>  {/* Enlace exclusivo para admin */}
                        </>
                    )}
                    <button onClick={handleLogout}>Cerrar sesión</button>
                </>
            ) : (
                <Link to="/login">Iniciar sesión</Link>
            )}
        </nav>
    );
};

export default Navbar;
