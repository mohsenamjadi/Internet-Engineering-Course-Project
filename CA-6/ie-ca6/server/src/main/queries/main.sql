use BolbolestanDB;

drop table if exists STUDENTS;

create table STUDENTS (
    id varchar(255) primary key,
    name tinytext not null,
    secondName tinytext not null,
    birthDate tinytext not null,
    field tinytext not null,
    faculty tinytext not null,
    level tinytext not null,
    status tinytext not null,
    img tinytext,
    searchFilter tinytext default ''
);

describe STUDENTS;

drop table if exists COURSES;

create table COURSES (
    code varchar(255) not null,
    classCode varchar(255) not null,
    name tinytext not null,
    units int not null,
    type tinytext not null,
    instructor tinytext not null,
    capacity int not null,
    signedUp int not null default 0,
    start tinytext not null,
    end tinytext not null,
    primary key(code, classCode)
);

describe COURSES;

drop table if exists GRADES;

create table GRADES (
    code varchar(255) not null,
    student_id varchar(255) not null,
    grade double not null,
    term int,
    primary key(code, student_id , term),
    foreign key (code) references COURSES(code),
    foreign key (student_id) references STUDENTS(id),
    foreign key (student_id) references TERMS(term, studentId)
);

describe GRADES;

drop table if exists TERMS;

create table TERMS (
   studentId varchar(255),
   term int not null,
   moadel double not null,
   primary key(term, studentId),
   foreign key (studentId) references STUDENTS(id)
);

describe TERMS;

drop table if exists DAYS;

create table DAYS (
    code varchar(255) not null,
    classCode varchar(255) not null,
    day tinytext not null,
    primary key (code, classCode),
    foreign key (code, classCode) references CLASSTIME(code, classCode)
);

describe DAYS;

drop table if exists PREREQUISITES;

create table PREREQUISITES (
    code varchar(255) not null,
    classCode varchar(255) not null,
    prerequisite tinytext not null,
    primary key (code, classCode),
    foreign key (code, classCode) references COURSES(code, classCode)
);

describe PREREQUISITES;

drop table if exists CLASSTIME;

create table CLASSTIME (
    code varchar(255) not null,
    classCode varchar(255) not null,
    time tinytext not null,
    primary key (code, classCode),
    foreign key (code, classCode) references COURSES(code, classCode)
);

describe CLASSTIME;

drop table if exists EXAMTIME;

create table EXAMTIME (
    code varchar(255) not null,
    classCode varchar(255) not null,
    start tinytext not null,
    end tinytext not null,
    primary key (code, classCode),
    foreign key (code, classCode) references COURSES(code, classCode)
);

describe EXAMTIME;

drop table if exists WEEKLYSCHEDULEITEMS;

create table WEEKLYSCHEDULEITEMS (
    code varchar(255) not null,
    classCode varchar(255) not null,
    studentId varchar(255),
    status tinytext not null,
    primary key (code, classCode , studentId),
    foreign key (code, classCode) references COURSES(code,classCode),
    foreign key (studentId) references STUDENTS(id)
);

describe WEEKLYSCHEDULEITEMS;