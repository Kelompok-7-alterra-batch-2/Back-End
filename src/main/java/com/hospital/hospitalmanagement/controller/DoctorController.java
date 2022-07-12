package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorScheduleDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorTwoDTO;
import com.hospital.hospitalmanagement.controller.response.GetScheduleDTO;
import com.hospital.hospitalmanagement.entities.ScheduleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.ScheduleServiceImpl;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    ScheduleServiceImpl scheduleService;

    @GetMapping("/count")
    public Long countAllDoctor(){
        return this.userService.countDoctor();
    }

    @GetMapping
    public List<GetDoctorTwoDTO> getAllDoctor(){
        return this.userService.getAllDoctor();
    }

    @GetMapping("/{id}")
    public GetDoctorTwoDTO getDoctorById(@PathVariable("id") Long id){
        return this.userService.getById(id);
    }

    @GetMapping("/departments/{departmentId}")
    public List<UserEntity> getAllDoctorByDepartment(@PathVariable("departmentId") Long departmentId){
        return this.userService.getDoctorByDepartment(departmentId);
    }

    @GetMapping("/emails")
    public UserEntity getDoctorByEmail(@RequestParam("email") String email){
        return this.userService.getDoctorByEmail(email);
    }

    @PostMapping
    public UserEntity createDoctor(@Valid @RequestBody DoctorDTO doctorDTO){
        return this.userService.createDoctor(doctorDTO);
    }

    @PutMapping("/{id}")
    public UserEntity updateDoctorById(@Valid @RequestBody DoctorDTO doctorDTO, @PathVariable("id") Long id){
        return this.userService.updateDoctor(id, doctorDTO);
    }

//    @PutMapping("/{id}/schedule")
//    public UserEntity updateDoctorSchedule(@PathVariable("id") Long doctorId, @RequestBody DoctorScheduleDTO doctorScheduleDTO){
//        return this.userService.updateDoctorSchedule(doctorId, doctorScheduleDTO);
//    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteDoctorById(@PathVariable("id") Long id){
        this.userService.deleteDoctor(id);
        return HttpStatus.OK;
    }

    @GetMapping("/names/{name}")
    public List<UserEntity> getDoctorByName(@PathVariable("name") String name){
        return this.userService.getDoctorByName(name);
    }

    @GetMapping("/page/{index}/{element}")
    public Page<UserEntity> getAllDoctorPaginate(@PathVariable("index") int index, @PathVariable("element") int element){
        return this.userService.getAllDoctorPaginate(index, element);
    }

    @GetMapping("/schedule/{id}")
    public GetScheduleDTO getScheduleById(@PathVariable("id") Long id){
        return this.scheduleService.getById(id);
    }

    @GetMapping("/schedule")
    public List<GetScheduleDTO> getAllSchedule(){
        return this.scheduleService.getAllSchedule();
    }

    @GetMapping("/schedules/available")
    public List<GetScheduleDTO> getAvailableSchedule(@RequestParam("department_id") Long departmentId ,@RequestParam("arrivalTime")String arrivalTime){
        return this.scheduleService.getScheduleFromArrivalTime(departmentId, arrivalTime);
    }

    @PostMapping("/schedule")
    public GetScheduleDTO createdSchedule(@RequestBody DoctorScheduleDTO doctorScheduleDTO){
        return this.scheduleService.createSchedule(doctorScheduleDTO);
    }

    @PutMapping("/schedule/{id}")
    public GetScheduleDTO newUpdateSchedule(@PathVariable("id") Long id, @RequestBody DoctorScheduleDTO doctorScheduleDTO){
        return this.scheduleService.updateSchedule(id,doctorScheduleDTO);
    }

    @DeleteMapping("/schedule/{id}")
    public HttpStatus deleteSchedule(@PathVariable("id") Long id){
        this.scheduleService.deleteSchedule(id);
        return HttpStatus.OK;
    }

}
