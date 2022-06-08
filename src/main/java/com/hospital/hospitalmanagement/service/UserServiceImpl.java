package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    public List<UserEntity> getAllUser(){
        return this.userRepository.findAll();
    }
}
