package com.task.api.Handler;

import java.util.List;

import com.task.api.Entities.*;
import com.task.api.RepoInterface.*;
import com.task.api.Service.ServiceEnrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping()
public class RequestHandler 
{
    @Autowired
    private ServiceEnrollment serviceEnrollment;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private CourseRepo courseRepo;

    @PostMapping("api/student")
    public ResponseEntity<String> addStudent(@RequestParam String name, @RequestParam String email) 
    {
        if(name == null || email == null)
            return ResponseEntity.badRequest().body("Student Name and Email are required.");
        
        serviceEnrollment.addStudent(name, email);
        return ResponseEntity.status(201).body("Successfully added Student data");
    }

    @PostMapping("api/courses")
    public ResponseEntity<String> addCourse(@RequestParam String title, @RequestParam String description) 
    {
        if(title == null || description == null)
            return ResponseEntity.badRequest().body("Course title and description are required.");
        
        serviceEnrollment.addCourse(title, description);
        return ResponseEntity.status(201).body("Successfully added Course data");
    }
    

    @PostMapping("api/enrollments")
    public ResponseEntity<String> enrollStudentToCourse(@RequestParam Long studentId, @RequestParam Long courseId) 
    {
        Student student = studentRepo.findById(studentId).orElse(null);
        if(student == null)
            return ResponseEntity.status(404).body("Could not find student");
        
        Course course = courseRepo.findById(courseId).orElse(null);
        if(course == null)
            return ResponseEntity.status(404).body("Could not find course");

        serviceEnrollment.enrollStudentToCourse(studentId, courseId);
        return ResponseEntity.status(201).body("Successfully Enrolled!");
    }

    @GetMapping("api/students/{studentId}/courses")
    public ResponseEntity<?> getCoursesByStudentId(@PathVariable Long studentId) 
    {
        Student student = studentRepo.findById(studentId).orElse(null);
        if(student == null)
            return ResponseEntity.status(404).body("Could not find student");

        List<Course> courses = serviceEnrollment.getCoursesEnrolledByStudent(studentId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("api/courses/{courseId}/students")
    public ResponseEntity<?> getStudentsByCourseId(@PathVariable Long courseId) 
    {
        Course course = courseRepo.findById(courseId).orElse(null);
        if(course == null)
            return ResponseEntity.status(404).body("Could not find course");
        
        List<Student> students = serviceEnrollment.getStudentsEnrolledToCourse(courseId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/api/students")
    public ResponseEntity<List<Student>> getAllStudents() 
    {
        List<Student> students = serviceEnrollment.getStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/api/courses")
    public ResponseEntity<List<Course>> getAllCourses()
    {
        List<Course> courses = serviceEnrollment.getCourses();
        return ResponseEntity.ok(courses);
    }
    
}
