import {COURSES_URL, HOME_URL, PROFILE_URL , PROFILE_GPA_URL , PROFILE_TOTAL_PASSED_URL} from "../config/config";
import Translator from "../utils/Translator";
import { http } from './http'


export default class HomeService {


    static async getStudentProfile() {
        let student = await http.get(PROFILE_URL);
        console.log(student);
        return student.data;
    }

    static async getStudentGPA() {
        let GPA = await http.get(PROFILE_GPA_URL);
        console.log(GPA);
        return GPA.data;
    }

    static async getStudentPassedUnits() {
        let units = await http.get(PROFILE_TOTAL_PASSED_URL);
        console.log(units);
        return units.data;
    }

    static async getCourseById(id) {
        let course = await http.get(COURSES_URL + id);
        if(course.data !== "")
            return course.data;
        else
            return null;
    }
}