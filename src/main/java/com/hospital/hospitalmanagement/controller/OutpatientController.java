package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.OutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.DepartmentRepository;
import com.hospital.hospitalmanagement.repository.OutpatientRepository;
import com.hospital.hospitalmanagement.repository.UserRepository;
import com.hospital.hospitalmanagement.service.OutpatientServiceImpl;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/outpatients")
public class OutpatientController {

    @Autowired
    OutpatientServiceImpl outpatientService;

    @Autowired
    OutpatientRepository outpatientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping("/today")
    public List<OutpatientEntity> getAllTodayOutpatient(){
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByDate(now);
    }

    @GetMapping("/count/today")
    public Long countTodayOutpatient(){
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.countByDate(now);
    }

    @GetMapping("/doctors")
    public List<UserEntity> getAllAvailableDoctor(@RequestBody DoctorDTO doctorDTO){
//        LocalTime parseArrivalTime = LocalTime.parse(arrivalTime);
//        DepartmentEntity department = this.departmentRepository.findById(doctorDTO.getDepartment_id()).get();
        return this.userRepository.findAllAvailableDoctor(doctorDTO.getAvailableTo(), doctorDTO.getDepartment_id());
    }

    @GetMapping
    public List<GetOutpatientDTO> getAllOutpatient(){
        return this.outpatientService.getAllOutpatient();
    }

    @GetMapping("/{id}")
    public GetOutpatientDTO getOutpatientById(@PathVariable("id") Long id){
        return this.outpatientService.getById(id);
    }

    @PostMapping
    public OutpatientEntity createOutpatient(@RequestBody OutpatientDTO outpatientDTO){
        return this.outpatientService.createOutpatient(outpatientDTO);
    }

    @PutMapping("/{id}")
    public OutpatientEntity updateOutpatient(@RequestBody OutpatientDTO outpatientDTO, @PathVariable("id")Long id){
        return this.outpatientService.updateOutpatient(id,outpatientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOutpatient(@PathVariable("id") Long id){
        this.outpatientService.deleteOutpatient(id);
    }
}
