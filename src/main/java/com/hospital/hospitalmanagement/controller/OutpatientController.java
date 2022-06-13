package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.OutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.service.OutpatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/outpatients")
public class OutpatientController {

    @Autowired
    OutpatientServiceImpl outpatientService;

    @GetMapping
    public List<GetOutpatientDTO> getAllOutpatient(){
        return this.outpatientService.getAllOutpatient();
    }

    @GetMapping("/{id}")
    public GetOutpatientDTO getOutpatientById(@PathVariable("id") Long id){
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
