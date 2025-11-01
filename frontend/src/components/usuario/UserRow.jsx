import { NavLink } from "react-router-dom"
import {useContext} from "react";
import {UserContext} from "../../context/UserContext.jsx";
import {AuthContext} from "../../auth/context/AuthContext.jsx";

export const UserRow = ({id, username, email, admin}) => {

    const {handlerUserSelectedForm, handlerRemoveUser} = useContext(UserContext);
    const {login} = useContext(AuthContext);
    return (
        <tr>
            <td>{id}</td>
            <td>{username}</td>
            <td>{email}</td>

            {!login.isAdmin ||
                <>
                    <td>
                        <button
                            type="button"
                            className="btn btn-secondary btn-sm"
                            onClick={() => handlerUserSelectedForm({
                                id,
                                username,
                                email,
                                admin
                            })}
                        >
                            update
                        </button>
                    </td>
                    <td>
                        <NavLink className={'btn btn-secondary btn-sm'}
                                 to={'/users/edit/' + id} >
                            update route
                        </NavLink>
                    </td>
                    <td>
                        <button
                            type="button"
                            className="btn btn-danger btn-sm"
                            onClick={() => handlerRemoveUser(id)}
                        >
                            remove
                        </button>
                    </td>
                </>}
        </tr>
    )
}