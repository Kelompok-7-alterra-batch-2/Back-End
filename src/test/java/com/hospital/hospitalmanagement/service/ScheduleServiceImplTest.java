package com.hospital.hospitalmanagement.service;



import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorScheduleDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetScheduleDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.ScheduleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.ScheduleRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ScheduleServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    @Spy
    ScheduleServiceImpl scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    DepartmentServiceImpl departmentService;

    @Mock
    UserServiceImpl userService;

    @Spy
    ModelMapper mapper = new ModelMapper();

    @Before
    public void setUp(){
        this.id = this.easyRandom.nextLong();
    }

    @Test
    public void convertFromScheduleEntityToGetScheduleDTO() {
    }

    @Test
    public void getAllSchedule() {
        // Then
        ScheduleEntity schedule1 = this.easyRandom.nextObject(ScheduleEntity.class);
        ScheduleEntity schedule2 = this.easyRandom.nextObject(ScheduleEntity.class);

        List<ScheduleEntity> scheduleList = List.of(schedule1, schedule2);

        GetScheduleDTO getSchedule1 = this.mapper.map(schedule1, GetScheduleDTO.class);
        GetScheduleDTO getSchedule2 = this.mapper.map(schedule2, GetScheduleDTO.class);

        List<GetScheduleDTO> getScheduleDTOList = List.of(getSchedule1, getSchedule2);

        when(this.scheduleRepository.findAll()).thenReturn(scheduleList);


        // When
        var result = this.scheduleService.getAllSchedule();

        // Then
        for (int i = 0; i < getScheduleDTOList.size(); i++) {
            GetScheduleDTO dto = getScheduleDTOList.get(i);
            GetScheduleDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAvailableFrom(), res.getAvailableFrom());
            assertEquals(dto.getAvailableTo(), res.getAvailableTo());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getCreatedAt(), res.getCreatedAt());
        }
    }

    @Test
    public void getById() {
        ScheduleEntity schedule = this.easyRandom.nextObject(ScheduleEntity.class);
        GetDoctorDTO getDoctorDTO = this.mapper.map(schedule.getDoctor(), GetDoctorDTO.class);
        GetScheduleDTO getScheduleDTO = this.mapper.map(schedule, GetScheduleDTO.class);

        when(this.scheduleRepository.findById(id)).thenReturn(Optional.of(schedule));
        when(this.scheduleService.convertFromScheduleEntityToGetScheduleDTO(schedule, getDoctorDTO)).thenReturn(getScheduleDTO);

        var result = this.scheduleService.getById(id);

        assertEquals(getScheduleDTO.getId(), result.getId());
        assertEquals(getScheduleDTO.getAvailableFrom(), result.getAvailableFrom());
        assertEquals(getScheduleDTO.getAvailableTo(), result.getAvailableTo());
        assertEquals(getScheduleDTO.getCreatedAt(), result.getCreatedAt());
        assertEquals(getScheduleDTO.getDoctor().getId(), result.getDoctor().getId());
    }

    @Test
    public void getScheduleFromArrivalTime() {
        // Then
        String timeString = "10:00:00";
        LocalTime time = LocalTime.parse(timeString);
        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);
        ScheduleEntity schedule1 = this.easyRandom.nextObject(ScheduleEntity.class);
        ScheduleEntity schedule2 = this.easyRandom.nextObject(ScheduleEntity.class);

        List<ScheduleEntity> scheduleList = List.of(schedule1, schedule2);

        GetScheduleDTO getSchedule1 = this.mapper.map(schedule1, GetScheduleDTO.class);
        GetScheduleDTO getSchedule2 = this.mapper.map(schedule2, GetScheduleDTO.class);

        List<GetScheduleDTO> getScheduleDTOList = List.of(getSchedule1, getSchedule2);

        when(this.departmentService.getDepartmentById(id)).thenReturn(department);
        when(this.scheduleRepository.findAllByAvailableFromLessThanAndAvailableToGreaterThanAndDoctorDepartment(time, time, department)).thenReturn(scheduleList);

        // When
        var result = this.scheduleService.getScheduleFromArrivalTime(id, timeString);

        // Then
        for (int i = 0; i < getScheduleDTOList.size(); i++) {
            GetScheduleDTO dto = getScheduleDTOList.get(i);
            GetScheduleDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getAvailableFrom(), res.getAvailableFrom());
            assertEquals(dto.getAvailableTo(), res.getAvailableTo());
            assertEquals(dto.getDoctor().getId(), res.getDoctor().getId());
            assertEquals(dto.getCreatedAt(), res.getCreatedAt());
        }
    }

    @Test
    public void createSchedule() {
        // Given
        DoctorScheduleDTO doctorScheduleDTO = this.easyRandom.nextObject(DoctorScheduleDTO.class);
        ScheduleEntity schedule = this.easyRandom.nextObject(ScheduleEntity.class);
        GetScheduleDTO getScheduleDTO = this.mapper.map(schedule, GetScheduleDTO.class);
        GetDoctorDTO getDoctorDTO = getScheduleDTO.getDoctor();
        UserEntity doctor = this.mapper.map(getDoctorDTO, UserEntity.class);

        when(this.userService.getDoctorById(doctorScheduleDTO.getDoctor_id())).thenReturn(doctor);
        when(this.scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(schedule);
        when(this.scheduleService.convertFromScheduleEntityToGetScheduleDTO(schedule, getDoctorDTO)).thenReturn(getScheduleDTO);

        // When
        var result = this.scheduleService.createSchedule(doctorScheduleDTO);

        // Then
        assertEquals(getScheduleDTO.getId(), result.getId());
        assertEquals(getScheduleDTO.getAvailableFrom(), result.getAvailableFrom());
        assertEquals(getScheduleDTO.getAvailableTo(), result.getAvailableTo());
        assertEquals(getScheduleDTO.getCreatedAt(), result.getCreatedAt());
        assertEquals(getScheduleDTO.getDoctor().getId(), result.getDoctor().getId());

    }

    @Test
    public void updateSchedule() {
        // Given
        DoctorScheduleDTO doctorScheduleDTO = this.easyRandom.nextObject(DoctorScheduleDTO.class);
        UserEntity doctor = this.easyRandom.nextObject(UserEntity.class);
        ScheduleEntity schedule = this.easyRandom.nextObject(ScheduleEntity.class);
        schedule.setDoctor(doctor);
        schedule.setAvailableFrom(doctorScheduleDTO.getAvailableFrom());
        schedule.setAvailableTo(doctorScheduleDTO.getAvailableTo());

        GetDoctorDTO getDoctorDTO = this.mapper.map(doctor, GetDoctorDTO.class);

        GetScheduleDTO getScheduleDTO = this.mapper.map(schedule, GetScheduleDTO.class);
        getScheduleDTO.setDoctor(getDoctorDTO);

        when(this.userService.getDoctorById(doctorScheduleDTO.getDoctor_id())).thenReturn(doctor);
        when(this.scheduleRepository.getById(id)).thenReturn(schedule);
        when(this.scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(schedule);
        when(this.scheduleService.convertFromScheduleEntityToGetScheduleDTO(schedule, getDoctorDTO)).thenReturn(getScheduleDTO);

        // When
        var result = this.scheduleService.updateSchedule(id, doctorScheduleDTO);

        // Then
        assertEquals(getScheduleDTO.getId(), result.getId());
        assertEquals(getScheduleDTO.getAvailableFrom(), result.getAvailableFrom());
        assertEquals(getScheduleDTO.getAvailableTo(), result.getAvailableTo());
        assertEquals(getScheduleDTO.getCreatedAt(), result.getCreatedAt());
        assertEquals(getScheduleDTO.getDoctor().getId(), result.getDoctor().getId());
    }

    @Test
    public void deleteSchedule() {
        // Given
        ScheduleEntity schedule = this.easyRandom.nextObject(ScheduleEntity.class);

        // When
        this.scheduleRepository.delete(schedule);

        // Then
        verify(this.scheduleRepository, times(1)).delete(schedule);
    }
}