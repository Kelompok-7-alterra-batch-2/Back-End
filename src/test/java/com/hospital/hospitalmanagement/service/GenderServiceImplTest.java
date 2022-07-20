package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.GenderDTO;
import com.hospital.hospitalmanagement.entities.GenderEntity;
import com.hospital.hospitalmanagement.repository.GenderRepository;
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
public class GenderServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    GenderServiceImpl genderService;

    @Mock
    GenderRepository genderRepository;

    @Before
    public void setUp(){
        this.id = this.easyRandom.nextLong();
    }

    @Test
    public void getAllGenders_willSuccess(){
        // Given
        GenderEntity gender1 = this.easyRandom.nextObject(GenderEntity.class);
        GenderEntity gender2 = this.easyRandom.nextObject(GenderEntity.class);

        List<GenderEntity> genderList = List.of(gender1, gender2);

        when(this.genderRepository.findAll()).thenReturn(genderList);

        // When
        var result = this.genderService.getAllGenders();

        // Then
        assertEquals(genderList, result);
    }

    @Test
    public void getGenderById_willSuccess(){
        // Given
        GenderEntity gender = this.easyRandom.nextObject(GenderEntity.class);

        when(this.genderRepository.findById(id)).thenReturn(Optional.of(gender));

        // When
        var result = this.genderService.getGenderById(id);

        // Then
        assertEquals(gender, result);
    }

    @Test
    public void createGender_willSuccess(){
        // Given
        GenderEntity gender = this.easyRandom.nextObject(GenderEntity.class);
        GenderDTO genderDTO = this.easyRandom.nextObject(GenderDTO.class);

        when(this.genderRepository.save(any())).thenReturn(gender);

        // When
        var result = this.genderService.createGender(genderDTO);

        // Then
        assertEquals(gender, result);
    }

}