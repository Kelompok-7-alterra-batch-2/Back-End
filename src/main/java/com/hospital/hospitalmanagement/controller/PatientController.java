package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientTwoDTO;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientServiceImpl patientService;

    @GetMapping
    public ResponseEntity<List<GetPatientTwoDTO>> getPatients(){
        return ResponseEntity.ok().body(this.patientService.getAllPatient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPatientTwoDTO> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.patientService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PatientEntity> createPatient(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok().body(this.patientService.createPatient(patientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientEntity> updatePatient(@RequestBody PatientDTO patientDTO, @PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.patientService.updatePatient(id,patientDTO));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePatientById(@PathVariable("id") Long id){
        this.patientService.deletePatient(id);
        return HttpStatus.OK;
    }

    @GetMapping("/names/{name}")
    public ResponseEntity<List<PatientEntity>> getPatientByName(@PathVariable("name") String name){
        return ResponseEntity.ok().body(this.patientService.getPatientByName(name));
    }

    @GetMapping("/page/{index}/{element}")
    public ResponseEntity<Page<PatientEntity>> getAllPatientPaginate(@PathVariable("index") int index, @PathVariable("element") int element){
        return ResponseEntity.ok().body(this.patientService.getAllPatientPaginate(index, element));
    }
}
