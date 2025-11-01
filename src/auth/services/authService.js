import axios from "axios";

export const loginUser = async ({username, password}) => {
    // eslint-disable-next-line no-useless-catch
    try{
        return await axios.post('http://localhost:8080/login',
            {username, password})
    }catch (error) {
        throw error;
    }
}