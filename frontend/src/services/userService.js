import usersApi from "../api/usersApi.js";

const BASE_URL = '';

// const config = () => {
//     return {
//         headers: {
//             'Authorization': sessionStorage.getItem('token'),
//             'Content-Type': 'application/json',
//         }
//     }
// }

export const findAll = async() => {
    try {
        const response = await usersApi.get(BASE_URL);
        return response;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export const save = async ({ username, email, password, admin }) => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await usersApi.post(BASE_URL, {
            username,
            email,
            password,
            admin
        });
    } catch (error) {
        throw error;
    }
}

export const update = async({ id, username, email, admin }) => {
    // eslint-disable-next-line no-useless-catch
    try{
        return await usersApi.put(`${BASE_URL}/${id}`, {
            username,
            email,
            admin,
        });
    } catch (error) {
        throw error;
    }
}

export const remove = async (id) => {
    // eslint-disable-next-line no-useless-catch
    try {
        await usersApi.delete(`${BASE_URL}/${id}`);
    } catch (error) {
        throw error;
    }
}