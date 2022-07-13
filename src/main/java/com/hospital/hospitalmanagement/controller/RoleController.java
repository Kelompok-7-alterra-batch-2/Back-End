package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.RoleDto;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleServiceImpl roleService;

    @GetMapping
    public ResponseEntity<List<RoleEntity>> getAllRole(){
        return ResponseEntity.ok().body(this.roleService.getAllRole());
    }

    @PostMapping
    public ResponseEntity<RoleEntity> createRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.ok().body(this.roleService.createRole(roleDto));
    }

}
