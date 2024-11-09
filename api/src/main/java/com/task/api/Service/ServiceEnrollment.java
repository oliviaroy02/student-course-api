package com.task.api.Service;

import java.util.List;
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
        Course course = new Course(title, description);
        courseRepo.save(course);
    }

    public void addStudent(String name, String email)
    {
        Student student = new Student(name, email);
        studentRepo.save(student);
    }

    public Enrollment enrollStudentToCourse(long studentId, long courseId)
    {
        Student student;
        Course course;

        student = studentRepo.findById(studentId).get();
        course = courseRepo.findById(courseId).get();

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
        Student student = studentRepo.findById(studentId).get();
        return student.getCourses();
    }

    public List<Student> getStudentsEnrolledToCourse(long courseId)
    {   
        Course course = courseRepo.findById(courseId).get();
        return course.getStudents();
    }

}
