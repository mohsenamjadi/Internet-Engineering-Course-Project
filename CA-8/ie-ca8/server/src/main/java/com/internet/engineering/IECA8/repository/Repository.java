package com.internet.engineering.IECA8.repository;

import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.Grade;
import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.Student;
import com.internet.engineering.IECA8.domain.CourseEnrolment.Student.WeeklyScheduleItem;
import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Day;
import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Prerequisite;
import com.internet.engineering.IECA8.repository.Course.CourseMapper;
import com.internet.engineering.IECA8.repository.Day.DayMapper;
import com.internet.engineering.IECA8.repository.Grade.GradeMapper;
import com.internet.engineering.IECA8.repository.Prerequisite.PrerequisiteMapper;
import com.internet.engineering.IECA8.repository.Student.StudentMapper;
import com.internet.engineering.IECA8.repository.WeeklyScheduleItem.WeeklyScheduleItemMapper;
import com.internet.engineering.IECA8.utils.CustomPair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository instance;

    private Repository() {
    }

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public void createAllTables() {
        try {
            CourseMapper courseMapper = new CourseMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            StudentMapper studentMapper = new StudentMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GradeMapper gradeMapper = new GradeMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            DayMapper dayMapper = new DayMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PrerequisiteMapper prerequisiteMapper = new PrerequisiteMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            WeeklyScheduleItemMapper weeklyScheduleItemMapper = new WeeklyScheduleItemMapper(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    COURSE
    public void insertCourse(Offering course) throws SQLException {
        CourseMapper courseMapper = new CourseMapper();
        courseMapper.insert(course);
    }

    public Offering findCourseById(String code, String classCode) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(code);
        args.add(classCode);
        return new CourseMapper().find(new CustomPair(args));
    }

    public List<Offering> getAllOfferings() throws SQLException {
        CourseMapper courseMapper = new CourseMapper();
        return courseMapper.getAll();
    }

    public Offering getOfferingByCode(String code) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(code);
        return new CourseMapper().getOfferingByCode(new CustomPair(args));
    }

    public List<Offering> search(String name) throws SQLException {
        return new CourseMapper().search(name);
    }

    public void updateCapacityAndSignedUp(Offering offering) throws SQLException {
        CourseMapper courseMapper = new CourseMapper();
        courseMapper.updateCapacity(offering);
        courseMapper.updateSignedUp(offering);
    }

    //    STUDENT
    public void insertStudent(Student student) throws SQLException {
        StudentMapper studentMapper = new StudentMapper();
        studentMapper.insert(student);
    }

    public List<Student> getAllOStudents() throws SQLException {
        StudentMapper studentMapper = new StudentMapper();
        return studentMapper.getAll();
    }

    public Student getStudent(String id) throws SQLException {
        return new StudentMapper().find(id);
    }

    public Student getStudentByEmail(String email) throws SQLException {
        return new StudentMapper().getStudentByEmail(email);
    }

    public void updateStudentPassword(Student student) throws SQLException {
        new StudentMapper().updateStudentPassword(student);
    }

    //    GRADE
    public List<Grade> getAllGrades() {
        try {
            return new GradeMapper().getAll();
        } catch (SQLException e) {
            return new ArrayList<Grade>();
        }
    }

    public List<Grade> getAllGradesByStudentId(String studentId) {
        try {
            return new GradeMapper().getAllGradesByStudentId(studentId);
        } catch (SQLException e) {
            return new ArrayList<Grade>();
        }
    }

    public Grade findGradesByStudentId(String code, String studentId) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(code);
        args.add(studentId);
        return new GradeMapper().find(new CustomPair(args));
    }

    //    WEEKLYSCHEDULE
    public List<WeeklyScheduleItem> getAllOWeeklySchedules() throws SQLException {
        WeeklyScheduleItemMapper weeklyScheduleItemMapper = new WeeklyScheduleItemMapper();
        return weeklyScheduleItemMapper.getAll();
    }

    public void insertWeeklyScheduleItem(WeeklyScheduleItem weeklyScheduleItem) throws SQLException {
        WeeklyScheduleItemMapper weeklyScheduleItemMapper = new WeeklyScheduleItemMapper();
        weeklyScheduleItemMapper.insert(weeklyScheduleItem);
    }

    public void deleteWeeklyScheduleItem(String studentId, String code, String classCode) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(studentId);
        args.add(code);
        args.add(classCode);
        new WeeklyScheduleItemMapper().delete(new CustomPair(args));
    }

    public void updateWeeklyScheduleItem(WeeklyScheduleItem weeklyScheduleItem) throws SQLException {
        new WeeklyScheduleItemMapper().updateWeeklyScheduleItem(weeklyScheduleItem);
    }

    public List<WeeklyScheduleItem> getLastSubmit(String studentId) throws SQLException {
        return new WeeklyScheduleItemMapper().getLastSubmit(studentId);
    }

    public void emptyWeeklySchedule(String studentId) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(studentId);
        new WeeklyScheduleItemMapper().emptyWeeklySchedule(new CustomPair(args));
    }

    public List<WeeklyScheduleItem> getNonFinalized(String studentId) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(studentId);
        return new WeeklyScheduleItemMapper().getNonFinalized(new CustomPair(args));
    }

    public WeeklyScheduleItem findWeeklyScheduleItem(String code, String classCode, String studenId) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(code);
        args.add(classCode);
        args.add(studenId);
        return new WeeklyScheduleItemMapper().find(new CustomPair(args));
    }

    public void updateWeeklyScheduleItemVisibility(WeeklyScheduleItem weeklyScheduleItem) throws SQLException {
        new WeeklyScheduleItemMapper().updateWeeklyScheduleItemVisibility(weeklyScheduleItem);
    }

    //    PREREQUISITE
    public void insertPrerequisite(Prerequisite prerequisite) throws SQLException {
        PrerequisiteMapper prerequisiteMapper = new PrerequisiteMapper();
        prerequisiteMapper.insert(prerequisite);
    }

    public List<Prerequisite> search(String code, String classCode) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(code);
        args.add(classCode);
        return new PrerequisiteMapper().search(new CustomPair(args));
    }

    //    DAY
    public void insertDay(Day day) throws SQLException {
        DayMapper dayMapper = new DayMapper();
        dayMapper.insert(day);
    }

    public List<Day> searchDays(String code, String classCode) throws SQLException {
        List<String> args = new ArrayList<>();
        args.add(code);
        args.add(classCode);
        return new DayMapper().search(new CustomPair(args));
    }
}
