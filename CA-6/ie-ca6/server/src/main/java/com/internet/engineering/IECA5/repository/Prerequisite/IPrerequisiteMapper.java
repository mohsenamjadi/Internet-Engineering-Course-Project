package com.internet.engineering.IECA5.repository.Prerequisite;

import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Day;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Prerequisite;
import com.internet.engineering.IECA5.repository.IMapper;
import com.internet.engineering.IECA5.utils.CustomPair;

import java.sql.SQLException;
import java.util.List;

public interface IPrerequisiteMapper extends IMapper<Prerequisite, CustomPair> {
        List<Prerequisite> getAll() throws SQLException;
        List<Prerequisite> search(CustomPair id) throws SQLException;
}
