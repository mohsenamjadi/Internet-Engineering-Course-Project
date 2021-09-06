import {WEEKLY_PLAN_URL, RESOLVE_COURSE_URL} from "../config/config";

const axios = require("axios").default;

export default class PlanService {

    static async getWeeklySchedule() {
        let obj = await axios.get(WEEKLY_PLAN_URL);
        return obj.data;
    }

    static async resolveCourse(day, time) {
        let course = await axios.post(RESOLVE_COURSE_URL, {day: day, time: time});
        return course.data;
    }
}