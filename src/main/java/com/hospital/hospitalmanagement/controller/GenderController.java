package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.GenderDTO;
import com.hospital.hospitalmanagement.entities.GenderEntity;
import com.hospital.hospitalmanagement.service.GenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genders")
public class GenderController {
    @Autowired
    GenderServiceImpl genderService;

    @GetMapping
    public List<GenderEntity> getAllGenders(){
        return this.genderService.getAllGenders();
    }

    @PostMapping
    public GenderEntity createGender(@RequestBody GenderDTO genderDTO){
        return this.genderService.createGender(genderDTO);
    }
}
