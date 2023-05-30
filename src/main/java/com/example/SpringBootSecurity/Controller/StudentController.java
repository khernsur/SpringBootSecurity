package com.example.SpringBootSecurity.Controller;

import com.example.SpringBootSecurity.Entity.Course;
import com.example.SpringBootSecurity.Entity.Student;
import com.example.SpringBootSecurity.Repository.CourseRepository;
import com.example.SpringBootSecurity.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    @Autowired
    public StudentController(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<Student> updateById(@PathVariable("id") Long id, @RequestBody Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            return ResponseEntity.ok(studentRepository.save(student));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/enroll")
    public ResponseEntity<Student> enroll(@PathVariable("id") Long id, @RequestBody Course course) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        Optional<Course> optionalCourse = courseRepository.findByName(course.getName()); // Retrieve course by name

        if (optionalStudent.isPresent() && optionalCourse.isPresent()) {
            Student student = optionalStudent.get();
            Course enrolledCourse = optionalCourse.get();

            student.setCourse(enrolledCourse);
            return ResponseEntity.ok(studentRepository.save(student));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/drop")
    public ResponseEntity<Student> drop(@PathVariable("id") Long id, @RequestBody Course course) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            if (student.getCourse() != null && student.getCourse().getId().equals(course.getId())) {
                student.dropCourse();
                return ResponseEntity.ok(studentRepository.save(student));
            }
        }
        return ResponseEntity.notFound().build();
    }
}