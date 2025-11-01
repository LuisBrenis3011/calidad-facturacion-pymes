import { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { ProductoContext } from "../context/ProductoContext";
import { ProductoModalForm } from "../components/producto/ProductoModalForm";
import { ProductosList } from "../components/producto/ProductosList";
import { ComprobanteProvider } from "../context/ComprobanteProvider";
import {ComprobantesSection} from "../components/comprobante/ComprobanteSection.jsx";

export const ProductosPage = () => {
    const { id } = useParams();
    const {
        productos,
        visibleForm,
        handlerOpenForm,
        getProductos
    } = useContext(ProductoContext);

    const [vistaActual, setVistaActual] = useState("productos"); // "productos" | "comprobantes"

    useEffect(() => {
        if (id) {
            getProductos(parseInt(id));
        }
    }, [id]);

    return (
        <ComprobanteProvider empresaId={parseInt(id)}>
            {!visibleForm || <ProductoModalForm empresaId={parseInt(id)} />}

            <div className="container my-4">
                <h2>Gestión de Empresa #{id}</h2>

                <ul className="nav nav-tabs mb-3">
                    <li className="nav-item">
                        <button
                            className={`nav-link ${vistaActual === "productos" ? "active" : ""}`}
                            onClick={() => setVistaActual("productos")}
                        >
                            Productos
                        </button>
                    </li>
                    <li className="nav-item">
                        <button
                            className={`nav-link ${vistaActual === "comprobantes" ? "active" : ""}`}
                            onClick={() => setVistaActual("comprobantes")}
                        >
                            Comprobantes
                        </button>
                    </li>
                </ul>

                {/* Contenido según la vista */}
                {vistaActual === "productos" ? (
                    <div className="row">
                        <div className="col">
                            {!visibleForm && (
                                <button
                                    className="btn btn-primary my-2"
                                    onClick={handlerOpenForm}
                                >
                                    Nuevo Producto
                                </button>
                            )}

                            {productos.length === 0 ? (
                                <div className="alert alert-warning">
                                    No hay productos registrados para esta empresa
                                </div>
                            ) : (
                                <ProductosList empresaId={parseInt(id)} />
                            )}
                        </div>
                    </div>
                ) : (
                    <ComprobantesSection productos={productos} />
                )}
            </div>
        </ComprobanteProvider>
    );
};