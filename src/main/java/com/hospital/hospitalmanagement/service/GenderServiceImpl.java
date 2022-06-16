package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.GenderDTO;
import com.hospital.hospitalmanagement.entities.GenderEntity;
import com.hospital.hospitalmanagement.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl {
    @Autowired
    GenderRepository genderRepository;

    public List<GenderEntity> getAllGenders() {
        return this.genderRepository.findAll();
    }

    public GenderEntity getGenderById(Long id){
        return this.genderRepository.findById(id).get();
    }

    public GenderEntity createGender(GenderDTO genderDTO) {
        GenderEntity newGenderEntity = GenderEntity.builder()
                .type(genderDTO.getType())
                .build();

        return this.genderRepository.save(newGenderEntity);
    }
}