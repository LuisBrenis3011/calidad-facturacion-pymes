import { Navigate, Route, Routes } from 'react-router-dom';
import { LoginPage } from './auth/pages/LoginPage';
import { UserRoutes } from './routes/UserRoutes';
import {useContext} from "react";
import {AuthContext} from "./auth/context/AuthContext.jsx";

export const UsersApp = () => {

    const {login} = useContext(AuthContext);
    return (
        <Routes>
            {
                login.isAuth
                    ? (
                        <Route path='/*' element={<UserRoutes/>} />
                    )
                    : <>
                        <Route path='/login' element={<LoginPage/>} />
                        <Route path='/*' element={<Navigate to="/login" /> } />
                    </>

            }
        </Routes>
    );
}