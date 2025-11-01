
export const usersReducer = (state = [], action) => {

    switch (action.type) {
        case 'addUser':

            return [
                ...state,
                {
                    ...action.payload,
                }
            ];
        case 'removeUser':
            return state.filter(user => user.id !== action.payload);
        case 'updateUser':
            return state.map(u => {
                if (u.id === action.payload.id) {
                    return {
                        ...action.payload,
                        password: u.password
                    };
                }
                return u;
            })
        case 'loadingUsers':
            return action.payload;
        default:
            return state;
    }
}