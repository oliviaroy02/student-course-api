package com.task.api.Entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseId;
    private String courseTitle;
    private String description;
    
    public Course() {}
    public Course(String courseTitle, String description)
    {
        this.courseTitle = courseTitle;
        this.description = description;
    }

    public void setCourseId(long courseId)
    {
        this.courseId = courseId;
    }

    public void setCourseTitle(String courseTitle)
    {
        this.courseTitle = courseTitle;
    }

    public void setCourseDescription(String description)
    {
        this.description = description;
    }

    public long getCourseId()
    {
        return courseId;
    }

    public String getCourseTitle()
    {
        return courseTitle;
    }

    public String getCourseDescription()
    {
        return description;
    }

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
    
}
