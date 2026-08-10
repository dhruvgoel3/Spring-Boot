package com.project.crud.service;

import com.project.crud.entity.Department;
import com.project.crud.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        Department dept = departmentRepository.save(department);
        return dept;
    }


}
