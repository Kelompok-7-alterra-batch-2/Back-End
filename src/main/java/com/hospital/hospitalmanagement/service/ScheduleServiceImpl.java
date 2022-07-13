package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.DoctorScheduleDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetScheduleDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.ScheduleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    DepartmentServiceImpl departmentService;

    ModelMapper mapper = new ModelMapper();

    public GetScheduleDTO convertFromScheduleEntityToGetScheduleDTO(ScheduleEntity schedule, GetDoctorDTO getDoctorDTO){
       GetScheduleDTO getScheduleDTO = GetScheduleDTO.builder()
               .id(schedule.getId())
               .doctor(getDoctorDTO)
               .availableTo(schedule.getAvailableTo())
               .availableFrom(schedule.getAvailableFrom())
               .createdAt(schedule.getCreatedAt())
               .build();

       return getScheduleDTO;
    }

    public List<GetScheduleDTO> getAllSchedule(){
        List<ScheduleEntity> scheduleList = this.scheduleRepository.findAll();

        List<GetScheduleDTO> getScheduleDTOList = scheduleList.stream()
                .map(schedule -> convertFromScheduleEntityToGetScheduleDTO(
                        schedule,
                        this.mapper.map(schedule.getDoctor(), GetDoctorDTO.class)
                )).collect(Collectors.toList());

        return getScheduleDTOList;
    }

    public GetScheduleDTO getById(Long id){
        ScheduleEntity scheduleEntity = this.scheduleRepository.findById(id).get();

        GetDoctorDTO getDoctorDTO = this.mapper.map(scheduleEntity.getDoctor(), GetDoctorDTO.class);

        return this.convertFromScheduleEntityToGetScheduleDTO(scheduleEntity, getDoctorDTO);
    }

    public List<GetScheduleDTO> getScheduleFromArrivalTime(Long departmentId, String arrivalTime){
        LocalTime time = LocalTime.parse(arrivalTime);
        DepartmentEntity existDepartment = this.departmentService.getDepartmentById(departmentId);
        List<ScheduleEntity> existSchedule = this.scheduleRepository.findAllByAvailableFromLessThanAndAvailableToGreaterThanAndDoctorDepartment(time, time, existDepartment);

        List<GetScheduleDTO> getScheduleDTOList = existSchedule.stream()
                .map(schedule -> convertFromScheduleEntityToGetScheduleDTO(
                        schedule,
                        this.mapper.map(schedule.getDoctor(), GetDoctorDTO.class)
                )).collect(Collectors.toList());

        return getScheduleDTOList;
    }

    public GetScheduleDTO createSchedule(DoctorScheduleDTO doctorScheduleDTO) {
        UserEntity existDoctor = this.userService.getDoctorById(doctorScheduleDTO.getDoctor_id());

        ScheduleEntity newSchedule = ScheduleEntity.builder()
                .doctor(existDoctor)
                .availableFrom(doctorScheduleDTO.getAvailableFrom())
                .availableTo(doctorScheduleDTO.getAvailableTo())
                .createdAt(LocalDateTime.now())
                .build();

        ScheduleEntity savedSchedule = this.scheduleRepository.save(newSchedule);

        return this.convertFromScheduleEntityToGetScheduleDTO(savedSchedule, this.mapper.map(existDoctor, GetDoctorDTO.class));
    }

    public GetScheduleDTO updateSchedule(Long id,DoctorScheduleDTO doctorScheduleDTO) {
        UserEntity existDoctor = this.userService.getDoctorById(doctorScheduleDTO.getDoctor_id());

        ScheduleEntity schedule = this.scheduleRepository.getById(id);
        schedule.setDoctor(existDoctor);
        schedule.setAvailableFrom(doctorScheduleDTO.getAvailableFrom());
        schedule.setAvailableTo(doctorScheduleDTO.getAvailableTo());

        ScheduleEntity savedSchedule = this.scheduleRepository.save(schedule);

        return this.convertFromScheduleEntityToGetScheduleDTO(savedSchedule, this.mapper.map(savedSchedule.getDoctor(), GetDoctorDTO.class));
    }

    public void deleteSchedule(Long id){
        ScheduleEntity schedule = this.scheduleRepository.getById(id);
        this.scheduleRepository.delete(schedule);
    }
}
