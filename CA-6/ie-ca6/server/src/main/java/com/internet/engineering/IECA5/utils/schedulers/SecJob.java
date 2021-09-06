package com.internet.engineering.IECA5.utils.schedulers;

import com.internet.engineering.IECA5.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA5.domain.CourseEnrolment.Student.WeeklyScheduleItem;
import com.internet.engineering.IECA5.repository.MzRepository;

import java.util.stream.Collectors;

public class SecJob implements Runnable {

    @Override
    public void run() {
        try {
            MzRepository.getInstance().getAllOWeeklySchedules().
            stream().
            filter(weeklyScheduleItem -> weeklyScheduleItem.getStatus().contains("pending")).
            collect(Collectors.toList()).
            forEach(weeklyScheduleItem -> {
                try {
                    weeklyScheduleItem.handleWaitingList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
        }
    }
}