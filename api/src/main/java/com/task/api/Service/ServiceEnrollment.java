package com.task.api.Service;

import java.util.List;
import java.util.NoSuchElementException;

import com.task.api.Entities.*;
import com.task.api.RepoInterface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceEnrollment 
{
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private EnrollmentRepo enrollmentRepo;


    public void addCourse(String title, String description)
    {
        if((title == null || title.equals("")) && (description == null || description.equals("")))
            throw new IllegalArgumentException("Both the fields cannot be empty");

        if(title == null || title.equals(""))
            throw new IllegalArgumentException("Title cannot be empty");

        if(description == null || description.equals(""))
            throw new IllegalArgumentException("Description cannot be empty");
        
        Course course = new Course(title, description);
        courseRepo.save(course);
    }

    public void addStudent(String name, String email)
    {
        if((name == null || name.equals("")) && (email == null || email.equals("")))
            throw new IllegalArgumentException("Both the fields cannot be empty");

        if(name == null || name.equals(""))
            throw new IllegalArgumentException("Name cannot be empty");

        if(email == null || email.equals(""))
            throw new IllegalArgumentException("Email cannot be empty");
        
        Student student = new Student(name, email);
        studentRepo.save(student);
    }

    public Enrollment enrollStudentToCourse(long studentId, long courseId)
    {   
        if(studentId <= 0 && courseId <= 0)
            throw new IllegalArgumentException("Invalid Student Id and Course Id");

        if(studentId <= 0)
            throw new IllegalArgumentException("Invalid Student Id");
        
        if(courseId <= 0)
            throw new IllegalArgumentException("Invalid Course Id");

        Student student = studentRepo.findById(studentId).get();
        Course course = courseRepo.findById(courseId).get();

        Enrollment enroll = new Enrollment();
        enroll.setStudentInfo(student);
        enroll.setCourseInfo(course);

        return enrollmentRepo.save(enroll);
    }

    public List<Course> getCourses()
    {
        List<Course> courses = courseRepo.findAll();
        return courses;
    }

    public List<Student> getStudents()
    {
        List<Student> students = studentRepo.findAll();
        return students;
    }

    
    public List<Course> getCoursesEnrolledByStudent(long studentId)
    {
        if(studentId <= 0)
            throw new IllegalArgumentException("Invalid Student Id");

        Student student = studentRepo.findById(studentId)
            .orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + studentId));

        return student.getCourses();
    }

    public List<Student> getStudentsEnrolledToCourse(long courseId)
    {   
        if(courseId <= 0)
            throw new IllegalArgumentException("Invalid Course Id");

        Course course = courseRepo.findById(courseId)
            .orElseThrow(() -> new NoSuchElementException("Course not found with ID: " + courseId));
        
        return course.getStudents();
    }

}
