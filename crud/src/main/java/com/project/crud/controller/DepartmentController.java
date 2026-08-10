package com.project.crud.controller;

import com.project.crud.entity.Department;
import com.project.crud.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apis/department")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<String> createDepartment(@RequestBody Department dept) {
        departmentService.createDepartment(dept);
        return ResponseEntity.ok("ok");
    }

}
