package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping
    public List<GetDoctorDTO> getAllDoctor(){
        return this.userService.getAllDoctor();
    }

    @GetMapping("/{id}")
    public GetDoctorDTO getById(@PathVariable("id") Long id){
        return this.userService.getById(id);
    }

    @PostMapping
    public UserEntity createDoctor(@RequestBody DoctorDTO doctorDTO){
        return this.userService.createDoctor(doctorDTO);
    }

    @PutMapping("/{id}")
    public UserEntity updateDoctorById(@RequestBody DoctorDTO doctorDTO, @PathVariable("id") Long id){
        return this.userService.updateDoctor(id, doctorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctorById(@PathVariable("id") Long id){
        this.userService.deleteDoctor(id);
    }
}
