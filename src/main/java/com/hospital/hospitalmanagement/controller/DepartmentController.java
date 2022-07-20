package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.DepartmentDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    DepartmentServiceImpl departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentEntity>> getAllDepartments(){
        return ResponseEntity.ok().body(this.departmentService.getAllDepartment());
    }

    @PostMapping
    public ResponseEntity<DepartmentEntity> createDepartment(@RequestBody DepartmentDTO departmentDTO){
        return ResponseEntity.ok().body(this.departmentService.createDepartment(departmentDTO));
    }
}
