package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.OutpatientDTO;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.OutpatientServiceImpl;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/outpatients")
public class OutpatientController {

    @Autowired
    OutpatientServiceImpl outpatientService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/today")
    public List<OutpatientEntity> getAllTodayOutpatient(){
        return this.outpatientService.getAllTodayOutpatient();
    }

    @GetMapping("/count/today")
    public Long countTodayOutpatient(){
        return this.outpatientService.countTodayOutpatient();
    }

    @GetMapping("/doctors")
    public List<UserEntity> getAllAvailableDoctor(@RequestBody DoctorDTO doctorDTO){
//        LocalTime parseArrivalTime = LocalTime.parse(arrivalTime);
//        DepartmentEntity department = this.departmentRepository.findById(doctorDTO.getDepartment_id()).get();
        return this.userService.getAllAvailableDoctor(doctorDTO);
    }

    @GetMapping
    public List<OutpatientEntity> getAllOutpatient(){
        return this.outpatientService.getAllOutpatient();
    }

    @GetMapping("/{id}")
    public OutpatientEntity getOutpatientById(@PathVariable("id") Long id){
        return this.outpatientService.getOutpatientById(id);
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
