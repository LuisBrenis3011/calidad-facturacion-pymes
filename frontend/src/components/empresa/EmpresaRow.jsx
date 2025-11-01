import { useContext } from "react";
import { EmpresaContext } from "../../context/EmpresaContext.jsx";
import { AuthContext } from "../../auth/context/AuthContext.jsx";
import {NavLink} from "react-router-dom";

export const EmpresaRow = ({ id, ruc, razonSocial, direccion, email }) => {

    const { handlerEmpresaSelectedForm, handlerRemoveEmpresa } = useContext(EmpresaContext);
    const { login } = useContext(AuthContext);

    return (
        <tr>
            <td>{id}</td>
            <td>{ruc}</td>
            <td>{razonSocial}</td>
            <td>{direccion}</td>
            <td>{email}</td>

            {!login.isAdmin || (
                <>
                    <td>
                        <button
                            type="button"
                            className="btn btn-secondary btn-sm"
                            onClick={() => handlerEmpresaSelectedForm({
                                id,
                                ruc,
                                razonSocial,
                                direccion,
                                email
                            })}>
                            Editar
                        </button>
                    </td>
                    <td>
                        <NavLink
                            to={`/empresas/${id}/productos`}
                            className="btn btn-sm btn-outline-primary"
                        >
                            Ver / Registrar Productos
                        </NavLink>
                    </td>
                    <td>
                        <button
                            type="button"
                            className="btn btn-danger btn-sm"
                            onClick={() => handlerRemoveEmpresa(id)}>
                            Eliminar
                        </button>
                    </td>
                </>
            )}
        </tr>
    );
};
