import { useContext, useState } from "react";
import { ComprobanteContext } from "../../context/ComprobanteContext";

export const ComprobanteRow = ({ comprobante }) => {
    const { handlerRemoveComprobante, handlerComprobanteSelectedForm } = useContext(ComprobanteContext);
    const [mostrarDetalles, setMostrarDetalles] = useState(false);

    return (
        <>
            <tr>
                <td>
                    <span className={`badge ${comprobante.tipo === "BOLETA" ? "bg-info" : "bg-success"}`}>
                        {comprobante.tipo}
                    </span>
                </td>
                <td>{comprobante.numeroComprobante}</td>
                <td>{comprobante.fecha}</td>
                <td>{comprobante.clienteNombre}</td>
                <td>{comprobante.clienteDocumento}</td>
                <td>S/ {comprobante.total.toFixed(2)}</td>
                <td>
                    <button
                        className="btn btn-info btn-sm me-1"
                        onClick={() => setMostrarDetalles(!mostrarDetalles)}
                        title="Ver detalles"
                    >
                        {mostrarDetalles ? "Ocultar" : "Ver"}
                    </button>
                    <button
                        className="btn btn-secondary btn-sm me-1"
                        onClick={() => handlerComprobanteSelectedForm(comprobante)}
                        title="Editar"
                    >
                        Editar
                    </button>
                    <button
                        className="btn btn-danger btn-sm"
                        onClick={() => handlerRemoveComprobante(comprobante.id)}
                        title="Eliminar"
                    >
                        Eliminar
                    </button>
                </td>
            </tr>
            {mostrarDetalles && (
                <tr>
                    <td colSpan="7" className="bg-light">
                        <div className="p-3">
                            <h6>Detalle del Comprobante</h6>
                            <table className="table table-sm table-bordered">
                                <thead>
                                <tr>
                                    <th>Producto</th>
                                    <th>Cantidad</th>
                                    <th>P. Unitario</th>
                                    <th>Subtotal</th>
                                </tr>
                                </thead>
                                <tbody>
                                {comprobante.items.map((item, index) => (
                                    <tr key={index}>
                                        <td>{item.nombre}</td>
                                        <td>{item.cantidad}</td>
                                        <td>S/ {item.valorUnitario.toFixed(2)}</td>
                                        <td>S/ {item.subtotal.toFixed(2)}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>
            )}
        </>
    );
};