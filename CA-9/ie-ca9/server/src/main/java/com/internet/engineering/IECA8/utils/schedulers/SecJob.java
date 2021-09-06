package com.internet.engineering.IECA8.utils.schedulers;

import com.internet.engineering.IECA8.repository.Repository;

import java.util.stream.Collectors;

public class SecJob implements Runnable {

    @Override
    public void run() {
        try {
            Repository.getInstance().getAllOWeeklySchedules().
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