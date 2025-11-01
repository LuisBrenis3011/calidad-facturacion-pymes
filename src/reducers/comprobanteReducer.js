export const comprobanteReducer = (state = [], action) => {
    switch (action.type) {
        case 'loadingComprobantes':
            return action.payload;

        case 'addComprobante':
            return [
                ...state,
                {
                    ...action.payload,
                }
            ];

        case 'updateComprobante':
            return state.map(comprobante => {
                if (comprobante.id === action.payload.id) {
                    return {
                        ...action.payload,
                    };
                }
                return comprobante;
            });

        case 'removeComprobante':
            return state.filter(comprobante => comprobante.id !== action.payload);

        default:
            return state;
    }
}