package com.task.api.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    public Enrollment() {}
    public Enrollment(Student student, Course course)
    {
        this.student = student;
        this.course = course;
    }

    public void setEnrollmentId(long enrollmentId)
    {
        this.enrollmentId = enrollmentId;
    }

    public void setStudentInfo(Student student)
    {
        this.student = student;
    }

    public void setCourseInfo(Course course)
    {
        this.course = course;
    }

    public long getEnrollmentId()
    {
        return enrollmentId;
    }

    public Student getStudentInfo()
    {
        return student;
    }

    public Course getCourseInfo()
    {
        return course;
    }
}
