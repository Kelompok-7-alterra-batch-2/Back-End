package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientTwoDTO;
import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.entities.GenderEntity;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.repository.PatientRepository;
import org.checkerframework.checker.nullness.Opt;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PatientServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    @Spy
    PatientServiceImpl patientService;

    @Mock
    PatientRepository patientRepository;

    @Mock
    GenderServiceImpl genderService;

    @Mock
    BloodTypeServiceImpl bloodTypeService;

    @Spy
    private ModelMapper mapper = new ModelMapper();

    @Before
    public void setUp(){
        this.id = this.easyRandom.nextLong();
    }

    @Test
    public void getAllPatient_willSuccess(){
        // Given
        PatientEntity patient1 = this.easyRandom.nextObject(PatientEntity.class);
        PatientEntity patient2 = this.easyRandom.nextObject(PatientEntity.class);

        List<PatientEntity> patientList = List.of(patient1, patient2);

        when(this.patientRepository.findAll()).thenReturn(patientList);

        // When
        var result = this.patientService.getAllPatient();

        // Then
        verify(this.patientRepository, times(1)).findAll();
    }

    @Test
    public void getById_willSuccess(){
        // Given
        PatientEntity patient = this.easyRandom.nextObject(PatientEntity.class);
        GetPatientTwoDTO patientDTO = this.mapper.map(patient, GetPatientTwoDTO.class);

        when(this.patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(this.patientService.convertPatientEntityToResponse(patient)).thenReturn(patientDTO);

        // When
        var result = this.patientService.getById(id);

        // Then
        assertEquals(patientDTO, result);
    }

    @Test
    public void getPatientById_willSuccess(){
        // Given
        PatientEntity patient = this.easyRandom.nextObject(PatientEntity.class);

        when(this.patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // When
        var result = this.patientService.getPatientById(id);

        // Then
        assertEquals(patient, result);
    }

    @Test
    public void createPatient_willSuccess(){
        // Given
        PatientDTO patientDTO = this.easyRandom.nextObject(PatientDTO.class);
        patientDTO.setDob("2001-10-23");
        PatientEntity patient = this.easyRandom.nextObject(PatientEntity.class);
        BloodTypeEntity bloodType = this.easyRandom.nextObject(BloodTypeEntity.class);
        GenderEntity gender = this.easyRandom.nextObject(GenderEntity.class);


        when(this.bloodTypeService.getBloodTypeById(patientDTO.getBlood_type_id())).thenReturn(bloodType);
        when(this.genderService.getGenderById(patientDTO.getGender_id())).thenReturn(gender);
        when(this.patientRepository.save(any(PatientEntity.class))).thenReturn(patient);

        // When
        var result = this.patientService.createPatient(patientDTO);

        // Then
        assertEquals(patient, result);
    }

    @Test
    public void updatePatient_willSuccess(){
        // Given
        PatientDTO patientDTO = this.easyRandom.nextObject(PatientDTO.class);
        patientDTO.setDob("2001-10-23");
        PatientEntity patient = this.easyRandom.nextObject(PatientEntity.class);
        BloodTypeEntity bloodType = this.easyRandom.nextObject(BloodTypeEntity.class);
        GenderEntity gender = this.easyRandom.nextObject(GenderEntity.class);

        when(this.patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(this.bloodTypeService.getBloodTypeById(patientDTO.getBlood_type_id())).thenReturn(bloodType);
        when(this.genderService.getGenderById(patientDTO.getGender_id())).thenReturn(gender);
        when(this.patientRepository.save(any(PatientEntity.class))).thenReturn(patient);

        // When
        var result = this.patientService.updatePatient(id, patientDTO);

        // Then
        assertEquals(patient, result);
    }

    @Test
    public void deletePatient_willSuccess(){
        // Given
        PatientEntity patient = this.easyRandom.nextObject(PatientEntity.class);

        when(this.patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // When
        this.patientService.deletePatient(id);

        // Then
        verify(this.patientRepository, times(1)).delete(patient);
    }

    @Test
    public void getPatientByName_willSuccess(){
        // Given
        PatientEntity patient1 = this.easyRandom.nextObject(PatientEntity.class);
        PatientEntity patient2 = this.easyRandom.nextObject(PatientEntity.class);

        List<PatientEntity> patientList = List.of(patient1, patient2);

        String name = patient1.getName();

        when(this.patientRepository.findByNameContainsIgnoreCase(name)).thenReturn(patientList);

        // When
        var result = this.patientService.getPatientByName(name);

        // Then
        assertEquals(patientList, result);
    }

    @Test
    public void getAllPatientPaginate_willSuccess(){
        // Given
        int index = this.easyRandom.nextInt(10);
        int element = this.easyRandom.nextInt(10);

        // When
        this.patientService.getAllPatientPaginate(index, element);

        // Then
        verify(this.patientRepository, times(1)).findAll(PageRequest.of(index, element));
    }

}