package com.task.api;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.task.api.Entities.*;
import com.task.api.RepoInterface.*;
import com.task.api.Service.ServiceEnrollment;

@SpringBootTest
public class ServiceEnrollmentTest
{
    @Mock
    private CourseRepo courseRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private EnrollmentRepo enrollmentRepo;

    @InjectMocks
    private ServiceEnrollment serviceEnrollment;

    private Student student;
    private Course course;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        student = new Student("Jerry", "jerry@email.com");
        course = new Course("AI/ML", "Learn AI/ML with this new course");
    }

    @Test
    public void testAddStudent()
    {
        when(studentRepo.save(any(Student.class))).thenReturn(student);
        serviceEnrollment.addStudent(student.getStudentName(), student.getStudentEmail());

        verify(studentRepo, times(1)).save(any(Student.class));
        assertEquals("Jerry", student.getStudentName());
        assertEquals("jerry@email.com", student.getStudentEmail());
    }

    @Test
    public void testAddStudent_WithEmptyName()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.addStudent("", student.getStudentEmail());
        });
        
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    public void testAddStudent_WithEmptyEmail()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.addStudent(student.getStudentName(), "");
        });

        assertEquals("Email cannot be empty", exception.getMessage());
    }

    @Test
    public void testAddStudent_WithEmptyNameAndEmail()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.addStudent("", "");
        });

        assertEquals("Both the fields cannot be empty", exception.getMessage());
    }

    @Test
    public void testAddCourse()
    {
        when(courseRepo.save(any(Course.class))).thenReturn(course);

        serviceEnrollment.addCourse(course.getCourseTitle(), course.getCourseDescription());

        verify(courseRepo, times(1)).save(any(Course.class));
        assertEquals("AI/ML", course.getCourseTitle());
        assertEquals("Learn AI/ML with this new course", course.getCourseDescription());
    }

    @Test
    public void testAddCourse_WithEmptyTitle()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.addCourse("", course.getCourseDescription());
        });

        assertEquals("Title cannot be empty", exception.getMessage());
    }

    @Test
    public void testAddCourseWith_EmptyDescription()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.addCourse(course.getCourseTitle(), "");
        });
        assertEquals("Description cannot be empty", exception.getMessage());
    }

    @Test
    public void testAddCourse_WithEmptyTitleAndDescription()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.addCourse("", "");
        });
        assertEquals("Both the fields cannot be empty", exception.getMessage());
    }

    @Test
    public void testEnrollStudentToCourse()
    {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepo.save(any(Enrollment.class))).thenReturn(new Enrollment(student, course));

        Enrollment enroll = serviceEnrollment.enrollStudentToCourse(1L, 1L);

        assertNotNull(enroll);
        assertEquals(student, enroll.getStudentInfo());
        assertEquals(course, enroll.getCourseInfo());
        verify(enrollmentRepo, times(1)).save(any(Enrollment.class));
    }

    @Test
    public void testEnrollStudentToCourse_WithInvalidStudentId()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.enrollStudentToCourse(-1L, 1L);
        });
        assertEquals("Invalid Student Id", exception.getMessage());
    }

    @Test
    public void testEnrollStudentToCourse_WithInvalidCourseId()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.enrollStudentToCourse(1L, -1L);
        });
        assertEquals("Invalid Course Id", exception.getMessage());
    }

    @Test
    public void testEnrollStudentToCourse_WithInvalidStudentAndcourseId()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.enrollStudentToCourse(-1L, -1L);
        });
        assertEquals("Invalid Student Id and Course Id", exception.getMessage());
    }

    @Test
    public void testGetCourses()
    {
        Course course1 = new Course("ABC", "Learn ABC");
        Course course2 = new Course("XYZ", "Learn XYZ");
        List<Course> expectedCourses = Arrays.asList(course1, course2);

        when(courseRepo.findAll()).thenReturn(expectedCourses);

        List<Course> actualCourses = serviceEnrollment.getCourses();

        verify(courseRepo, times(1)).findAll();
        assertNotNull(actualCourses);
        assertEquals(expectedCourses.size(), actualCourses.size());
    }

    @Test
    public void testGetCourses_WhenListIsEmpty()
    {
        List<Course> expectedCourses = Arrays.asList();

        when(courseRepo.findAll()).thenReturn(expectedCourses);

        List<Course> actualCourses = serviceEnrollment.getCourses();

        verify(courseRepo, times(1)).findAll();
        assertTrue(actualCourses.isEmpty());
    }
    
    @Test
    public void testGetStudents()
    {
        Student student1 = new Student("Jerry", "jerry@email.com");
        Student student2 = new Student("Berry", "berry@email.com");
        List<Student> expectedStudents = Arrays.asList(student1, student2);

        when(studentRepo.findAll()).thenReturn(expectedStudents);

        List<Student> actualStudents = serviceEnrollment.getStudents();

        verify(studentRepo, times(1)).findAll();
        assertNotNull(actualStudents);
        assertEquals(expectedStudents.size(), actualStudents.size());
    }

    @Test
    public void testGetStudents_WhenListIsEmpty()
    {
        List<Student> expectedStudents = Arrays.asList();

        when(studentRepo.findAll()).thenReturn(expectedStudents);

        List<Student> actualStudents = serviceEnrollment.getStudents();

        verify(studentRepo, times(1)).findAll();
        assertTrue(actualStudents.isEmpty());
    }

    @Test
    public void testGetCoursesEnrolledByStudent()
    {
        student.setStudentId(1L);
        Course course1 = new Course("XYZ", "Learn XYZ Course now!");
        List<Course> expectedCourses = Arrays.asList(course, course1);
        student.setCourses(expectedCourses);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        List<Course> actualCourses = serviceEnrollment.getCoursesEnrolledByStudent(student.getStudentId());

        assertNotNull(actualCourses);
        assertEquals(expectedCourses.size(), actualCourses.size());
        
    }

    @Test
    public void testGetCoursesEnrolledByStudent_WithInvalidStudentId()
    {
        student.setStudentId(-1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.getCoursesEnrolledByStudent(student.getStudentId());
        });

        assertEquals("Invalid Student Id", exception.getMessage());
    }

    @Test
    public void testGetCoursesEnrolledByStudent_WithNonExistentStudentId()
    {
        student.setStudentId(1l);

        when(studentRepo.findById(1L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, ()->
        {
            serviceEnrollment.getCoursesEnrolledByStudent(student.getStudentId());
        });

        assertEquals("Student not found with ID: " + student.getStudentId(), exception.getMessage());
    }

    @Test
    public void testGetStudentsEnrolledToCourse()
    {
        course.setCourseId(1L);
        Student student1 = new Student("Berry", "berry@email.com");
        List<Student> expectedStudents = Arrays.asList(student, student1);
        course.setStudents(expectedStudents);

        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));

        List<Student> actualStudents = serviceEnrollment.getStudentsEnrolledToCourse(1L);

        assertNotNull(actualStudents);
        assertEquals(expectedStudents.size(), actualStudents.size());
    }

    @Test
    public void testGetstudentsEnrolledToCourse_WithInvalidCourseId()
    {
        course.setCourseId(-1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
        {
            serviceEnrollment.getStudentsEnrolledToCourse(course.getCourseId());
        });

        assertEquals("Invalid Course Id", exception.getMessage());
    }

    @Test
    public void testGetStudentsEnrolledToCourse_WithNonExistentCourseId()
    {
        course.setCourseId(1l);

        when(courseRepo.findById(1L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, ()->
        {
            serviceEnrollment.getStudentsEnrolledToCourse(course.getCourseId());
        });

        assertEquals("Course not found with ID: " + course.getCourseId(), exception.getMessage());
    }

}
