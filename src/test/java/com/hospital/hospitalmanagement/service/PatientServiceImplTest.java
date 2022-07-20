package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientTwoDTO;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.controller.validation.UnprocessableException;
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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        GetPatientTwoDTO patientDTO1 = this.mapper.map(patient1, GetPatientTwoDTO.class);
        GetPatientTwoDTO patientDTO2 = this.mapper.map(patient2, GetPatientTwoDTO.class);

        List<GetPatientTwoDTO> patientDTOList = List.of(patientDTO1, patientDTO2);

        when(this.patientRepository.findAll()).thenReturn(patientList);

        // When
        var result = this.patientService.getAllPatient();

        // Then
        for (int i = 0; i < patientDTOList.size(); i++) {
            PatientEntity patient = patientList.get(i);
            GetPatientTwoDTO patientDTO = patientDTOList.get(i);

            assertEquals(patient.getName(), patientDTO.getName());
            assertEquals(patient.getAddress(), patientDTO.getAddress());
            assertEquals(patient.getCity(), patientDTO.getCity());
            assertEquals(patient.getGender().getType(), patientDTO.getGender().getType());
            assertEquals(patient.getBloodType().getType(), patientDTO.getBloodType().getType());
            assertEquals(patient.getDob(), patientDTO.getDob());
            assertEquals(patient.getId(), patientDTO.getId());
            assertEquals(patient.getPhoneNumber(), patientDTO.getPhoneNumber());
            assertEquals(patient.getCreatedAt(), patientDTO.getCreatedAt());
        }
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
    public void getById_willFailed(){
        // Given
        PatientEntity patient = this.easyRandom.nextObject(PatientEntity.class);
        GetPatientTwoDTO patientDTO = this.mapper.map(patient, GetPatientTwoDTO.class);

        when(this.patientRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> this.patientService.getById(id), "Data Not Found");
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
    public void getPatientById_willFailed(){
        // Given
        PatientEntity patient = this.easyRandom.nextObject(PatientEntity.class);

        when(this.patientRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> this.patientService.getPatientById(id), "Data Not Found");
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
        Page<PatientEntity> pagePatient = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(this.patientRepository.findAll(pageable)).thenReturn(pagePatient);

        Page<GetPatientDTO> dtoPage = pagePatient.map(entity -> GetPatientDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .dob(entity.getDob())
                .bloodType(entity.getBloodType())
                .gender(entity.getGender())
                .city(entity.getCity())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .build());

        // When
        var result = this.patientService.getAllPatientPaginate(pageable);

        // Then
        assertEquals(dtoPage, result);
    }

}