package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.BloodTypeDTO;
import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.repository.BloodTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodTypeServiceImpl {
    @Autowired
    BloodTypeRepository bloodTypeRepository;

    public List<BloodTypeEntity> getAllBloodTypes() {
        return this.bloodTypeRepository.findAll();
    }

    public BloodTypeEntity getBloodTypeById(Long id){
        return this.bloodTypeRepository.findById(id).get();
    }

    public BloodTypeEntity createBloodType(BloodTypeDTO bloodTypeDTO) {
        BloodTypeEntity newBloodTypeEntity = BloodTypeEntity.builder()
                .type(bloodTypeDTO.getType())
                .build();

        return this.bloodTypeRepository.save(newBloodTypeEntity);
    }
}
