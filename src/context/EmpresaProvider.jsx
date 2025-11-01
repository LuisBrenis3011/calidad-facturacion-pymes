import { EmpresaContext } from "./EmpresaContext";
import { useEmpresas } from "../hooks/useEmpresas";

export const EmpresaProvider = ({ children }) => {

    const {
        empresas,
        empresaSelected,
        visibleForm,
        initialEmpresaForm,
        errors,
        handlerAddEmpresa,
        handlerRemoveEmpresa,
        handlerEmpresaSelectedForm,
        handlerOpenForm,
        handlerCloseForm,
        getEmpresas
    } = useEmpresas();

    return (
        <EmpresaContext.Provider value={{
            empresas,
            empresaSelected,
            visibleForm,
            initialEmpresaForm,
            errors,
            handlerAddEmpresa,
            handlerRemoveEmpresa,
            handlerEmpresaSelectedForm,
            handlerOpenForm,
            handlerCloseForm,
            getEmpresas
        }}>
            {children}
        </EmpresaContext.Provider>
    )
}
