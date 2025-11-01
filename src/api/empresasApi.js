import axios from "axios";

const empresasApi = axios.create({
    baseURL: 'http://localhost:8080/empresa'
});

empresasApi.interceptors.request.use(config => {
    config.headers = {
        ...config.headers,
        'Authorization': sessionStorage.getItem('token'),
    }
    return config;
});

export default empresasApi;
