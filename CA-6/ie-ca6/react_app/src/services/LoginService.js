import {LOGIN_URL} from "../config/config";
import Translator from "../utils/Translator";

const axios = require("axios").default;


export default class LoginService {

    static async login(std_id) {
        try {
            let response = await axios.post(LOGIN_URL, {std_id: std_id})
            if (response.data !== "" || response.data === undefined)
                return Translator.toFa(response.data);
            else
                return "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }catch (e) {
            return Translator.toFa(e.response.data.message) ||  "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }
    }
}