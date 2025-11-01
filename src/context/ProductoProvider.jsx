import { ProductoContext } from "./ProductoContext";
import {useProductos} from "../hooks/useProductos.js";


export const ProductoProvider = ({ children }) => {
    const {
        productos,
        productoSelected,
        visibleForm,
        getProductos,
        handlerAddProducto,
        handlerRemoveProducto,
        handlerOpenForm,
        handlerCloseForm,
        handlerProductoSelectedForm
    } = useProductos();

    return (
        <ProductoContext.Provider value={{
            productos,
            productoSelected,
            visibleForm,
            getProductos,
            handlerAddProducto,
            handlerRemoveProducto,
            handlerOpenForm,
            handlerCloseForm,
            handlerProductoSelectedForm
        }}>
            {children}
        </ProductoContext.Provider>
    );
};
