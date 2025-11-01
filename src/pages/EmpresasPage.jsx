import { useContext, useEffect } from "react";
import { EmpresaModalForm } from "../components/empresa/EmpresaModalForm.jsx";
import { EmpresasList } from "../components/empresa/EmpresasList.jsx";
import { EmpresaContext } from "../context/EmpresaContext";
import { AuthContext } from "../auth/context/AuthContext.jsx";

export const EmpresasPage = () => {

    const {
        empresas,
        visibleForm,
        handlerOpenForm,
        getEmpresas
    } = useContext(EmpresaContext);

    const { login } = useContext(AuthContext);

    useEffect(() => {
        getEmpresas();
    }, []);

    return (
        <>
            {!visibleForm || <EmpresaModalForm />}

            <div className="container my-4">
                <h2>Empresas</h2>
                <div className="row">
                    <div className="col">
                        { (visibleForm || !login.isAdmin) || (
                            <button
                                className="btn btn-primary my-2"
                                onClick={handlerOpenForm}>
                                Nueva Empresa
                            </button>
                        )}

                        {empresas.length === 0
                            ? <div className="alert alert-warning">No hay empresas registradas!</div>
                            : <EmpresasList />}
                    </div>
                </div>
            </div>
        </>
    );
}
