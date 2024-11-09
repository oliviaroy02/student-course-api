package com.task.api.RepoInterface;

import com.task.api.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Long> {} 
