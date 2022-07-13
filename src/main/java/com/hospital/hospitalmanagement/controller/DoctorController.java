package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorScheduleDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorTwoDTO;
import com.hospital.hospitalmanagement.controller.response.GetScheduleDTO;
import com.hospital.hospitalmanagement.controller.validation.UnprocessableException;
import com.hospital.hospitalmanagement.entities.ScheduleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.ScheduleServiceImpl;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> countAllDoctor(){
        return ResponseEntity.ok().body(this.userService.countDoctor());
    }

    @GetMapping
    public ResponseEntity<List<GetDoctorTwoDTO>> getAllDoctor(){
        return ResponseEntity.ok().body(this.userService.getAllDoctor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDoctorTwoDTO> getDoctorById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.userService.getById(id));
    }

    @GetMapping("/departments/{departmentId}")
    public ResponseEntity<List<UserEntity>> getAllDoctorByDepartment(@PathVariable("departmentId") Long departmentId){
        return ResponseEntity.ok().body(this.userService.getDoctorByDepartment(departmentId));
    }

    @GetMapping("/emails")
    public ResponseEntity<UserEntity> getDoctorByEmail(@RequestParam("email") String email){
        return ResponseEntity.ok().body(this.userService.getDoctorByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserEntity> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO){
        return ResponseEntity.ok().body(this.userService.createDoctor(doctorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateDoctorById(@Valid @RequestBody DoctorDTO doctorDTO, @PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.userService.updateDoctor(id, doctorDTO));
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
    public ResponseEntity<List<UserEntity>> getDoctorByName(@PathVariable("name") String name){
        return ResponseEntity.ok().body(this.userService.getDoctorByName(name));
    }

    @GetMapping("/page/{index}/{element}")
    public ResponseEntity<Page<UserEntity>> getAllDoctorPaginate(@PathVariable("index") int index, @PathVariable("element") int element){
        return ResponseEntity.ok().body(this.userService.getAllDoctorPaginate(index, element));
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<GetScheduleDTO> getScheduleById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.scheduleService.getById(id));
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<GetScheduleDTO>> getAllSchedule(){
        return ResponseEntity.ok().body(this.scheduleService.getAllSchedule());
    }

    @GetMapping("/schedules/available")
    public ResponseEntity<List<GetScheduleDTO>> getAvailableSchedule(@RequestParam("department_id") Long departmentId ,@RequestParam("arrivalTime")String arrivalTime){
        return ResponseEntity.ok().body(this.scheduleService.getScheduleFromArrivalTime(departmentId, arrivalTime));
    }

    @PostMapping("/schedule")
    public ResponseEntity<GetScheduleDTO> createdSchedule(@RequestBody DoctorScheduleDTO doctorScheduleDTO){
        return ResponseEntity.ok().body(this.scheduleService.createSchedule(doctorScheduleDTO));
    }

    @PutMapping("/schedule/{id}")
    public ResponseEntity<GetScheduleDTO> newUpdateSchedule(@PathVariable("id") Long id, @RequestBody DoctorScheduleDTO doctorScheduleDTO){
        return ResponseEntity.ok().body(this.scheduleService.updateSchedule(id,doctorScheduleDTO));
    }

    @DeleteMapping("/schedule/{id}")
    public HttpStatus deleteSchedule(@PathVariable("id") Long id){
        this.scheduleService.deleteSchedule(id);
        return HttpStatus.OK;
    }

}
