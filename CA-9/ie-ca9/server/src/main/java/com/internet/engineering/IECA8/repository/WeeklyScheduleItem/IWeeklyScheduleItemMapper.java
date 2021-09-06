package com.internet.engineering.IECA8.repository.WeeklyScheduleItem;

import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.WeeklyScheduleItem;
import com.internet.engineering.IECA8.repository.IMapper;
import com.internet.engineering.IECA8.utils.CustomPair;

import java.sql.SQLException;
import java.util.List;

public interface IWeeklyScheduleItemMapper extends IMapper<WeeklyScheduleItem, CustomPair> {
        List<WeeklyScheduleItem> getAll() throws SQLException;
        void updateWeeklyScheduleItem(WeeklyScheduleItem weeklyScheduleItem) throws SQLException;
        List<WeeklyScheduleItem> getLastSubmit(String studentId) throws SQLException;
        void emptyWeeklySchedule(CustomPair id) throws SQLException;
        List<WeeklyScheduleItem> getNonFinalized(CustomPair id) throws SQLException;
        void updateWeeklyScheduleItemVisibility(WeeklyScheduleItem weeklyScheduleItem) throws SQLException;
}
