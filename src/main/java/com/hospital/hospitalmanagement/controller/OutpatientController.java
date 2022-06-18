package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.AvailDoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DiagnosisDTO;
import com.hospital.hospitalmanagement.controller.dto.OutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.OutpatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public GetOutpatientDTO createOutpatient(@RequestBody OutpatientDTO outpatientDTO){
        return this.outpatientService.createOutpatient(outpatientDTO);
    }

    @PutMapping("/{id}")
    public GetOutpatientDTO updateOutpatient(@RequestBody OutpatientDTO outpatientDTO, @PathVariable("id")Long id){
        return this.outpatientService.updateOutpatient(id,outpatientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOutpatient(@PathVariable("id") Long id){
        this.outpatientService.deleteOutpatient(id);
    }


    @GetMapping("/pending")
    public List<OutpatientEntity> getAllPendingOutpatient(){
        return this.outpatientService.getAllPendingOutpatient();
    }

    @GetMapping("/pending/today")
    public List<OutpatientEntity> getAllPendingOutpatientToday(){
        return this.outpatientService.findAllTodayPendingOutpatient();
    }

    @GetMapping("/process")
    public List<OutpatientEntity> getAllProcessOutpatient(){
        return this.outpatientService.getAllProcessOutpatient();
    }

    @GetMapping("/process/today")
    public List<OutpatientEntity> getAllProcessOutpatientToday(){
        return this.outpatientService.findAllTodayProcessOutpatient();
    }

    @PutMapping("/process/{id}")
    public OutpatientEntity updateOutpatientConditionToProcess(@PathVariable("id") Long condition_id){
        return this.outpatientService.processOutpatient(condition_id);
    }

    @GetMapping("/done")
    public List<OutpatientEntity> getAllDoneOutpatient(){
        return this.outpatientService.getAllDoneOutpatient();
    }

    @GetMapping("/done/today")
    public List<OutpatientEntity> getAllDoneOutpatientToday(){
        return this.outpatientService.findAllTodayDoneOutpatient();
    }

    @PutMapping("/done/{id}")
    public OutpatientEntity updateOutpatientConditionToDone(@PathVariable("id") Long outpatient_id){
        return this.outpatientService.doneOutpatient(outpatient_id);
    }

    @GetMapping("/departments/{id}")
    public List<OutpatientEntity> getAllOutpatientByDepartment(@PathVariable("id") Long department_id){
        return this.outpatientService.getAllTodayOutpatientByDepartment(department_id);
    }

    @PutMapping("/diagnosis/{id}")
    public OutpatientEntity updateDiagnosisOutpatient(@PathVariable("id") Long outpatient_id, @RequestBody DiagnosisDTO diagnosisDTO){
        return this.outpatientService.diagnosisOutpatient(outpatient_id, diagnosisDTO);
    }

    @GetMapping("/doctors/{id}")
    public List<OutpatientEntity> getAllOutpatientByDoctorASC(@PathVariable("id") Long doctor_id){
        return this.outpatientService.getAllOutpatientByDoctor(doctor_id);
    }

    @GetMapping("/OutpatientByDoctorToday/{id}")
    public List<OutpatientEntity> getAllOutpatientByDoctor(@PathVariable("id") Long doctor_id){
        return this.outpatientService.getAllTodayOutpatientByDoctor(doctor_id);
    }
}
