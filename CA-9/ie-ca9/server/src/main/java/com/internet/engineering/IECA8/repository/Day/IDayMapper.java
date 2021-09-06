package com.internet.engineering.IECA8.repository.Day;

import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Day;
import com.internet.engineering.IECA8.repository.IMapper;
import com.internet.engineering.IECA8.utils.CustomPair;

import java.sql.SQLException;
import java.util.List;

public interface IDayMapper extends IMapper<Day, CustomPair> {
        List<Day> getAll() throws SQLException;
        List<Day> search(CustomPair id) throws SQLException;
}
