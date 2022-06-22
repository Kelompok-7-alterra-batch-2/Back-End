package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.*;
import com.hospital.hospitalmanagement.repository.OutpatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    ModelMapper modelMapper;

    public List<GetOutpatientDTO>getAllOutpatient(){
        return this.outpatientRepository.findAll()
                .stream()
                .map(this::convertOutpatient)
                .collect(Collectors.toList());
    }

    private GetOutpatientDTO convertOutpatient(OutpatientEntity outpatientEntity){
        GetOutpatientDTO getOutpatientDTO = new GetOutpatientDTO();
        getOutpatientDTO = modelMapper.map(outpatientEntity, GetOutpatientDTO.class);
        return getOutpatientDTO;
    }

    public GetOutpatientDTO getOutpatientById(Long id){
        Optional<GetOutpatientDTO> data = this.outpatientRepository.findById(id)
                .stream()
                .map(this::convertOutpatient)
                .findFirst();

        if (data.isEmpty()){
            return null;
        }

       return data.get();
    }

    public OutpatientEntity getById(Long id){
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

        OutpatientEntity existOutpatient = this.getById(id);
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
        OutpatientEntity existPatient = this.getById(id);
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

    public List<OutpatientEntity> findAllTodayPendingOutpatient(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(1L);

        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByOutpatientConditionAndDate(existCondition, now);
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

    public List<OutpatientEntity> findAllTodayProcessOutpatient(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(2L);

        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByOutpatientConditionAndDate(existCondition, now);
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

    public List<OutpatientEntity> findAllTodayDoneOutpatient(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(3L);

        LocalDate now = LocalDate.now();
        return this.outpatientRepository.findAllByOutpatientConditionAndDate(existCondition, now);
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
