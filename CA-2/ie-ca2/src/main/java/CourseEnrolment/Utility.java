package CourseEnrolment;

public class Utility {
    public static class Memory{
        private static CourseEnrolment courseEnrolment;

        public static CourseEnrolment getCourseEnrolment(){return courseEnrolment;}
        public static void setCourseEnrolment(CourseEnrolment courseEnrolment_){courseEnrolment = courseEnrolment_;}
    }
}
