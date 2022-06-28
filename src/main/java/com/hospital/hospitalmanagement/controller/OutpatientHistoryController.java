package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.OutpatientHistoryEntity;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.OutpatientHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Access;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/outpatientHistory")
public class OutpatientHistoryController {
    @Autowired
    OutpatientHistoryServiceImpl outpatientHistoryService;

    @GetMapping
    public List<GetOutpatientDTO> getAllOutpatientHistories(){

        return this.outpatientHistoryService.getAllOutpatientHistories();
    }

}
