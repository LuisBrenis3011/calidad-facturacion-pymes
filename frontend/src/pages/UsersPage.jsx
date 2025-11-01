import { useContext, useEffect } from "react";
import { UserModalForm } from "../components/usuario/UserModalForm.jsx";
import { UsersList } from "../components/usuario/UsersList.jsx";
import { UserContext } from "../context/UserContext";
import {AuthContext} from "../auth/context/AuthContext.jsx";

export const UsersPage = () => {

    const {
        users,
        visibleForm,
        handlerOpenForm,
        getUsers,
    } = useContext(UserContext);

    const {login} = useContext(AuthContext);

    useEffect(() => {
        getUsers();
    }, []);

    return (
        <>

            {!visibleForm ||
                <UserModalForm />}
            <div className="container my-4">
                <h2>Users</h2>
                <div className="row">
                    <div className="col">
                        {(visibleForm || !login.isAdmin) || <button
                            className="btn btn-primary my-2"
                            onClick={handlerOpenForm}>
                            Nuevo Usuario
                        </button>}
                        {
                            users.length === 0
                                ? <div className="alert alert-warning">No hay usuarios en el sistema!</div>
                                : <UsersList />
                        }
                    </div>
                </div>
            </div>
        </>
    );
}