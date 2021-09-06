import {WEEKLY_PLAN_URL, RESOLVE_COURSE_URL} from "../config/config";
import { http } from './http'


export default class PlanService {

    static async getWeeklySchedule() {
        let obj = await http.get(WEEKLY_PLAN_URL);
        return obj.data;
    }

    static async resolveCourse(day, time) {
        let course = await http.post(RESOLVE_COURSE_URL, {day: day, time: time});
        return course.data;
    }
}