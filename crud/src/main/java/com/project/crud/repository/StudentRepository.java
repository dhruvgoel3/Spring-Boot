package com.project.crud.repository;

import com.project.crud.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
//    Student getStudentById(Long studentId);
//    List<Student> findByDepartmentId(Long deptId);
}
