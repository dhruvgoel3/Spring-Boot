package com.project.crud.service;

import com.project.crud.entity.Department;
import com.project.crud.entity.Student;
import com.project.crud.repository.DepartmentRepository;
import com.project.crud.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    // Here , first we need a department , so that we can put the student to that department
    public Student createStudent(Student student, Long deptId) {
        Department department = departmentRepository.getDepartmentById(deptId);
        student.setDepartment(department);
        return studentRepository.save(student);

    }

    // But here , first we are creating a department and then saving student to that department
    public Student createStudentWithDept(Student student, String deptName) {
        Department department = new Department();
        department.setName(deptName);
        departmentRepository.save(department);
        student.setDepartment(department);
        return studentRepository.save(student);
    }

    // getStudent
//
//    public Student getStudentById(Long studentId) {
//        Student student = studentRepository.getStudentById(studentId);
//        return student;
//    }
//
//    public List<Student> getStudentByDeptId(Long deptId) {
//        return studentRepository.findByDepartmentId(deptId);
//
//    }


}
