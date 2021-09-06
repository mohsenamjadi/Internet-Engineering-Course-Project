import {
    COURSES_URL,
    COURSES_ADD_URL,
    COURSES_DELETE_URL,
    COURSES_SUBMIT_URL,
    COURSES_RESET_URL,
    COURSES_SEARCH_URL,
    COURSES_SELECTED_URL,
    COURSES_PRESENTED_BY_TYPE_URL,
    COURSES_LAST_SUBMITED_UNITS_URL
} from "../config/config";
import Translator from "../utils/Translator";
import { http } from './http'


export default class CoursesService {

    static async getCourses() {
        let courses = await http.get(COURSES_URL);
        console.log(courses);
        return courses.data;
    }

    static async getSelectedCourses() {
        let selectedcourses = await http.get(COURSES_SELECTED_URL);
        console.log(selectedcourses);
        return selectedcourses.data;
    }

    static async getPresentedCoursesByType(filter) {
        let presentCoursesByType = await http.post(COURSES_PRESENTED_BY_TYPE_URL, {filter: filter});
        console.log(presentCoursesByType);
        return presentCoursesByType.data;
    }

    static async getPresentedCoursesByName() {
        let presentCoursesByType = await http.get(COURSES_SEARCH_URL);
        console.log(presentCoursesByType);
        return presentCoursesByType.data;
    }

    static async setSearchFilter(filter) {
        return await http.post(COURSES_SEARCH_URL, {filter: filter});
    }

    static async addCourseToSelected(courseId, classCode) {
        try {
            let response = await http.post(COURSES_ADD_URL, {courseId: courseId, classCode: classCode})
            if (response.data !== "" || response.data === undefined)
                return Translator.toFa(response.data);
            else
                return "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }catch (e) {
            return Translator.toFa(e.response.data.message) ||  "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }

    }

    static async removeFromSelectedCourses(courseId, classCode) {
        try {
            let response = await http.delete(COURSES_DELETE_URL, {data: {courseId: courseId, classCode: classCode}})
            if (response.data !== "" || response.data === undefined)
                return Translator.toFa(response.data);
            else
                return "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }catch (e) {
            return Translator.toFa(e.response.data.message) ||  "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }
    }

    static async courseSubmitSelected() {
        try {
            let response = await http.post(COURSES_SUBMIT_URL);
            console.log(response);
            if (response.data !== "" || response.data === undefined)
                return Translator.toFa(response.data);
            else
                return "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }catch (e) {
            return Translator.toFa(e.response.data.message) ||  "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }
    }

    static async courseResetSelected() {
        try {
            let response = await http.post(COURSES_RESET_URL);
            console.log(response);
            if (response.data !== "" || response.data === undefined)
                return Translator.toFa(response.data);
            else
                return "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }catch (e) {
            return Translator.toFa(e.response.data.message) ||  "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }

    }

    static async searchCourse(search) {
        try {
            let response = await http.post(COURSES_SEARCH_URL, {search: search})
            if (response.data !== "" || response.data === undefined)
                return Translator.toFa(response.data);
            else
                return "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }catch (e) {
            return Translator.toFa(e.response.data.message) ||  "مشکلی پیش آمده! لطفا دوباره تلاش کنید";
        }
    }


    static async getLastSumbitedCoursesUnits() {
        let sumOfUnits = await http.get(COURSES_LAST_SUBMITED_UNITS_URL);
        console.log(sumOfUnits);
        return sumOfUnits.data;
    }


}