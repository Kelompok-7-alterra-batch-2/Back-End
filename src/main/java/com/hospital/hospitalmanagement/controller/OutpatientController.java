package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.AvailDoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DiagnosisDTO;
import com.hospital.hospitalmanagement.controller.dto.OutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
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
    public List<GetOutpatientDTO> getAllTodayOutpatient(){
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
    public List<GetOutpatientDTO> getAllOutpatient(){
        return this.outpatientService.getAllOutpatient();
    }

    @GetMapping("/{id}")
    public GetOutpatientDTO getOutpatientById(@PathVariable("id") Long id){
        return this.outpatientService.getOutpatientById(id);
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
    public List<GetOutpatientDTO> getAllPendingOutpatient(){
        return this.outpatientService.getAllPendingOutpatient();
    }

    @GetMapping("/pending/doctors/{doctorId}/today")
    public List<GetOutpatientDTO> getAllPendingOutpatientToday(@PathVariable("doctorId") Long doctorId){
        return this.outpatientService.findAllTodayPendingOutpatientByDoctor(doctorId);
    }

    @GetMapping("/process")
    public List<GetOutpatientDTO> getAllProcessOutpatient(){
        return this.outpatientService.getAllProcessOutpatient();
    }

    @GetMapping("/process/doctors/{doctorId}/today")
    public List<GetOutpatientDTO> getAllProcessOutpatientToday(@PathVariable("doctorId") Long doctorId){
        return this.outpatientService.findAllTodayProcessOutpatientByDoctor(doctorId);
    }

    @PutMapping("/process/{id}")
    public GetOutpatientDTO updateOutpatientConditionToProcess(@PathVariable("id") Long condition_id){
        return this.outpatientService.processOutpatient(condition_id);
    }

    @GetMapping("/done")
    public List<GetOutpatientDTO> getAllDoneOutpatient(){
        return this.outpatientService.getAllDoneOutpatient();
    }

    @GetMapping("/done/doctors/{doctorId}/today")
    public List<GetOutpatientDTO> getAllDoneOutpatientToday(@PathVariable("doctorId") Long doctorId){
        return this.outpatientService.findAllTodayDoneOutpatientByDoctor(doctorId);
    }

    @PutMapping("/done/{id}")
    public GetOutpatientDTO updateOutpatientConditionToDone(@PathVariable("id") Long outpatient_id){
        return this.outpatientService.doneOutpatient(outpatient_id);
    }

    @GetMapping("/departments/{id}")
    public List<GetOutpatientDTO> getAllOutpatientByDepartment(@PathVariable("id") Long department_id){
        return this.outpatientService.getAllTodayOutpatientByDepartment(department_id);
    }

    @PutMapping("/diagnosis/{id}")
    public GetOutpatientDTO updateDiagnosisOutpatient(@PathVariable("id") Long outpatient_id, @RequestBody DiagnosisDTO diagnosisDTO){
        return this.outpatientService.diagnosisOutpatient(outpatient_id, diagnosisDTO);
    }

    @GetMapping("/doctors/{id}")
    public List<GetOutpatientDTO> getAllOutpatientByDoctorASC(@PathVariable("id") Long doctor_id){
        return this.outpatientService.getAllOutpatientByDoctor(doctor_id);
    }

    @GetMapping("/doctors/{id}/today")
    public List<GetOutpatientDTO> getAllTodayOutpatientByDoctorASC(@PathVariable("id") Long doctor_id){
        return this.outpatientService.getAllTodayOutpatientByDoctor(doctor_id);
    }

    @PutMapping("/truncate")
    public void truncateOutpatientTable(){
        this.outpatientService.truncateOutpatientTable();
    }
}
