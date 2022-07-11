package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.OutpatientConditionDTO;
import com.hospital.hospitalmanagement.entities.OutpatientConditionEntity;
import com.hospital.hospitalmanagement.repository.OutpatientConditionRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OutpatientConditionServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    OutpatientConditionServiceImpl outpatientConditionService;

    @Mock
    OutpatientConditionRepository outpatientConditionRepository;

    @Before
    public void setUp(){
        this.id = this.easyRandom.nextLong();
    }

    @Test
    public void getAllOutpatientCondition_willSuccess(){
        // Given
        OutpatientConditionEntity outpatientCondition1 = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientConditionEntity outpatientCondition2 = this.easyRandom.nextObject(OutpatientConditionEntity.class);

        List<OutpatientConditionEntity> outpatientConditionList = List.of(outpatientCondition1, outpatientCondition2);

        when(this.outpatientConditionRepository.findAll()).thenReturn(outpatientConditionList);

        // When
        var result = this.outpatientConditionService.getAllOutpatientCondition();

        // Then
        assertEquals(outpatientConditionList, result);
    }

    @Test
    public void getOutpatientById_willSuccess(){
        // Given
        OutpatientConditionEntity outpatientCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);

        when(this.outpatientConditionRepository.findById(id)).thenReturn(Optional.of(outpatientCondition));

        // When
        var result = this.outpatientConditionService.getOutpatientById(id);

        // Then
        assertEquals(outpatientCondition, result);
    }

    @Test
    public void createOutpatientCondition_willSuccess(){
        // Given
        OutpatientConditionEntity outpatientCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientConditionDTO outpatientConditionDTO = this.easyRandom.nextObject(OutpatientConditionDTO.class);

        when(this.outpatientConditionRepository.save(any())).thenReturn(outpatientCondition);

        // When
        var result = this.outpatientConditionService.createOutpatientCondition(outpatientConditionDTO);

        // Then
        assertEquals(outpatientCondition, result);
    }
}