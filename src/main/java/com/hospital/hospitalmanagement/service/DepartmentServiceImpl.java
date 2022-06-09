package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.DepartmentDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<DepartmentEntity> getAllDepartment(){
        return this.departmentRepository.findAll();
    }

    public DepartmentEntity getDepartmentById(Long id){
        Optional<DepartmentEntity> optionalDepartment = this.departmentRepository.findById(id);

        if(optionalDepartment.isEmpty()){
            return null;
        }

        return optionalDepartment.get();
    }

    public DepartmentEntity createDepartment(DepartmentDTO departmentDTO){
        DepartmentEntity newDepartment = DepartmentEntity.builder()
                .name(departmentDTO.getName())
                .createdAt(LocalDateTime.now())
                .build();

        return this.departmentRepository.save(newDepartment);
    }


}
