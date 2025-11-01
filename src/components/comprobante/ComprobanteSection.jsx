import { useContext } from "react";
import { ComprobanteContext } from "../../context/ComprobanteContext";
import { ComprobanteModalForm } from "./ComprobanteModalForm";
import {ComprobantesList} from "./ComprobanteList.jsx";


export const ComprobantesSection = ({ productos }) => {
    const {
        comprobantes,
        visibleForm,
        handlerOpenForm
    } = useContext(ComprobanteContext);

    return (
        <>
            {visibleForm && <ComprobanteModalForm productos={productos} />}

            <div className="row">
                <div className="col">
                    {!visibleForm && (
                        <button
                            className="btn btn-success my-2"
                            onClick={handlerOpenForm}
                        >
                            Crear Comprobante
                        </button>
                    )}

                    {comprobantes.length === 0 ? (
                        <div className="alert alert-info">
                            No hay comprobantes registrados para esta empresa
                        </div>
                    ) : (
                        <ComprobantesList />
                    )}
                </div>
            </div>
        </>
    );
};