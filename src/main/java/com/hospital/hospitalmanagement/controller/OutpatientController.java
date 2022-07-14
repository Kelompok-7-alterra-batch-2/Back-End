package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.AvailDoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DiagnosisDTO;
import com.hospital.hospitalmanagement.controller.dto.OutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.OutpatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/outpatients")
public class OutpatientController {

    @Autowired
    OutpatientServiceImpl outpatientService;

    @GetMapping("/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllTodayOutpatient(){
        return ResponseEntity.ok().body(this.outpatientService.findAllTodayOutpatient());
    }

    @GetMapping("/count/today")
    public ResponseEntity<Long> countTodayOutpatient(){
        return ResponseEntity.ok().body(this.outpatientService.countTodayOutpatient());
    }

//    @GetMapping("/doctors")
//    public List<UserEntity> getAllAvailableDoctor(
//            @RequestParam(name = "arrival_time") String arrival_time,
//            @RequestParam(name = "department_id") Long department_id){
//        return this.outpatientService.getAllAvailableDoctor(arrival_time, department_id);
//    }

    @GetMapping
    public ResponseEntity<List<GetOutpatientDTO>> getAllOutpatient(){
        return ResponseEntity.ok().body(this.outpatientService.getAllOutpatient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOutpatientDTO> getOutpatientById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.outpatientService.getOutpatientById(id));
    }

    @PostMapping
    public ResponseEntity<GetOutpatientDTO> createOutpatient(@RequestBody OutpatientDTO outpatientDTO){
        return ResponseEntity.ok().body(this.outpatientService.createOutpatient(outpatientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetOutpatientDTO> updateOutpatient(@RequestBody OutpatientDTO outpatientDTO, @PathVariable("id")Long id){
        return ResponseEntity.ok().body(this.outpatientService.updateOutpatient(id,outpatientDTO));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteOutpatient(@PathVariable("id") Long id){
        this.outpatientService.deleteOutpatient(id);
        return HttpStatus.OK;
    }


    @GetMapping("/pending/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllPendingOutpatientToday(){
        return ResponseEntity.ok().body(this.outpatientService.getAllPendingOutpatientToday());
    }

    @GetMapping("/pending/doctors/{doctorId}/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllPendingOutpatientToday(@PathVariable("doctorId") Long doctorId){
        return ResponseEntity.ok().body(this.outpatientService.findAllTodayPendingOutpatientByDoctor(doctorId));
    }

    @GetMapping("/process/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllProcessOutpatientToday(){
        return ResponseEntity.ok().body(this.outpatientService.getAllProcessOutpatientToday());
    }

    @GetMapping("/process/doctors/{doctorId}/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllProcessOutpatientToday(@PathVariable("doctorId") Long doctorId){
        return ResponseEntity.ok().body(this.outpatientService.findAllTodayProcessOutpatientByDoctor(doctorId));
    }

    @PutMapping("/process/{id}")
    public ResponseEntity<GetOutpatientDTO> updateOutpatientConditionToProcess(@PathVariable("id") Long condition_id){
        return ResponseEntity.ok().body(this.outpatientService.processOutpatient(condition_id));
    }

    @GetMapping("/done/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllDoneOutpatientToday(){
        return ResponseEntity.ok().body(this.outpatientService.getAllDoneOutpatientToday());
    }

    @GetMapping("/done/doctors/{doctorId}/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllDoneOutpatientToday(@PathVariable("doctorId") Long doctorId){
        return ResponseEntity.ok().body(this.outpatientService.findAllTodayDoneOutpatientByDoctor(doctorId));
    }

    @PutMapping("/done/{id}")
    public ResponseEntity<GetOutpatientDTO> updateOutpatientConditionToDone(@PathVariable("id") Long outpatient_id){
        return ResponseEntity.ok().body(this.outpatientService.doneOutpatient(outpatient_id));
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<List<GetOutpatientDTO>> getAllOutpatientByDepartment(@PathVariable("id") Long department_id){
        return ResponseEntity.ok().body(this.outpatientService.getAllTodayOutpatientByDepartment(department_id));
    }

    @PutMapping("/diagnosis/{id}")
    public ResponseEntity<GetOutpatientDTO> updateDiagnosisOutpatient(@PathVariable("id") Long outpatient_id, @RequestBody DiagnosisDTO diagnosisDTO){
        return ResponseEntity.ok().body(this.outpatientService.diagnosisOutpatient(outpatient_id, diagnosisDTO));
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<List<GetOutpatientDTO>> getAllOutpatientByDoctorASC(@PathVariable("id") Long doctor_id){
        return ResponseEntity.ok().body(this.outpatientService.getAllOutpatientByDoctor(doctor_id));
    }

    @GetMapping("/doctors/{id}/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllTodayOutpatientByDoctorASC(@PathVariable("id") Long doctor_id){
        return ResponseEntity.ok().body(this.outpatientService.getAllTodayOutpatientByDoctor(doctor_id));
    }

    @PutMapping("/truncate")
    public void truncateOutpatientTable(){
        this.outpatientService.truncateOutpatientTable();
    }

    @GetMapping("/patients/today")
    public ResponseEntity<List<GetOutpatientDTO>> getAllOutpatientByPatientNameToday(@RequestParam("name") String name){
        return ResponseEntity.ok().body(this.outpatientService.getAllOutpatientByPatientName(name));
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<GetOutpatientDTO>> getAllOutpatientByPatientIdToday(@PathVariable("patientId") Long patientId){
        return ResponseEntity.ok().body(this.outpatientService.getAllOutpatientByPatientIdToday(patientId));
    }
}
