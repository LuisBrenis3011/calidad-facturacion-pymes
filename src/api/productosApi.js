import axios from "axios";

const productosApi = axios.create({
    baseURL: "http://localhost:8080/producto"
});

productosApi.interceptors.request.use(config => {
    config.headers = {
        ...config.headers,
        'Authorization': sessionStorage.getItem('token'),
    };
    return config;
});

export default productosApi;
