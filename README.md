# student-course-api

# Overview
  This project is a Spring Boot based REST API for managing student-course enrollments. It allows users to add students and courses, 
  enroll 
  students in courses, and retrieve information about the same.

# Project Setup
  1. Clone the repository
     Use Git to clone the project repository from GitHub :
       cmd: git clone https://github.com/oliviaroy02/student-course-enrollment-api.git

# Run the Application
  Either use Maven as the build tool or run directly
  
  While using Maven:
    1. In the terminal, go to the api directory :
       cd api
    2. Build the Project using Maven and install the necessary dependencies:
       mvn clean install
    3. Run the application:
       mvn spring-boot run
  
  Alternatively, run the ApiApplication.java file in the directory: api/src/main/java/ [click on Run Java]
  The application runs on http://localhost:8080.

# API Endpoints
  1. endpoint    : POST /api/student
     description : Add a new Student record
     parameters  : name, email
     response    : 201 (Created): Successfully added student data.
                   400 (Bad Request): If name or email is missing.
     
  2. endpoint    : POST /api/courses
     description : Add a new course
     parameters  : title, description
     response    : 201 (Created): Successfully added course data.
                   400 (Bad Request): If title or description is missing.
     
  3. endpoint    : POST /api/enrollments
     description : Enroll a student in a course.
     parameters  : studentId, courseId
     response    : 201 (Created): Successfully enrolled student to course.
                   400 (Bad Request): If course or student is missing.
     
  4. endpoint    : GET /api/students
     description : Retrieve all students.
     response    : 200 (OK): Returns a list of all students.

  5. endpoint    : GET /api/courses
     description : Retrieve all courses.
     response    : 200 (OK): Returns a list of all courses

 6. endpoint     : GET /api/students/{studentId}/courses
    description  : Fetches all courses in which a student is enrolled.
    response     : 200 (OK): Returns the list of courses the student is enrolled in.
                   404 (Not Found): If the student does not exist.

 7. endpoint     : GET /api/courses/{courseId}/students
    description  : Fetches all students enrolled in a course.
    response     : 200 (OK): Returns the list of students enrolled in the course.
                   404 (Not Found): If the course does not exist.

