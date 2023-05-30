package com.example.SpringBootSecurity.Repository;

import com.example.SpringBootSecurity.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
