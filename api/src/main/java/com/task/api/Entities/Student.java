package com.task.api.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Student 
{
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long studentId;

    private String studentName, studentEmail;

    public Student() {}
    public Student(String studentName, String studentEmail)
    {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }

    public void setStudentId(long studentId)
    {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public void setStudentEmail(String studentEmail)
    {
        this.studentEmail = studentEmail;
    }

    public long getStudentId()
    {
        return studentId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public String getStudentEmail()
    {
        return studentEmail;
    }

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
        name = "Enrollment",
        joinColumns = @JoinColumn(name = "studentId"),
        inverseJoinColumns = @JoinColumn(name = "courseId")
    )

    private List<Course> courses;

    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }

    public List<Course> getCourses()
    {
        return courses;
    }

}
