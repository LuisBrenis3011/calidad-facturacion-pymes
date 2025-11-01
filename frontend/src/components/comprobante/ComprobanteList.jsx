import { useContext } from "react";
import { ComprobanteContext } from "../../context/ComprobanteContext";
import { ComprobanteRow } from "./ComprobanteRow";

export const ComprobantesList = () => {
    const { comprobantes } = useContext(ComprobanteContext);

    return (
        <div className="table-responsive">
            <table className="table table-hover table-striped">
                <thead>
                <tr>
                    <th>Tipo</th>
                    <th>Número</th>
                    <th>Fecha</th>
                    <th>Cliente</th>
                    <th>Documento</th>
                    <th>Total</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                {comprobantes.map((comprobante) => (
                    <ComprobanteRow key={comprobante.id} comprobante={comprobante} />
                ))}
                </tbody>
            </table>
        </div>
    );
};