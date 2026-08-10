package com.project.crud.controller;

import com.project.crud.entity.Student;
import com.project.crud.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/student")
@RestController
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/{deptId}")
    public ResponseEntity<String> createStudent(
            @RequestBody Student student,
            @PathVariable Long deptId) {

        studentService.createStudent(student, deptId);
        return ResponseEntity.ok("DONE");
    }

    @PostMapping("/withDept")
    public ResponseEntity<String> createStudent(
            @RequestBody Student student,
            @RequestParam String deptName) {

        studentService.createStudentWithDept(student, deptName);
        return ResponseEntity.ok("DONE");
    }


}
