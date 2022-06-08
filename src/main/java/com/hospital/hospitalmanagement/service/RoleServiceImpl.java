package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.entities.QueueEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl {
    @Autowired
    RoleRepository roleRepository;

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }

    public List<RoleEntity> getAllRole(){
        return this.roleRepository.findAll();
    }

//    public RoleEntity createRole(RoleEntity roleEntity){
//
//    }
}
