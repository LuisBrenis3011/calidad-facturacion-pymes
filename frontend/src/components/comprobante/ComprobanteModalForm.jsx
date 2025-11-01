import { useContext, useEffect, useState } from "react";
import {ComprobanteContext} from "../../context/ComprobanteContext.jsx";


export const ComprobanteModalForm = ({ productos }) => {
    const {
        comprobanteSelected,
        handlerAddComprobante,
        handlerCloseForm,
        errors
    } = useContext(ComprobanteContext);

    const [comprobanteForm, setComprobanteForm] = useState(comprobanteSelected);
    const [itemsSeleccionados, setItemsSeleccionados] = useState(comprobanteSelected.items || []);

    useEffect(() => {
        setComprobanteForm(comprobanteSelected);
        setItemsSeleccionados(comprobanteSelected.items || []);
    }, [comprobanteSelected]);

    const onInputChange = ({ target }) => {
        const { name, value } = target;
        setComprobanteForm({
            ...comprobanteForm,
            [name]: value
        });
    };

    const agregarProducto = (producto) => {
        const yaExiste = itemsSeleccionados.find(item => item.productoId === producto.id);

        if (yaExiste) {
            // Si ya existe, aumentar cantidad
            setItemsSeleccionados(
                itemsSeleccionados.map(item =>
                    item.productoId === producto.id
                        ? { ...item, cantidad: item.cantidad + 1, subtotal: (item.cantidad + 1) * item.valorUnitario }
                        : item
                )
            );
        } else {
            // Agregar nuevo item
            const nuevoItem = {
                productoId: producto.id,
                nombre: producto.nombre,
                valorUnitario: parseFloat(producto.valorUnitario),
                cantidad: 1,
                subtotal: parseFloat(producto.valorUnitario)
            };
            setItemsSeleccionados([...itemsSeleccionados, nuevoItem]);
        }
    };

    const actualizarCantidad = (productoId, cantidad) => {
        const cantidadNum = parseInt(cantidad);
        if (cantidadNum <= 0) {
            eliminarItem(productoId);
            return;
        }

        setItemsSeleccionados(
            itemsSeleccionados.map(item =>
                item.productoId === productoId
                    ? { ...item, cantidad: cantidadNum, subtotal: cantidadNum * item.valorUnitario }
                    : item
            )
        );
    };

    const eliminarItem = (productoId) => {
        setItemsSeleccionados(itemsSeleccionados.filter(item => item.productoId !== productoId));
    };

    const calcularTotal = () => {
        return itemsSeleccionados.reduce((sum, item) => sum + item.subtotal, 0);
    };

    const onSubmit = (event) => {
        event.preventDefault();

        const comprobanteCompleto = {
            ...comprobanteForm,
            items: itemsSeleccionados,
            total: calcularTotal()
        };

        handlerAddComprobante(comprobanteCompleto);
    };

    return (
        <div className="modal" style={{ display: "block", backgroundColor: "rgba(0,0,0,0.5)" }}>
            <div className="modal-dialog modal-lg">
                <div className="modal-content">
                    <form onSubmit={onSubmit}>
                        <div className="modal-header">
                            <h5 className="modal-title">
                                {comprobanteForm.id > 0 ? "Editar" : "Crear"} Comprobante
                            </h5>
                            <button
                                type="button"
                                className="btn-close"
                                onClick={handlerCloseForm}
                            ></button>
                        </div>

                        <div className="modal-body">
                            {/* Tipo de comprobante */}
                            <div className="mb-3">
                                <label className="form-label">Tipo de Comprobante</label>
                                <select
                                    className="form-select"
                                    name="tipo"
                                    value={comprobanteForm.tipo}
                                    onChange={onInputChange}
                                >
                                    <option value="BOLETA">Boleta</option>
                                    <option value="FACTURA">Factura</option>
                                </select>
                            </div>

                            {/* Fecha */}
                            <div className="mb-3">
                                <label className="form-label">Fecha</label>
                                <input
                                    type="date"
                                    className="form-control"
                                    name="fecha"
                                    value={comprobanteForm.fecha}
                                    onChange={onInputChange}
                                />
                            </div>

                            {/* Cliente */}
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label className="form-label">Nombre del Cliente</label>
                                    <input
                                        type="text"
                                        className={`form-control ${errors.clienteNombre ? "is-invalid" : ""}`}
                                        name="clienteNombre"
                                        placeholder="Nombre completo"
                                        value={comprobanteForm.clienteNombre}
                                        onChange={onInputChange}
                                    />
                                    {errors.clienteNombre && (
                                        <div className="invalid-feedback">{errors.clienteNombre}</div>
                                    )}
                                </div>

                                <div className="col-md-6 mb-3">
                                    <label className="form-label">
                                        {comprobanteForm.tipo === "FACTURA" ? "RUC" : "DNI"}
                                    </label>
                                    <input
                                        type="text"
                                        className={`form-control ${errors.clienteDocumento ? "is-invalid" : ""}`}
                                        name="clienteDocumento"
                                        placeholder={comprobanteForm.tipo === "FACTURA" ? "RUC" : "DNI"}
                                        value={comprobanteForm.clienteDocumento}
                                        onChange={onInputChange}
                                    />
                                    {errors.clienteDocumento && (
                                        <div className="invalid-feedback">{errors.clienteDocumento}</div>
                                    )}
                                </div>
                            </div>

                            {/* Selección de productos */}
                            <div className="mb-3">
                                <label className="form-label">Agregar Productos</label>
                                <select
                                    className="form-select"
                                    onChange={(e) => {
                                        const productoId = parseInt(e.target.value);
                                        const producto = productos.find(p => p.id === productoId);
                                        if (producto) {
                                            agregarProducto(producto);
                                            e.target.value = ""; // Resetear select
                                        }
                                    }}
                                >
                                    <option value="">Seleccione un producto</option>
                                    {productos.map(producto => (
                                        <option key={producto.id} value={producto.id}>
                                            {producto.nombre} - S/ {producto.valorUnitario}
                                        </option>
                                    ))}
                                </select>
                                {errors.items && (
                                    <small className="text-danger">{errors.items}</small>
                                )}
                            </div>

                            {/* Items seleccionados */}
                            {itemsSeleccionados.length > 0 && (
                                <div className="table-responsive">
                                    <table className="table table-sm">
                                        <thead>
                                        <tr>
                                            <th>Producto</th>
                                            <th>P. Unit.</th>
                                            <th>Cant.</th>
                                            <th>Subtotal</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {itemsSeleccionados.map(item => (
                                            <tr key={item.productoId}>
                                                <td>{item.nombre}</td>
                                                <td>S/ {item.valorUnitario.toFixed(2)}</td>
                                                <td>
                                                    <input
                                                        type="number"
                                                        className="form-control form-control-sm"
                                                        style={{ width: "70px" }}
                                                        min="1"
                                                        value={item.cantidad}
                                                        onChange={(e) =>
                                                            actualizarCantidad(item.productoId, e.target.value)
                                                        }
                                                    />
                                                </td>
                                                <td>S/ {item.subtotal.toFixed(2)}</td>
                                                <td>
                                                    <button
                                                        type="button"
                                                        className="btn btn-danger btn-sm"
                                                        onClick={() => eliminarItem(item.productoId)}
                                                    >
                                                        X
                                                    </button>
                                                </td>
                                            </tr>
                                        ))}
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th colSpan="3" className="text-end">TOTAL:</th>
                                            <th>S/ {calcularTotal().toFixed(2)}</th>
                                            <th></th>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            )}
                        </div>

                        <div className="modal-footer">
                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={handlerCloseForm}
                            >
                                Cerrar
                            </button>
                            <button type="submit" className="btn btn-primary">
                                {comprobanteForm.id > 0 ? "Actualizar" : "Crear"}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};