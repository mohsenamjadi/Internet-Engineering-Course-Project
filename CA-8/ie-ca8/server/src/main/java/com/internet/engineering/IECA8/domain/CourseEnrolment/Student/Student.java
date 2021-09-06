package com.internet.engineering.IECA8.domain.CourseEnrolment.Student;

import com.google.gson.annotations.Expose;
import com.internet.engineering.IECA8.domain.CourseEnrolment.CourseEnrolment;
import com.internet.engineering.IECA8.domain.CourseEnrolment.System.Offering;
import com.internet.engineering.IECA8.repository.Grade.GradeMapper;
import com.internet.engineering.IECA8.repository.Repository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Student {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String secondName;
    @Expose
    private String birthDate;
    @Expose
    private String field;
    @Expose
    private String faculty;
    @Expose
    private String level;
    @Expose
    private String status;
    @Expose
    private String img;
    private String searchFilter;
//    @Expose
//    private List<Grade> grades;
//    private List<WeeklyScheduleItem> lastSubmit;
    @Expose
    private List<Term> terms;
    @Expose
    private String email;
    @Expose
    private String password;

    public Student(String id, String name, String secondName, String birthDate, String field, String faculty, String level, String status, String img, String email, String password) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.field = field;
        this.faculty = faculty;
        this.level = level;
        this.status = status;
        this.img = img;
        this.searchFilter = "";
        this.email = email;
        this.password = password;

        this.terms = new ArrayList<>();
    }

    public Student() {
//        this.grades = new ArrayList<>();
//        this.lastSubmit = new ArrayList<>();
        this.searchFilter = "";
        this.terms = new ArrayList<>();
    }

    public void setSearchFilter (String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public String getSearchFilter () {
        return searchFilter;
    }

    public List<WeeklyScheduleItem> getSelectedCourses() throws Exception {
        return CourseEnrolment.getInstance().getWeeklySchedule(id, true);
    }

    public Offering resolveCourse(String day, String time) throws Exception {
        try {
            return getLastSubmit().stream().
                filter(weeklyScheduleItem -> weeklyScheduleItem.getOffering().getClassTime().getDays().contains(day) && weeklyScheduleItem.getOffering().getClassTime().getTime().equals(time)).
                findFirst().get().
                getOffering();
        } catch (NoSuchElementException e) {
        } catch (NullPointerException e) {
        }

        return null;
    }


    private boolean ifAlreadyInWeeklySchdule(WeeklyScheduleItem weeklyScheduleItem){
        try {
            getSelectedCourses().stream().
            filter(iter -> iter.getOffering().getCode().equals(weeklyScheduleItem.getOffering().getCode()) && iter.getOffering().getClassCode().equals(weeklyScheduleItem.getOffering().getClassCode())).
            findFirst().get();
        } catch (NoSuchElementException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void setSelectedCourses(List<WeeklyScheduleItem> selectedCourses) throws Exception {
        CourseEnrolment.getInstance().getWeeklySchedule(CourseEnrolment.getInstance().getStudent().getStudentId(), false).
        stream().
        filter(weeklyScheduleItem -> weeklyScheduleItem.getStatus().equals("finalized")).
        collect(Collectors.toList()).
        forEach(weeklyScheduleItem -> {
            try {
                weeklyScheduleItem.setVisibility("true");
                Repository.getInstance().updateWeeklyScheduleItemVisibility(weeklyScheduleItem);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        CourseEnrolment.getInstance().getWeeklySchedule(CourseEnrolment.getInstance().getStudent().getStudentId(), false).
                stream().
                filter(weeklyScheduleItem -> weeklyScheduleItem.getVisibility().equals("false")).
                collect(Collectors.toList()).
                forEach(weeklyScheduleItem -> {
                    try {
                        CourseEnrolment.getInstance().removeFromWeeklySchedule(
                                CourseEnrolment.getInstance().getStudent().getStudentId(),
                                weeklyScheduleItem.getOffering().getCode(),
                                weeklyScheduleItem.getOffering().getClassCode()
                        );
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

//        if(selectedCourses.isEmpty())
//            CourseEnrolment.getInstance().emptyWeeklySchedule(id);
//
//        for (WeeklyScheduleItem weeklyScheduleItem: selectedCourses)
//            if(!ifAlreadyInWeeklySchdule(weeklyScheduleItem))
//                CourseEnrolment.getInstance().addToWeeklySchedule(
//                        weeklyScheduleItem
//                );
//
        for (WeeklyScheduleItem weeklyScheduleItem: Repository.getInstance().getNonFinalized(id))
            if(weeklyScheduleItem.getStatus().contains("non-finalized"))
                CourseEnrolment.getInstance().removeFromWeeklySchedule(
                        CourseEnrolment.getInstance().getStudent().getStudentId(),
                        weeklyScheduleItem.getOffering().getCode(),
                        weeklyScheduleItem.getOffering().getClassCode()
                );
    }

    public List<WeeklyScheduleItem> getLastSubmit() {
        try {
            return Repository.getInstance().getLastSubmit(id);
        } catch (SQLException sqlException) {
            return null;
        }
    }

//    public void setLastSubmit(List<WeeklyScheduleItem> selectedCourses) {
//        this.lastSubmit = selectedCourses.stream()
//            .filter(weeklyScheduleItem -> weeklyScheduleItem.getStatus().equals("finalized"))
//            .collect(Collectors.toList());
//    }

    public void addToLastSubmit(WeeklyScheduleItem weeklyScheduleItem) {
//        this.lastSubmit.add(weeklyScheduleItem);
        weeklyScheduleItem.finalizeWeeklyScheduleItem();
    }

    public String getStudentId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getField() {
        return field;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getLevel() {
        return level;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return img;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getGpa() throws Exception{
        double sum = 0;
        int coursesUnits = 0;

        for (Grade grade : Repository.getInstance().getAllGradesByStudentId(id)){
            int courseUnits = CourseEnrolment.getInstance().getOffering(grade.getCode()).getUnits();
            coursesUnits += courseUnits;
            sum += grade.getGrade()*courseUnits;
        }
        double gpa = sum/coursesUnits;

        return gpa;
    }

    public int getLastSubmitedUnits() {
        int sumOfUnits = 0;
        for(WeeklyScheduleItem submitedWeekly : getLastSubmit()){
            sumOfUnits += submitedWeekly.getOffering().getUnits();
        }
        return sumOfUnits;
    }
//
//    public void addGrade(Grade grade){this.grades.add(grade);}

    public void insertGrade(Grade grade) throws SQLException {
        GradeMapper gradeMapper = new GradeMapper();
        gradeMapper.insert(grade);
    }

    public double getTotalPassedUnits() throws Exception {
        int totalPassedUnits = 0;

        for (Grade grade : Repository.getInstance().getAllGradesByStudentId(this.getStudentId()))
            if(grade.getGrade()>=10)
                totalPassedUnits += CourseEnrolment.getInstance().getOffering(grade.getCode()).getUnits();

        return totalPassedUnits;
    }

    public int getTotalUnits()throws Exception{
        int totalUnits = 0;
        for (WeeklyScheduleItem weeklyScheduleItem : CourseEnrolment.getInstance().getWeeklySchedule(id, false))
            totalUnits+=weeklyScheduleItem.getOffering().getUnits();

        return totalUnits;
    }

    public List<Grade> getGrades() {
        return Repository.getInstance().getAllGradesByStudentId(this.getStudentId());
    }

    public List<Grade> getGradesByTerm(int term) {
        return Repository.getInstance().getAllGradesByStudentId(this.getStudentId()).stream()
            .filter(grade -> grade.getTerm() == term)
            .collect(Collectors.toList());
    }

    public List<Term> getTerms() {
        if(!terms.isEmpty())
            return terms;

        Set<Integer> values = Repository.getInstance().getAllGradesByStudentId(this.getStudentId()).stream()
                .map(Grade::getTerm)
                .collect(Collectors.toSet());

        Collections.sort(values.stream().collect(Collectors.toList()));

        for (Iterator<Integer> i = values.iterator(); i.hasNext();) {
            Integer item = i.next();

            Term term = new Term(item);

            term.setGrades(Repository.getInstance().getAllGradesByStudentId(this.getStudentId()).stream()
                    .filter(grade -> grade.getTerm() == item)
                    .collect(Collectors.toList())
            );

            terms.add(term);
        }

        return terms;
    }
}