import { useReducer, useState } from "react";
import { productoReducer } from "../reducers/productoReducer.js";
import { findByEmpresa, save, update, remove } from "../services/productoService.js";
import Swal from "sweetalert2";

const initialProductoForm = {
    id: 0,
    nombre: "",
    descripcion: "",
    valorUnitario: "",
    unidadMedida: ""
};

export const useProductos = () => {

    const [productos, dispatch] = useReducer(productoReducer, []);
    const [productoSelected, setProductoSelected] = useState(initialProductoForm);
    const [visibleForm, setVisibleForm] = useState(false);
    const [errors, setErrors] = useState({});

    // getProductos recibe el empresaId
    const getProductos = async (empresaId) => {
        if (!empresaId) return;
        try {
            const result = await findByEmpresa(empresaId);
            dispatch({
                type: "loadingProductos",
                payload: result.data
            });
        } catch (error) {
            console.error("Error al obtener productos:", error);
        }
    };

    // Guardar o actualizar producto
    const handlerAddProducto = async (empresaId, producto) => {
        if (!producto.nombre || !producto.descripcion || !producto.valorUnitario || !producto.unidadMedida) {
            setErrors({
                nombre: !producto.nombre ? "El nombre es obligatorio" : "",
                descripcion: !producto.descripcion ? "La descripción es obligatoria" : "",
                valorUnitario: !producto.valorUnitario ? "El valor unitario es obligatorio" : "",
                unidadMedida: !producto.unidadMedida ? "La unidad de medida es obligatoria" : ""
            });
            return;
        }

        try {
            let response;
            if (producto.id === 0) {
                response = await save(empresaId, producto);
                dispatch({ type: "addProducto", payload: response.data });
                await Swal.fire("Producto creado", "El producto fue registrado con éxito", "success");
            } else {
                response = await update(empresaId, producto);
                dispatch({ type: "updateProducto", payload: response.data });
                await Swal.fire("Producto actualizado", "El producto fue actualizado con éxito", "info");
            }
            handlerCloseForm();
            setErrors({});
        } catch (error) {
            console.error(error);
            await Swal.fire("Error", "No se pudo guardar el producto", "error");
        }
    };

    // Eliminar producto
    const handlerRemoveProducto = async (empresaId, id) => {
        Swal.fire({
            title: "¿Estás seguro?",
            text: "No podrás revertir esto",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Sí, eliminar",
            cancelButtonText: "Cancelar"
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await remove(empresaId, id);
                    dispatch({ type: "removeProducto", payload: id });
                    await Swal.fire("Eliminado", "El producto fue eliminado", "success");
                } catch (error) {
                    console.error(error);
                    await Swal.fire("Error", "No se pudo eliminar el producto", "error");
                }
            }
        });
    };

    const handlerOpenForm = () => setVisibleForm(true);
    const handlerCloseForm = () => {
        setVisibleForm(false);
        setProductoSelected(initialProductoForm);
    };

    const handlerProductoSelectedForm = (producto) => {
        setProductoSelected(producto);
        setVisibleForm(true);
    };

    return {
        productos,
        productoSelected,
        visibleForm,
        errors,
        initialProductoForm,

        getProductos,
        handlerAddProducto,
        handlerRemoveProducto,
        handlerOpenForm,
        handlerCloseForm,
        handlerProductoSelectedForm
    };
};
