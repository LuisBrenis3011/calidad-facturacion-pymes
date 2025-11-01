import { useContext, useEffect, useState } from "react";
import {ProductoContext} from "../../context/ProductoContext.jsx";

export const ProductoForm = ({ productoSelected, handlerCloseForm, empresaId }) => {
    const { initialProductoForm, handlerAddProducto, errors } = useContext(ProductoContext);
    const [productoForm, setProductoForm] = useState(initialProductoForm);

    useEffect(() => {
        if (productoSelected) {
            setProductoForm({ ...productoSelected });
        } else {
            setProductoForm(initialProductoForm);
        }
    }, [initialProductoForm, productoSelected]);

    if (!productoForm) return null; 

    const { id, nombre, descripcion, valorUnitario, unidadMedida } = productoForm;

    const onInputChange = ({ target }) => {
        const { name, value } = target;
        setProductoForm({
            ...productoForm,
            [name]: value,
        });
    };

    const onSubmit = (event) => {
        event.preventDefault();
        handlerAddProducto(empresaId ,productoForm);
    };

    const onCloseForm = () => {
        handlerCloseForm();
        setProductoForm(initialProductoForm);
    };

    return (
        <form onSubmit={onSubmit}>
            <input
                className="form-control my-3 w-75"
                placeholder="Nombre"
                name="nombre"
                value={nombre}
                onChange={onInputChange}
            />
            <p className="text-danger">{errors?.nombre}</p>

            <textarea
                className="form-control my-3 w-75"
                placeholder="Descripción"
                name="descripcion"
                value={descripcion}
                onChange={onInputChange}
            />
            <p className="text-danger">{errors?.descripcion}</p>

            <input
                className="form-control my-3 w-75"
                placeholder="Valor Unitario"
                name="valorUnitario"
                type="number"
                value={valorUnitario}
                onChange={onInputChange}
            />
            <p className="text-danger">{errors?.valorUnitario}</p>

            <input
                className="form-control my-3 w-75"
                placeholder="Unidad de Medida"
                name="unidadMedida"
                value={unidadMedida}
                onChange={onInputChange}
            />
            <p className="text-danger">{errors?.unidadMedida}</p>

            <input type="hidden" name="id" value={id} />

            <button className="btn btn-primary" type="submit">
                {id > 0 ? "Editar" : "Crear"}
            </button>

            {!handlerCloseForm || (
                <button
                    className="btn btn-secondary mx-2"
                    type="button"
                    onClick={() => onCloseForm()}>
                    Cerrar
                </button>
            )}
        </form>
    );
};
