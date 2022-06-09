package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.RoleDto;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleServiceImpl roleService;

    @GetMapping
    public List<RoleEntity> getAllRole(){
        return this.roleService.getAllRole();
    }

    @PostMapping
    public RoleEntity createRole(@RequestBody RoleDto roleDto){
        return this.roleService.createRole(roleDto);
    }

}
