package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.DepartmentDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.repository.DepartmentRepository;
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
public class DepartmentServiceImplTest {

    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Mock
    DepartmentRepository departmentRepository;

    @Before
    public void setUp(){
        this.id = easyRandom.nextLong();
    }

    @Test
    public void getAllDepartment_willSuccess() {
        // Given
        DepartmentEntity department1 = this.easyRandom.nextObject(DepartmentEntity.class);
        DepartmentEntity department2 = this.easyRandom.nextObject(DepartmentEntity.class);

        List<DepartmentEntity> departmentList = List.of(department1, department2);

        when(this.departmentRepository.findAll()).thenReturn(departmentList);

        // When
        var result = this.departmentService.getAllDepartment();

        // Then
        assertEquals(departmentList, result);
    }

    @Test
    public void getDepartmentById_willSuccess() {
        // Given
        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);

        when(this.departmentRepository.findById(id)).thenReturn(Optional.of(department));

        // When
        var result = this.departmentService.getDepartmentById(id);

        // Then
        assertEquals(department, result);
    }

    @Test
    public void createDepartment_willSuccess() {
        // Given
        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);
        DepartmentDTO departmentDTO = this.easyRandom.nextObject(DepartmentDTO.class);

        when(this.departmentRepository.save(any())).thenReturn(department);

        // When
        var result = this.departmentService.createDepartment(departmentDTO);

        // Then
        assertEquals(department, result);
    }
}