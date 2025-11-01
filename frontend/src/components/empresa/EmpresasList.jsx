import { useContext } from "react";
import { EmpresaContext } from "../../context/EmpresaContext.jsx";
import { EmpresaRow } from "./EmpresaRow.jsx";
import { AuthContext } from "../../auth/context/AuthContext.jsx";

export const EmpresasList = () => {

    const { empresas } = useContext(EmpresaContext);
    const { login } = useContext(AuthContext);

    return (
        <table className="table table-hover table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>RUC</th>
                <th>Razón Social</th>
                <th>Dirección</th>
                <th>Email</th>
                {!login.isAdmin || (
                    <>
                        <th>Editar</th>
                        <th>Productos</th>
                        <th>Eliminar</th>
                    </>
                )}
            </tr>
            </thead>
            <tbody>
            {
                empresas.map(({ id, ruc, razonSocial, direccion, email }) => (
                    <EmpresaRow
                        key={id}
                        id={id}
                        ruc={ruc}
                        razonSocial={razonSocial}
                        direccion={direccion}
                        email={email}
                    />
                ))
            }
            </tbody>
        </table>
    )
}
