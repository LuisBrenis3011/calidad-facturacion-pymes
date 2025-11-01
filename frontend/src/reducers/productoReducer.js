export const productoReducer = (state = [], action) => {
    switch (action.type) {
        case "loadingProductos":
            return action.payload;

        case "addProducto":
            return [...state, action.payload];

        case "updateProducto":
            return state.map((producto) =>
                producto.id === action.payload.id
                    ? action.payload
                    : producto
            );

        case "removeProducto":
            return state.filter((producto) => producto.id !== action.payload);

        default:
            return state;
    }
};
