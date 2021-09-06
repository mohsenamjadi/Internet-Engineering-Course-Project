package com.internet.engineering.IECA8.repository.Course;

import com.internet.engineering.IECA8.utils.CustomPair;
import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA8.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface ICourseMapper extends IMapper<Offering, CustomPair> {
        List<Offering> getAll() throws SQLException;
        Offering getOfferingByCode(CustomPair id) throws SQLException;
        void updateSignedUp(Offering offering) throws SQLException;
        void updateCapacity(Offering offering) throws SQLException;
}
