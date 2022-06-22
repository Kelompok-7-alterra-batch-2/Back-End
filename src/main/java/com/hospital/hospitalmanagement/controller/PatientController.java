package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientServiceImpl patientService;

    @GetMapping
    public List<GetPatientDTO> getPatients(){
        return this.patientService.getAllPatient();
    }

    @GetMapping("/{id}")
    public GetPatientDTO getById(@PathVariable("id") Long id){
        return this.patientService.getById(id);
    }

    @PostMapping
    public PatientEntity createPatient(@RequestBody PatientDTO patientDTO){
        return this.patientService.createPatient(patientDTO);
    }

    @PutMapping("/{id}")
    public PatientEntity updatePatient(@RequestBody PatientDTO patientDTO, @PathVariable("id") Long id){
        return this.patientService.updatePatient(id,patientDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePatientById(@PathVariable("id") Long id){
        this.patientService.deletePatient(id);
    }

    @GetMapping("/names/{name}")
    public List<PatientEntity> getPatientByName(@PathVariable("name") String name){
        return this.patientService.getPatientByName(name);
    }

    @GetMapping("/page/{index}/{element}")
    public Page<PatientEntity> getAllPatientPaginate(@PathVariable("index") int index, @PathVariable("element") int element){
        return this.patientService.getAllPatientPaginate(index, element);
    }
}
