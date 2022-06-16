package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.OutpatientConditionDTO;
import com.hospital.hospitalmanagement.entities.OutpatientConditionEntity;
import com.hospital.hospitalmanagement.service.OutpatientConditionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/outpatientCondition")
public class OutpatientConditionController {
    @Autowired
    OutpatientConditionServiceImpl outpatientCondition;

    @GetMapping
    public List<OutpatientConditionEntity> getAllOutpatientCondition(){
        return this.outpatientCondition.getAllOutpatientCondition();
    }

    @PostMapping
    public OutpatientConditionEntity createOutpatientCondition(@RequestBody OutpatientConditionDTO outpatientConditionDTO){
        return this.outpatientCondition.createOutpatientCondition(outpatientConditionDTO);
    }
}
