import { useComprobantes } from "../hooks/useComprobantes";
import { ComprobanteContext } from "./ComprobanteContext";

export const ComprobanteProvider = ({ empresaId, children }) => {
    const {
        comprobantes,
        comprobanteSelected,
        visibleForm,
        errors,
        initialComprobanteForm,
        getComprobantes,
        handlerAddComprobante,
        handlerRemoveComprobante,
        handlerOpenForm,
        handlerCloseForm,
        handlerComprobanteSelectedForm
    } = useComprobantes(empresaId);

    return (
        <ComprobanteContext.Provider value={{
            comprobantes,
            comprobanteSelected,
            visibleForm,
            errors,
            initialComprobanteForm,
            getComprobantes,
            handlerAddComprobante,
            handlerRemoveComprobante,
            handlerOpenForm,
            handlerCloseForm,
            handlerComprobanteSelectedForm
        }}>
            {children}
        </ComprobanteContext.Provider>
    );
};