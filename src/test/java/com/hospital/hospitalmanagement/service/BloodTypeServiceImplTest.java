package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.BloodTypeDTO;
import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.repository.BloodTypeRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BloodTypeServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    BloodTypeServiceImpl bloodTypeService;

    @Mock
    BloodTypeRepository bloodTypeRepository;

    @Before
    public void setUp(){
        this.id = easyRandom.nextLong();
    }

    @Test
    public void getAllBloodTypes_willSuccess(){
        // Given
        BloodTypeEntity bloodType1 = this.easyRandom.nextObject(BloodTypeEntity.class);
        BloodTypeEntity bloodType2 = this.easyRandom.nextObject(BloodTypeEntity.class);

        List<BloodTypeEntity> bloodTypeList = List.of(bloodType1, bloodType2);

        when(this.bloodTypeRepository.findAll()).thenReturn(bloodTypeList);

        // When
        var result = this.bloodTypeService.getAllBloodTypes();

        // Then
        assertEquals(bloodTypeList, result);
    }

    @Test
    public void getBloodTypeById_willSuccess(){
        // Given
        BloodTypeEntity bloodType = this.easyRandom.nextObject(BloodTypeEntity.class);

        when(this.bloodTypeRepository.findById(id)).thenReturn(Optional.of(bloodType));

        // When
        var result = this.bloodTypeService.getBloodTypeById(id);

        // Then
        assertEquals(bloodType, result);
    }

    @Test
    public void createBloodType_willSuccess(){
        // Given
        BloodTypeEntity bloodType = this.easyRandom.nextObject(BloodTypeEntity.class);
        BloodTypeDTO bloodTypeDTO = this.easyRandom.nextObject(BloodTypeDTO.class);

        when(this.bloodTypeRepository.save(any())).thenReturn(bloodType);

        // When
        var result = this.bloodTypeService.createBloodType(bloodTypeDTO);

        // Then
        assertEquals(bloodType, result);
    }

}