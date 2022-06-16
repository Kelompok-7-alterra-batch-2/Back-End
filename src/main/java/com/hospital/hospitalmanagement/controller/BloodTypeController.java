package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.BloodTypeDTO;
import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.service.BloodTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bloods")
public class BloodTypeController {
    @Autowired
    BloodTypeServiceImpl bloodTypeService;

    @GetMapping
    public List<BloodTypeEntity> getAllBloodTypes(){
        return this.bloodTypeService.getAllBloodTypes();
    }

    @PostMapping
    public BloodTypeEntity createBloodType(@RequestBody BloodTypeDTO bloodTypeDTO){
        return this.bloodTypeService.createBloodType(bloodTypeDTO);
    }

}
