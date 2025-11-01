import { Navigate, Route, Routes } from "react-router-dom"
import { Navbar } from "../components/layout/Navbar"
import { RegisterPage } from "../pages/RegisterPage"
import { UsersPage } from "../pages/UsersPage"
import {UserProvider} from "../context/UserProvider.jsx";
import {EmpresasPage} from "../pages/EmpresasPage.jsx";
import {EmpresaProvider} from "../context/EmpresaProvider.jsx";
import {useContext} from "react";
import {AuthContext} from "../auth/context/AuthContext.jsx";
import {ProductosPage} from "../pages/ProductosPage.jsx";
import {ProductoProvider} from "../context/ProductoProvider.jsx";

export const UserRoutes = () => {
    const {login} = useContext(AuthContext);

    return (
        <>
            <UserProvider>
                <EmpresaProvider>
                    <Navbar />
                    <Routes>
                        <Route path="users" element={<UsersPage />} />

                        {!login.isAdmin ||
                            <>
                                <Route path="empresas" element={<EmpresasPage />} />
                                <Route path="users/register" element={<RegisterPage />} />
                                <Route path="users/edit/:id" element={<RegisterPage />} />
                                <Route
                                    path="empresas/:id/productos"
                                    element={
                                        <ProductoProvider>
                                            <ProductosPage />
                                        </ProductoProvider>
                                    }
                                />
                            </>
                        }
                        <Route path="/" element={<Navigate to="/users" />} />
                    </Routes>
                </EmpresaProvider>
            </UserProvider>
        </>

    )
}