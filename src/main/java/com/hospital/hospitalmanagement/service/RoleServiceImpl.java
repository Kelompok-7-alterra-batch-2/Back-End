package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.RoleDto;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.QueueEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public RoleEntity getRoleById(Long id){
            Optional<RoleEntity> optionalRole = this.roleRepository.findById(id);

        if(optionalRole.isEmpty()){
            throw new NotFoundException("Data Not Found");
        }

        return optionalRole.get();
    }

    public RoleEntity createRole(RoleDto roleDto) {
        RoleEntity newRole = RoleEntity.builder()
                .name(roleDto.getName())
                .createdAt(getTimeNow())
                .build();

        return this.roleRepository.save(newRole);
    }

}
