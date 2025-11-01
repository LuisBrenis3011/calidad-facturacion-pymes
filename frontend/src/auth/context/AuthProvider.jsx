import {AuthContext} from "./AuthContext.jsx";
import {useAuth} from "../hooks/useAuth.js";

export const AuthProvider = ({children}) => {
    const { login, handlerLogin, handlerLogout } = useAuth();
    return (
        <AuthContext.Provider value={
            {
                login,
                handlerLogin,
                handlerLogout
            }
        }>
            {children}
        </AuthContext.Provider>
    )
}