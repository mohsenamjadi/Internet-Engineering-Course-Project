import {LOGIN_URL, SIGNUP_URL, FORGET_PASSWORD_URL, RESET_PASSWORD_URL} from "../config/config";
import { http } from './http'

export default class AuthService {

    static signup(credentials) {
        return http.post(SIGNUP_URL, credentials);
    }

    static login(credentials) {
        return http.post(LOGIN_URL, credentials);
    }

    static getUserToken(){
        console.log(localStorage.getItem("token"));
        return localStorage.getItem("token");
    }

    static getAuthHeader() {
        return 'Bearer ' + this.getUserToken();
    }

    static logout() {
        localStorage.removeItem("token");
    }

    static forgetPassword(credentials) {
        return http.post(FORGET_PASSWORD_URL, credentials);
    }

    static resetPassword(credentials, token) {
        return http.post(RESET_PASSWORD_URL + token, credentials);
    }


}