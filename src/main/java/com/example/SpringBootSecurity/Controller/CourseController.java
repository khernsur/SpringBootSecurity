package com.example.SpringBootSecurity.Controller;

import com.example.SpringBootSecurity.Entity.Course;
import com.example.SpringBootSecurity.Repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("/addCourse")
    public Course addCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @GetMapping("/getAllCourse")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/getCourseById/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteCourseById/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Long id, @RequestBody Course updatedCourse) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setName(updatedCourse.getName());
            return ResponseEntity.ok(courseRepository.save(course));
        }
        return ResponseEntity.notFound().build();
    }
}
