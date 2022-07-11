package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.OutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.entities.*;
import com.hospital.hospitalmanagement.repository.OutpatientRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class OutpatientServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    @Spy
    OutpatientServiceImpl outpatientService;

    @Mock
    PatientServiceImpl patientService;

    @Mock
    UserServiceImpl userService;

    @Mock
    DepartmentServiceImpl departmentService;

    @Mock
    OutpatientConditionServiceImpl outpatientConditionService;

    @Mock
    OutpatientRepository outpatientRepository;

    @Spy
    private ModelMapper mapper = new ModelMapper();

    @Before
    public void setUp(){
        this.id = this.easyRandom.nextLong();
    }

    @Test
    public void getAllOutpatient_willSuccess(){
        // Given
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        when(this.outpatientRepository.findAll()).thenReturn(outpatientList);

        // When
        this.outpatientService.getAllOutpatient();

        // Then
        verify(this.outpatientRepository, times(1)).findAll();
    }

    @Test
    public void getOutpatientById_willSuccess(){
        // Given
        OutpatientEntity outpatient = this.easyRandom.nextObject(OutpatientEntity.class);

        GetDoctorDTO getDoctorDTO = this.mapper.map(outpatient.getDoctor(), GetDoctorDTO.class);
        GetPatientDTO getPatientDTO = this.mapper.map(outpatient.getPatient(), GetPatientDTO.class);
        GetOutpatientDTO getOutpatientDTO = this.mapper.map(outpatient, GetOutpatientDTO.class);

        when(this.outpatientRepository.findById(id)).thenReturn(Optional.of(outpatient));
        when(this.outpatientService.convertDoctorEntityToResponse(outpatient.getDoctor())).thenReturn(getDoctorDTO);
        when(this.outpatientService.convertPatientEntityToResponse(outpatient.getPatient())).thenReturn(getPatientDTO);
        when(this.outpatientService.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO)).thenReturn(getOutpatientDTO);

        // When
        var result = this.outpatientService.getOutpatientById(id);

        // Then
        assertEquals(getOutpatientDTO, result);
    }

    @Test
    public void getById_willSuccess(){
        // Given
        OutpatientEntity outpatient = this.easyRandom.nextObject(OutpatientEntity.class);

        when(this.outpatientRepository.findById(id)).thenReturn(Optional.of(outpatient));

        // When
        var result = this.outpatientService.getById(id);

        // Then
        assertEquals(outpatient, result);
    }

    @Test
    public void createOutpatient_willSuccess(){
        OutpatientDTO outpatientDTO = this.easyRandom.nextObject(OutpatientDTO.class);
        PatientEntity patient = this.easyRandom.nextObject(PatientEntity.class);
        UserEntity doctor = this.easyRandom.nextObject(UserEntity.class);
        DepartmentEntity departmentEntity = this.easyRandom.nextObject(DepartmentEntity.class);
        OutpatientConditionEntity outpatientCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient = this.easyRandom.nextObject(OutpatientEntity.class);

        GetDoctorDTO getDoctorDTO = this.mapper.map(outpatient.getDoctor(), GetDoctorDTO.class);
        GetPatientDTO getPatientDTO = this.mapper.map(outpatient.getPatient(), GetPatientDTO.class);
        GetOutpatientDTO getOutpatientDTO = this.mapper.map(outpatient, GetOutpatientDTO.class);

        when(this.patientService.getPatientById(outpatientDTO.getPatient_id())).thenReturn(patient);
        when(this.userService.getDoctorById(outpatientDTO.getDoctor_id())).thenReturn(doctor);
        when(this.departmentService.getDepartmentById(outpatientDTO.getDepartment_id())).thenReturn(departmentEntity);
        when(this.outpatientConditionService.getOutpatientById(1L)).thenReturn(outpatientCondition);
        when(this.outpatientRepository.save(any(OutpatientEntity.class))).thenReturn(outpatient);
        outpatient.setId(1L);
        outpatient.setQueue(Math.toIntExact(outpatient.getId()));
        when(this.outpatientRepository.save(any(OutpatientEntity.class))).thenReturn(outpatient);

        when(this.outpatientService.convertDoctorEntityToResponse(outpatient.getDoctor())).thenReturn(getDoctorDTO);
        when(this.outpatientService.convertPatientEntityToResponse(outpatient.getPatient())).thenReturn(getPatientDTO);
        when(this.outpatientService.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO)).thenReturn(getOutpatientDTO);

        var result = this.outpatientService.createOutpatient(outpatientDTO);

        assertEquals(getOutpatientDTO, result);
    }


    @Test
    public void deleteOutpatient() {
        OutpatientEntity outpatient = this.easyRandom.nextObject(OutpatientEntity.class);

        when(this.outpatientRepository.findById(id)).thenReturn(Optional.of(outpatient));

        this.outpatientService.deleteOutpatient(id);

        verify(this.outpatientRepository, times(1)).delete(outpatient);
    }

    @Test
    public void findAllTodayOutpatient() {
        // Given
        LocalDate now = LocalDate.now();
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);


        when(this.outpatientRepository.findAllByDate(now)).thenReturn(outpatientList);

        this.outpatientService.findAllTodayOutpatient();

        verify(this.outpatientService, times(outpatientList.size())).convertOutpatientEntityToResponse(any(OutpatientEntity.class), any(GetDoctorDTO.class), any(GetPatientDTO.class));
    }

    @Test
    public void countTodayOutpatient() {
        LocalDate now = LocalDate.now();
        Long count = 3L;

        when(this.outpatientRepository.countByDate(now)).thenReturn(count);

        var result = this.outpatientService.countTodayOutpatient();

        assertEquals(count, result);
    }

    @Test
    public void getAllAvailableDoctor() {
        LocalTime arrivalTime = LocalTime.parse("11:00:00");
        UserEntity user1 = this.easyRandom.nextObject(UserEntity.class);
        UserEntity user2 = this.easyRandom.nextObject(UserEntity.class);

        List<UserEntity> userList = List.of(user1, user2);

        when(this.userService.findAllAvailableDoctor(arrivalTime, id)).thenReturn(userList);

        var result = this.outpatientService.getAllAvailableDoctor("11:00:00", id);

        assertEquals(userList, result);
    }

    @Test
    public void getAllPendingOutpatient() {
    }

    @Test
    public void findAllTodayPendingOutpatientByDoctor() {
    }

    @Test
    public void processOutpatient() {
        OutpatientConditionEntity outpatientCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient = this.easyRandom.nextObject(OutpatientEntity.class);
        GetOutpatientDTO getOutpatientDTO = this.easyRandom.nextObject(GetOutpatientDTO.class);
        GetDoctorDTO getDoctorDTO = this.easyRandom.nextObject(GetDoctorDTO.class);
        GetPatientDTO getPatientDTO = this.easyRandom.nextObject(GetPatientDTO.class);

        when(this.outpatientConditionService.getOutpatientById(2L)).thenReturn(outpatientCondition);
        when(this.outpatientRepository.findById(id)).thenReturn(Optional.of(outpatient));
        when(this.outpatientRepository.save(any(OutpatientEntity.class))).thenReturn(outpatient);
        when(this.outpatientService.convertDoctorEntityToResponse(any(UserEntity.class))).thenReturn(getDoctorDTO);
        when(this.outpatientService.convertPatientEntityToResponse(any(PatientEntity.class))).thenReturn(getPatientDTO);
        when(this.outpatientService.convertOutpatientEntityToResponse(any(OutpatientEntity.class), any(GetDoctorDTO.class), any(GetPatientDTO.class))).thenReturn(getOutpatientDTO);

        var result = this.outpatientService.processOutpatient(id);

        assertEquals(getOutpatientDTO, result);
    }

    @Test
    public void getAllProcessOutpatient() {
    }

    @Test
    public void findAllTodayProcessOutpatientByDoctor() {
    }

    @Test
    public void doneOutpatient() {
    }

    @Test
    public void getAllDoneOutpatient() {
    }

    @Test
    public void findAllTodayDoneOutpatientByDoctor() {
    }

    @Test
    public void getAllTodayOutpatientByDepartment() {
    }

    @Test
    public void diagnosisOutpatient() {
    }

    @Test
    public void getAllOutpatientByDoctor() {
    }

    @Test
    public void getAllTodayOutpatientByDoctor() {
    }

    @Test
    public void truncateOutpatientTable() {
    }
}