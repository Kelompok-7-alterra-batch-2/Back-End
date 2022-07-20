package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.BloodTypeDTO;
import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.service.BloodTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/bloods")
public class BloodTypeController {
    @Autowired
    BloodTypeServiceImpl bloodTypeService;

    @GetMapping
    public ResponseEntity<List<BloodTypeEntity>> getAllBloodTypes(){
        return ResponseEntity.ok().body(this.bloodTypeService.getAllBloodTypes());
    }

    @PostMapping
    public ResponseEntity<BloodTypeEntity> createBloodType(@RequestBody BloodTypeDTO bloodTypeDTO){
        return ResponseEntity.ok(this.bloodTypeService.createBloodType(bloodTypeDTO));
    }

}
