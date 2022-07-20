package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.GenderDTO;
import com.hospital.hospitalmanagement.entities.GenderEntity;
import com.hospital.hospitalmanagement.service.GenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/genders")
public class GenderController {
    @Autowired
    GenderServiceImpl genderService;

    @GetMapping
    public ResponseEntity<List<GenderEntity>> getAllGenders(){
        return ResponseEntity.ok().body(this.genderService.getAllGenders());
    }

    @PostMapping
    public ResponseEntity<GenderEntity> createGender(@RequestBody GenderDTO genderDTO){
        return ResponseEntity.ok().body(this.genderService.createGender(genderDTO));
    }
}
