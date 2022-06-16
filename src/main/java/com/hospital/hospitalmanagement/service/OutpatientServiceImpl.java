package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.entities.*;
import com.hospital.hospitalmanagement.repository.OutpatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OutpatientServiceImpl {
    @Autowired
    OutpatientRepository outpatientRepository;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    DepartmentServiceImpl departmentService;

    @Autowired
    OutpatientConditionServiceImpl outpatientConditionService;

    public List<OutpatientEntity>getAllOutpatient(){
        return this.outpatientRepository.findAll();
    }

    public OutpatientEntity getById(Long id){
        Optional<OutpatientEntity> data = this.outpatientRepository.findById(id);

        if (data.isEmpty()){
            return null;
        }

       return data.get();
    }

    public OutpatientEntity getOutpatientById(Long id){
        Optional<OutpatientEntity> optionalOutpatient = this.outpatientRepository.findById(id);

        if (optionalOutpatient.isEmpty()){
            return null;
        }
        return optionalOutpatient.get();
    }

    public OutpatientEntity createOutpatient(OutpatientDTO outpatientDTO){
        PatientEntity existPatient = patientService.getPatientById(outpatientDTO.getPatient_id());

        UserEntity existDoctor = this.userService.getUserById(outpatientDTO.getDoctor_id());

        DepartmentEntity existDepartment = departmentService.getDepartmentById(outpatientDTO.getDepartment_id());

        OutpatientConditionEntity existOutpatientCondition = outpatientConditionService.getOutpatientById(1L);

        OutpatientEntity newOutpatient = OutpatientEntity.builder()
                .name(outpatientDTO.getName())
                .patient(existPatient)
                .doctor(existDoctor)
                .department(existDepartment)
                .outpatientCondition(existOutpatientCondition)
                .queue(outpatientDTO.getQueue())
                .date(LocalDate.now())
                .arrivalTime(LocalTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(newOutpatient);

        this.userService.creatOutpatient(savedOutpatient, outpatientDTO.getDoctor_id());
        this.patientService.createOutpatient(savedOutpatient, outpatientDTO.getPatient_id());

        return this.outpatientRepository.save(savedOutpatient);
    }

    public OutpatientEntity updateOutpatient(Long id, OutpatientDTO outpatientDTO){
        PatientEntity existPatient = patientService.getPatientById(outpatientDTO.getPatient_id());

        UserEntity existDoctor = userService.getDoctorById(outpatientDTO.getDoctor_id());

        DepartmentEntity existDepartment = departmentService.
                getDepartmentById(outpatientDTO.getDepartment_id());

        OutpatientConditionEntity existOutpatientCondition = outpatientConditionService
                .getOutpatientById(outpatientDTO.getOutpatientCondition_id());

        OutpatientEntity existOutpatient = this.getOutpatientById(id);
        existOutpatient.setName(outpatientDTO.getName());
        existOutpatient.setPatient(existPatient);
        existOutpatient.setDoctor(existDoctor);
        existOutpatient.setDepartment(existDepartment);
        existOutpatient.setOutpatientCondition(existOutpatientCondition);
        existOutpatient.setQueue(outpatientDTO.getQueue());
        existOutpatient.setDate(LocalDate.now());
        existOutpatient.setArrivalTime(LocalTime.now());

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(existOutpatient);

        existPatient.setOutpatient(List.of(savedOutpatient));
        existDoctor.setOutpatient(List.of(savedOutpatient));

        this.userService.save(existDoctor);
        this.patientService.save(existPatient);

        return this.outpatientRepository.save(savedOutpatient);
    }

    public void deleteOutpatient(Long id){
        OutpatientEntity existPatient = this.getOutpatientById(id);
        this.outpatientRepository.delete(existPatient);
    }

    public List<OutpatientEntity> findAllTodayOutpatient() {
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByDate(now);
    }

    public Long countTodayOutpatient() {
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.countByDate(now);
    }

    public List<UserEntity> getAllAvailableDoctor(LocalTime arrivalTime, Long department_id) {
        return this.userService.findAllAvailableDoctor(arrivalTime, department_id);
    }
}
