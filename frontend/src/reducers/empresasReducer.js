export const empresasReducer = (state = [], action) => {
    switch (action.type) {
        case 'addEmpresa':
            return [
                ...state,
                { ...action.payload }
            ];
        case 'removeEmpresa':
            return state.filter(empresa => empresa.id !== action.payload);
        case 'updateEmpresa':
            return state.map(e =>
                e.id === action.payload.id ? { ...action.payload } : e
            );
        case 'loadingEmpresas':
            return action.payload;
        default:
            return state;
    }
}