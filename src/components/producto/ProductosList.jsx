import { useContext } from "react";
import { ProductoRow } from "./ProductoRow.jsx";
import {ProductoContext} from "../../context/ProductoContext.jsx";

export const ProductosList = ({ empresaId }) => {
    const { productos } = useContext(ProductoContext);

    return (
        <table className="table table-hover table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Valor Unitario</th>
                <th>Unidad de Medida</th>
                <th>Actualizar</th>
                <th>Eliminar</th>
            </tr>
            </thead>
            <tbody>
            {
                productos.map(({ id, nombre, descripcion, valorUnitario, unidadMedida }) => (
                    <ProductoRow
                        key={id}
                        id={id}
                        nombre={nombre}
                        descripcion={descripcion}
                        valorUnitario={valorUnitario}
                        unidadMedida={unidadMedida}
                        empresaId={empresaId}
                    />
                ))
            }
            </tbody>
        </table>
    );
};
