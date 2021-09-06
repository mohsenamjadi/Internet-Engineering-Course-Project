//package com.internet.engineering.IECA5.utils.schedulers;
//
//import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
//
//public class MinJob implements Runnable {
//
//    @Override
//    public void run() {
//        try {
//            CourseEnrolment.getInstance().getCourses()
//            .stream().forEach(course -> {
//                course.handleWaitingList();
//            });
//        } catch (Exception e) {
//        }
//    }
//}