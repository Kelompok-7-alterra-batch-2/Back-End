package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.DiagnosisDTO;
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

import static java.lang.System.out;
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

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.outpatientRepository.findAll()).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.getAllOutpatient();

        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
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
    public void updateOutpatient_willSuccess(){
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
        when(this.outpatientConditionService.getOutpatientById(outpatientDTO.getOutpatientCondition_id())).thenReturn(outpatientCondition);
        when(this.outpatientRepository.findById(id)).thenReturn(Optional.of(outpatient));
        when(this.outpatientRepository.save(any())).thenReturn(outpatient);
        when(this.outpatientService.convertDoctorEntityToResponse(outpatient.getDoctor())).thenReturn(getDoctorDTO);
        when(this.outpatientService.convertPatientEntityToResponse(outpatient.getPatient())).thenReturn(getPatientDTO);
        when(this.outpatientService.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO)).thenReturn(getOutpatientDTO);

        var result = this.outpatientService.updateOutpatient(id, outpatientDTO);

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

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.outpatientRepository.findAllByDate(now)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.findAllTodayOutpatient();

        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
//        verify(this.outpatientService, times(outpatientList.size())).convertOutpatientEntityToResponse(any(OutpatientEntity.class), any(GetDoctorDTO.class), any(GetPatientDTO.class));
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
    public void getAllPendingOutpatientToday_willSuccess() {
        // Given
        LocalDate now = LocalDate.now();
        OutpatientConditionEntity existCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.outpatientConditionService.getOutpatientById(1L)).thenReturn(existCondition);
        when(this.outpatientRepository.findAllByOutpatientConditionAndDateOrderByQueueAsc(existCondition, now)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.getAllPendingOutpatientToday();

        // Then
        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
    }

    @Test
    public void findAllTodayPendingOutpatientByDoctor() {
        // Given
        LocalDate now = LocalDate.now();
        UserEntity existDoctor = this.easyRandom.nextObject(UserEntity.class);
        OutpatientConditionEntity existCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.outpatientConditionService.getOutpatientById(1L)).thenReturn(existCondition);
        when(this.userService.getDoctorById(id)).thenReturn(existDoctor);
        when(this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctorOrderByQueueAsc(existCondition, now, existDoctor)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.findAllTodayPendingOutpatientByDoctor(id);

        // Then
        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
    }

    @Test
    public void processOutpatient() {
    }

    @Test
    public void getAllProcessOutpatientToday() {
        // Given
        LocalDate now = LocalDate.now();
        OutpatientConditionEntity existCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.outpatientConditionService.getOutpatientById(2L)).thenReturn(existCondition);
        when(this.outpatientRepository.findAllByOutpatientConditionAndDateOrderByQueueAsc(existCondition, now)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.getAllProcessOutpatientToday();

        // Then
        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
    }

    @Test
    public void findAllTodayProcessOutpatientByDoctor() {
        // Given
        LocalDate now = LocalDate.now();
        UserEntity existDoctor = this.easyRandom.nextObject(UserEntity.class);
        OutpatientConditionEntity existCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.outpatientConditionService.getOutpatientById(2L)).thenReturn(existCondition);
        when(this.userService.getDoctorById(id)).thenReturn(existDoctor);
        when(this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctorOrderByQueueAsc(existCondition, now, existDoctor)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.findAllTodayProcessOutpatientByDoctor(id);

        // Then
        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
    }

    @Test
    public void doneOutpatient() {
        OutpatientConditionEntity outpatientCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient = this.easyRandom.nextObject(OutpatientEntity.class);
        GetOutpatientDTO getOutpatientDTO = this.easyRandom.nextObject(GetOutpatientDTO.class);
        GetDoctorDTO getDoctorDTO = this.easyRandom.nextObject(GetDoctorDTO.class);
        GetPatientDTO getPatientDTO = this.easyRandom.nextObject(GetPatientDTO.class);

        when(this.outpatientConditionService.getOutpatientById(3L)).thenReturn(outpatientCondition);
        when(this.outpatientRepository.findById(id)).thenReturn(Optional.of(outpatient));
        when(this.outpatientRepository.save(any(OutpatientEntity.class))).thenReturn(outpatient);
        when(this.outpatientService.convertDoctorEntityToResponse(outpatient.getDoctor())).thenReturn(getDoctorDTO);
        when(this.outpatientService.convertPatientEntityToResponse(outpatient.getPatient())).thenReturn(getPatientDTO);
        when(this.outpatientService.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO)).thenReturn(getOutpatientDTO);

        var result = this.outpatientService.doneOutpatient(id);

        assertEquals(getOutpatientDTO, result);
    }

    @Test
    public void getAllDoneOutpatientToday() {
        // Given
        LocalDate now = LocalDate.now();
        OutpatientConditionEntity existCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.outpatientConditionService.getOutpatientById(3L)).thenReturn(existCondition);
        when(this.outpatientRepository.findAllByOutpatientConditionAndDateOrderByQueueAsc(existCondition, now)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.getAllDoneOutpatientToday();

        // Then
        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
    }

    @Test
    public void findAllTodayDoneOutpatientByDoctor() {
        // Given
        LocalDate now = LocalDate.now();
        UserEntity existDoctor = this.easyRandom.nextObject(UserEntity.class);
        OutpatientConditionEntity existCondition = this.easyRandom.nextObject(OutpatientConditionEntity.class);
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.outpatientConditionService.getOutpatientById(3L)).thenReturn(existCondition);
        when(this.userService.getDoctorById(id)).thenReturn(existDoctor);
        when(this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctorOrderByQueueAsc(existCondition, now, existDoctor)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.findAllTodayDoneOutpatientByDoctor(id);

        // Then
        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
    }

    @Test
    public void getAllTodayOutpatientByDepartment() {
    }

    @Test
    public void diagnosisOutpatient() {
        DiagnosisDTO diagnosisDTO = this.easyRandom.nextObject(DiagnosisDTO.class);
        OutpatientEntity outpatient = this.easyRandom.nextObject(OutpatientEntity.class);
        GetDoctorDTO getDoctorDTO = this.easyRandom.nextObject(GetDoctorDTO.class);
        GetPatientDTO getPatientDTO = this.easyRandom.nextObject(GetPatientDTO.class);
        GetOutpatientDTO getOutpatientDTO = this.easyRandom.nextObject(GetOutpatientDTO.class);

        when(this.outpatientRepository.findById(id)).thenReturn(Optional.of(outpatient));
        when(this.outpatientRepository.save(any(OutpatientEntity.class))).thenReturn(outpatient);

        when(this.outpatientService.convertDoctorEntityToResponse(outpatient.getDoctor())).thenReturn(getDoctorDTO);
        when(this.outpatientService.convertPatientEntityToResponse(outpatient.getPatient())).thenReturn(getPatientDTO);
        when(this.outpatientService.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO)).thenReturn(getOutpatientDTO);

        var result = this.outpatientService.diagnosisOutpatient(id, diagnosisDTO);

        assertEquals(getOutpatientDTO, result);
    }

    @Test
    public void getAllOutpatientByDoctor() {
        // Given
        LocalDate now = LocalDate.now();
        UserEntity existDoctor = this.easyRandom.nextObject(UserEntity.class);
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.userService.getDoctorById(id)).thenReturn(existDoctor);
        when(this.outpatientRepository.findAllByDoctorOrderByQueueAsc(existDoctor)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.getAllOutpatientByDoctor(id);

        // Then
        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
    }

    @Test
    public void getAllTodayOutpatientByDoctor() {
        // Given
        LocalDate now = LocalDate.now();
        UserEntity existDoctor = this.easyRandom.nextObject(UserEntity.class);
        OutpatientEntity outpatient1 = this.easyRandom.nextObject(OutpatientEntity.class);
        OutpatientEntity outpatient2 = this.easyRandom.nextObject(OutpatientEntity.class);

        List<OutpatientEntity> outpatientList = List.of(outpatient1, outpatient2);

        GetOutpatientDTO outpatientDto1 = this.mapper.map(outpatient1, GetOutpatientDTO.class);
        GetOutpatientDTO outpatientDto2 = this.mapper.map(outpatient2, GetOutpatientDTO.class);

        List<GetOutpatientDTO> outpatientDTOList = List.of(outpatientDto1, outpatientDto2);

        when(this.userService.getDoctorById(id)).thenReturn(existDoctor);
        when(this.outpatientRepository.findAllByDoctorAndDateOrderByQueueAsc(existDoctor, now)).thenReturn(outpatientList);

        // When
        var result = this.outpatientService.getAllTodayOutpatientByDoctor(id);

        // Then
        for (int i = 0; i < outpatientDTOList.size(); i++) {
            GetOutpatientDTO dto = outpatientDTOList.get(i);
            GetOutpatientDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAppointmentReason(), res.getAppointmentReason());
            assertEquals(dto.getArrivalTime(), res.getArrivalTime());
            assertEquals(dto.getDate(), res.getDate());
            assertEquals(dto.getOutpatientCondition(), res.getOutpatientCondition());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getDiagnosis(), res.getDiagnosis());
            assertEquals(dto.getPrescription(), res.getPrescription());
            assertEquals(dto.getPatient().getId(), res.getPatient().getId());
        }
    }

    @Test
    public void truncateOutpatientTable() {
        // When
        this.outpatientService.truncateOutpatientTable();

        // Then
        verify(this.outpatientRepository, times(1)).truncateMyTable();
    }
}