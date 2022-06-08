package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping
    public List<UserEntity> getAllUsers(){
        return this.userService.getAllUser();
    }

//    @PostMapping("/admins")
//    public UserEntity createUserAdmin(){}
}
