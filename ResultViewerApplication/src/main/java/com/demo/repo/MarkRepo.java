package com.demo.repo;

import com.demo.entity.Mark;
import com.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepo extends JpaRepository<Mark,Long> {

    List<Mark> findByStudent(Student student);
}
