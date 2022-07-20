package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.RoleDto;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.repository.RoleRepository;
import org.checkerframework.checker.nullness.Opt;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RoleServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;

    @Spy
    private ModelMapper mapper = new ModelMapper();

    @Before
    public void setUp(){
        this.id = this.easyRandom.nextLong();
    }

    @Test
    public void getAllRole_willSuccess(){
        // Given
        RoleEntity role1 = this.easyRandom.nextObject(RoleEntity.class);
        RoleEntity role2 = this.easyRandom.nextObject(RoleEntity.class);

        List<RoleEntity> roleList = List.of(role1, role2);

        when(this.roleRepository.findAll()).thenReturn(roleList);

        // When
        var result = this.roleService.getAllRole();

        // Then
        assertEquals(roleList, result);
    }

    @Test
    public void getRoleById_willSuccess(){
        // Given
        RoleEntity role = this.easyRandom.nextObject(RoleEntity.class);

        when(this.roleRepository.findById(id)).thenReturn(Optional.of(role));

        // When
        var result = this.roleService.getRoleById(id);

        // Then
        assertEquals(role, result);
    }

    @Test
    public void createRole_willSuccess(){
        // Given
        RoleDto roleDto = this.easyRandom.nextObject(RoleDto.class);
        RoleEntity role = this.mapper.map(roleDto, RoleEntity.class);

        when(this.roleRepository.save(any(RoleEntity.class))).thenReturn(role);

        // When
        var result = this.roleService.createRole(roleDto);

        // Then
        assertEquals(role, result);
    }

}