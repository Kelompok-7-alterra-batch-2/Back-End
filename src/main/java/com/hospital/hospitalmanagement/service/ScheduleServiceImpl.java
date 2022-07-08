package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.DoctorScheduleDTO;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.entities.ScheduleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserServiceImpl userService;

    public List<ScheduleEntity> getAllSchedule(){
        return this.scheduleRepository.findAll();
    }

    public ScheduleEntity getById(Long id){
        return this.scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Data Not Found"));
    }

    public ScheduleEntity createSchedule(DoctorScheduleDTO doctorScheduleDTO) {
        UserEntity existDoctor = this.userService.getDoctorById(doctorScheduleDTO.getDoctor_id());

        ScheduleEntity newSchedule = ScheduleEntity.builder()
                .doctor_id(existDoctor)
                .availableFrom(doctorScheduleDTO.getAvailableFrom())
                .availableTo(doctorScheduleDTO.getAvailableTo())
                .createdAt(LocalDateTime.now())
                .build();

        return this.scheduleRepository.save(newSchedule);
    }

    public ScheduleEntity updateSchedule(Long id,DoctorScheduleDTO doctorScheduleDTO) {
        ScheduleEntity schedule = this.getById(id);

        UserEntity existDoctor = userService.getDoctorById(doctorScheduleDTO.getDoctor_id());

        schedule.setDoctor_id(existDoctor);
        schedule.setAvailableFrom(doctorScheduleDTO.getAvailableFrom());
        schedule.setAvailableTo(doctorScheduleDTO.getAvailableTo());

        return this.scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id){
        ScheduleEntity schedule = this.getById(id);
        this.scheduleRepository.delete(schedule);
    }
}
