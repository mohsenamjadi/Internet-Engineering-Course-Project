import axios from 'axios';
import AuthService from "./AuthService";
import {toast} from "react-toastify";

let instance = axios.create();
toast.configure({rtl: true, className: "text-center"});

let domain_url = "http://localhost:8080/";
let auth_url = "auth";

instance.interceptors.request.use((config) => {
    if (!config.url.startsWith(domain_url + auth_url)) {
        config.headers = {Authorization: AuthService.getAuthHeader()};
    }
    return config;
})

instance.interceptors.response.use(response => {
    return response;
}, error => {
    if (error.response.status === 403 ) {
        window.location = "/login";
        localStorage.removeItem("token");
    } else {
        return Promise.reject(error);
    }
})

export const http = instance;