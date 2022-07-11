package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.OutpatientConditionDTO;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.entities.OutpatientConditionEntity;
import com.hospital.hospitalmanagement.repository.OutpatientConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OutpatientConditionServiceImpl {
    @Autowired
    OutpatientConditionRepository conditionRepository;

    public List<OutpatientConditionEntity> getAllOutpatientCondition(){
        return this.conditionRepository.findAll();
    }

    public OutpatientConditionEntity getOutpatientById(Long id){
        Optional<OutpatientConditionEntity> optionalOutpatientCondition = this.conditionRepository.findById(id);

        if (optionalOutpatientCondition.isEmpty()){
            throw new NotFoundException("Data Not Found");
        }
        return optionalOutpatientCondition.get();
    }

    public OutpatientConditionEntity createOutpatientCondition(OutpatientConditionDTO outpatientConditionDTO){
        OutpatientConditionEntity outpatientCondition = OutpatientConditionEntity.builder()
                .conditions(outpatientConditionDTO.getConditions())
                .createdAt(LocalDateTime.now())
                .build();
        return this.conditionRepository.save(outpatientCondition);
    }
}
