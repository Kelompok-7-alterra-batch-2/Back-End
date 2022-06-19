package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.*;
import com.hospital.hospitalmanagement.repository.OutpatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    int queue;

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
                .patient(existPatient)
                .doctor(existDoctor)
                .dokter(existDoctor.getId())
                .department(existDepartment)
                .outpatientCondition(existOutpatientCondition)
                .queue(++queue)
                .appointmentReason(outpatientDTO.getAppointmentReason())
                .medicalRecord(outpatientDTO.getMedicalRecord())
                .date(outpatientDTO.getDate())
                .arrivalTime(outpatientDTO.getArrivalTime())
                .createdAt(LocalDateTime.now())
                .build();

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(newOutpatient);

        return savedOutpatient;
    }

    public OutpatientEntity updateOutpatient(Long id, OutpatientDTO outpatientDTO){
        PatientEntity existPatient = patientService.getPatientById(outpatientDTO.getPatient_id());

        UserEntity existDoctor = userService.getDoctorById(outpatientDTO.getDoctor_id());

        DepartmentEntity existDepartment = departmentService.
                getDepartmentById(outpatientDTO.getDepartment_id());

        OutpatientConditionEntity existOutpatientCondition = outpatientConditionService
                .getOutpatientById(outpatientDTO.getOutpatientCondition_id());

        OutpatientEntity existOutpatient = this.getOutpatientById(id);
        existOutpatient.setPatient(existPatient);
        existOutpatient.setDoctor(existDoctor);
        existOutpatient.setDokter(existDoctor.getId());
        existOutpatient.setDepartment(existDepartment);
        existOutpatient.setOutpatientCondition(existOutpatientCondition);
        existOutpatient.setQueue(outpatientDTO.getQueue());
        existOutpatient.setDate(outpatientDTO.getDate());
        existOutpatient.setAppointmentReason(outpatientDTO.getAppointmentReason());
        existOutpatient.setMedicalRecord(outpatientDTO.getMedicalRecord());
        existOutpatient.setArrivalTime(outpatientDTO.getArrivalTime());

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(existOutpatient);

        return savedOutpatient;
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

    public List<OutpatientEntity> getAllPendingOutpatient(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(1L);
        return this.outpatientRepository.findAllByOutpatientCondition(existCondition);
    }

    public List<OutpatientEntity> findAllTodayPendingOutpatient(Long doctorId){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(1L);
        UserEntity existDoctor = this.userService.getDoctorById(doctorId);
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctor(existCondition, now, existDoctor);
    }

    public OutpatientEntity processOutpatient(Long outpatient_id){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(2L);

        OutpatientEntity existOutpatient = this.getById(outpatient_id);
        existOutpatient.setOutpatientCondition(existCondition);

        return this.outpatientRepository.save(existOutpatient);
    }

    public List<OutpatientEntity> getAllProcessOutpatient(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(2L);
        return this.outpatientRepository.findAllByOutpatientCondition(existCondition);
    }

    public List<OutpatientEntity> findAllTodayProcessOutpatient(Long doctorId){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(2L);
        UserEntity existDoctor = this.userService.getDoctorById(doctorId);
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctor(existCondition, now, existDoctor);
    }

    public OutpatientEntity doneOutpatient(Long outpatient_id){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(3L);

        OutpatientEntity existOutpatient = this.getById(outpatient_id);
        existOutpatient.setOutpatientCondition(existCondition);

        return this.outpatientRepository.save(existOutpatient);
    }

    public List<OutpatientEntity> getAllDoneOutpatient(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(3L);
        return this.outpatientRepository.findAllByOutpatientCondition(existCondition);
    }

    public List<OutpatientEntity> findAllTodayDoneOutpatient(Long doctorId){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(3L);
        UserEntity existDoctor = this.userService.getDoctorById(doctorId);
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctor(existCondition, now, existDoctor);
    }

    public List<OutpatientEntity> getAllTodayOutpatientByDepartment(Long department_id){
        DepartmentEntity existDepartment = this.departmentService.getDepartmentById(department_id);
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByDepartmentAndDate(existDepartment, now);
    }

    public OutpatientEntity diagnosisOutpatient(Long outpatient_id, DiagnosisDTO diagnosisDTO) {
        OutpatientEntity existOutpatient = this.getById(outpatient_id);
        existOutpatient.setDiagnosis(diagnosisDTO.getDiagnosis());
        existOutpatient.setPrescription(diagnosisDTO.getPrescription());

        return this.outpatientRepository.save(existOutpatient);
    }

    public List<OutpatientEntity> getAllOutpatientByDoctor(Long doctor_id) {
        UserEntity existDoctor = this.userService.getDoctorById(doctor_id);
        return this.outpatientRepository.findAllByDoctorOrderByQueueAsc(existDoctor);
    }

    public List<OutpatientEntity> getAllTodayOutpatientByDoctor(Long doctor_id){
        UserEntity existDoctor = this.userService.getDoctorById(doctor_id);
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByDoctorAndDateOrderByQueueAsc(existDoctor, now);
    }
}
