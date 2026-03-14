package com.demo.repo;


import com.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,String> {

    //important
    Optional<Student> findByRollNumber(String rollNumber);

    Optional<Student> findByRollNumberAndDateOfBirth(String rollNumber, LocalDate dateOfBirth);
}
