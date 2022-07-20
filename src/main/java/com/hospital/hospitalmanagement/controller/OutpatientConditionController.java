package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.OutpatientConditionDTO;
import com.hospital.hospitalmanagement.entities.OutpatientConditionEntity;
import com.hospital.hospitalmanagement.service.OutpatientConditionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/outpatientCondition")
public class OutpatientConditionController {
    @Autowired
    OutpatientConditionServiceImpl outpatientCondition;

    @GetMapping
    public ResponseEntity<List<OutpatientConditionEntity>> getAllOutpatientCondition(){
        return ResponseEntity.ok().body(this.outpatientCondition.getAllOutpatientCondition());
    }

    @PostMapping
    public ResponseEntity<OutpatientConditionEntity> createOutpatientCondition(@RequestBody OutpatientConditionDTO outpatientConditionDTO){
        return ResponseEntity.ok().body(this.outpatientCondition.createOutpatientCondition(outpatientConditionDTO));
    }
}
