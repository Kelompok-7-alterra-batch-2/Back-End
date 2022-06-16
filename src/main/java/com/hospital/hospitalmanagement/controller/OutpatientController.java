package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.AvailDoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.OutpatientDTO;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.OutpatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/outpatients")
public class OutpatientController {

    @Autowired
    OutpatientServiceImpl outpatientService;

    @GetMapping("/today")
    public List<OutpatientEntity> getAllTodayOutpatient(){
        return this.outpatientService.findAllTodayOutpatient();
    }

    @GetMapping("/count/today")
    public Long countTodayOutpatient(){
        return this.outpatientService.countTodayOutpatient();
    }

    @GetMapping("/doctors")
    public List<UserEntity> getAllAvailableDoctor(@RequestBody AvailDoctorDTO availDoctorDTO){
        return this.outpatientService.getAllAvailableDoctor(availDoctorDTO.getArrival_time(), availDoctorDTO.getDepartment_id());
    }

    @GetMapping
    public List<OutpatientEntity> getAllOutpatient(){
        return this.outpatientService.getAllOutpatient();
    }

    @GetMapping("/{id}")
    public OutpatientEntity getOutpatientById(@PathVariable("id") Long id){
        return this.outpatientService.getById(id);
    }

    @PostMapping
    public OutpatientEntity createOutpatient(@RequestBody OutpatientDTO outpatientDTO){
        return this.outpatientService.createOutpatient(outpatientDTO);
    }

    @PutMapping("/{id}")
    public OutpatientEntity updateOutpatient(@RequestBody OutpatientDTO outpatientDTO, @PathVariable("id")Long id){
        return this.outpatientService.updateOutpatient(id,outpatientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOutpatient(@PathVariable("id") Long id){
        this.outpatientService.deleteOutpatient(id);
    }
}
