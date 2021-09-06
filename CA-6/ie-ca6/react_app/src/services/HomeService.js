import {COURSES_URL, HOME_URL, PROFILE_URL , PROFILE_GPA_URL , PROFILE_TOTAL_PASSED_URL} from "../config/config";
import Translator from "../utils/Translator";

const axios = require("axios").default;


export default class HomeService {


    static async getStudentProfile() {
        let student = await axios.get(PROFILE_URL);
        console.log(student);
        return student.data;
    }

    static async getStudentGPA() {
        let GPA = await axios.get(PROFILE_GPA_URL);
        console.log(GPA);
        return GPA.data;
    }

    static async getStudentPassedUnits() {
        let units = await axios.get(PROFILE_TOTAL_PASSED_URL);
        console.log(units);
        return units.data;
    }

    static async getCourseById(id) {
        let course = await axios.get(COURSES_URL + id);
        if(course.data !== "")
            return course.data;
        else
            return null;
    }
}