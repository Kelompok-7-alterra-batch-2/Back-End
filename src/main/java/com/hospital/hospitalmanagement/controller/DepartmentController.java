package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.DepartmentDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    DepartmentServiceImpl departmentService;

    @GetMapping
    public List<DepartmentEntity> getAllDepartments(){
        return this.departmentService.getAllDepartment();
    }

    @PostMapping
    public DepartmentEntity createDepartment(@RequestBody DepartmentDTO departmentDTO){
        return this.departmentService.createDepartment(departmentDTO);
    }
}
