import {SIGNUP_URL} from "../config/config";
import Translator from "../utils/Translator";

const axios = require("axios").default;


export default class SignUpService {

    static async signup(std_id) {
        try {
            let response = await axios.post(SIGNUP_URL)
            if (response.data !== "" || response.data === undefined)
                return Translator.toFa(response.data);
            else
                return "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }catch (e) {
            return Translator.toFa(e.response.data.message) ||  "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }
    }
}