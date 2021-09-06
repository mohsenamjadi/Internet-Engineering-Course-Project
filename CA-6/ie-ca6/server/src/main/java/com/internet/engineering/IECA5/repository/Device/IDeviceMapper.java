package com.internet.engineering.IECA5.repository.Device;

import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Device;
import com.internet.engineering.IECA5.utils.CustomPair;
import com.internet.engineering.IECA5.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA5.repository.IMapper;

import java.sql.SQLException;
import java.util.List;

public interface IDeviceMapper extends IMapper<Device, CustomPair> {
    List<Device> getAll() throws SQLException;
    Device getDeviceById(CustomPair id) throws SQLException;
}

