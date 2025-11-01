import { useContext } from "react";
import {ProductoContext} from "../../context/ProductoContext.jsx";

export const ProductoRow = ({ id, nombre, descripcion, valorUnitario, unidadMedida}) => {

    const { handlerProductoSelectedForm, handlerRemoveProducto } = useContext(ProductoContext);

    return (
        <tr>
            <td>{id}</td>
            <td>{nombre}</td>
            <td>{descripcion}</td>
            <td>{valorUnitario}</td>
            <td>{unidadMedida}</td>
            <td>
                <button
                    type="button"
                    className="btn btn-secondary btn-sm"
                    onClick={() => handlerProductoSelectedForm({
                        id,
                        nombre,
                        descripcion,
                        valorUnitario,
                        unidadMedida
                    })}
                >
                    Editar
                </button>
            </td>
            <td>
                <button
                    type="button"
                    className="btn btn-danger btn-sm"
                    onClick={() => handlerRemoveProducto(id)}
                >
                    Eliminar
                </button>
            </td>
        </tr>
    );
};
