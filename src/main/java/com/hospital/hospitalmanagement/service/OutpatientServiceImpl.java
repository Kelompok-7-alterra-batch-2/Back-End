package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
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

    @Autowired
    QueueServiceImpl queueService;


    public List<GetOutpatientDTO>getAllOutpatient(){
        List<GetOutpatientDTO> outpatients = new ArrayList<>();

        List<OutpatientEntity> outpatient = outpatientRepository.findAll();

        for (OutpatientEntity data : outpatient){

            GetOutpatientDTO obj = new GetOutpatientDTO();

            GetPatientDTO patient = new GetPatientDTO();
            GetDoctorDTO doctor = new GetDoctorDTO();
            DepartmentEntity department = new DepartmentEntity();
            OutpatientConditionEntity outpatientCondition = new OutpatientConditionEntity();
            QueueEntity queue = new QueueEntity();

            patient.setId(data.getPatient().getId());
            patient.setName(data.getPatient().getName());
            patient.setMedicalRecord(data.getPatient().getMedicalRecord());
            patient.setDob(data.getPatient().getDob());

            doctor.setId(data.getDoctor().getId());
            doctor.setName(data.getDoctor().getName());
            doctor.setEmail(data.getDoctor().getEmail());
            doctor.setDob(data.getDoctor().getDob().toString());
            doctor.setAvailableFrom(data.getDoctor().getAvailableFrom());
            doctor.setAvailableTo(data.getDoctor().getAvailableTo());

            department.setId(data.getDepartment().getId());
            department.setName(data.getDepartment().getName());

            outpatientCondition.setId(data.getOutpatientCondition().getId());
            outpatientCondition.setConditions(data.getOutpatientCondition().getConditions());

            obj.setId(data.getId());
            obj.setName(data.getName());
            obj.setDate(data.getDate());
            obj.setArrivalTime(data.getArrivalTime());
            obj.setCreateAt(data.getCreatedAt());

            obj.setPatient(patient);
            obj.setDoctor(doctor);
            obj.setDepartment(department);
            obj.setOutpatientCondition(outpatientCondition);
            obj.setQueue(data.getQueue());


            outpatients.add(obj);
        }

        return outpatients;
    }

    public GetOutpatientDTO getById(Long id){
        Optional<OutpatientEntity> data = this.outpatientRepository.findById(id);

        if (data.isEmpty()){
            return null;
        }

        GetOutpatientDTO obj = new GetOutpatientDTO();

        GetPatientDTO patient = new GetPatientDTO();
        GetDoctorDTO doctor = new GetDoctorDTO();
        DepartmentEntity department = new DepartmentEntity();
        OutpatientConditionEntity outpatientCondition = new OutpatientConditionEntity();
        QueueEntity queue = new QueueEntity();

        patient.setId(data.get().getPatient().getId());
        patient.setName(data.get().getPatient().getName());
        patient.setMedicalRecord(data.get().getPatient().getMedicalRecord());
        patient.setDob(data.get().getPatient().getDob());

        doctor.setId(data.get().getDoctor().getId());
        doctor.setName(data.get().getDoctor().getName());
        doctor.setEmail(data.get().getDoctor().getEmail());
        doctor.setDob(data.get().getDoctor().getDob().toString());
        doctor.setAvailableFrom(data.get().getDoctor().getAvailableFrom());
        doctor.setAvailableTo(data.get().getDoctor().getAvailableTo());

        department.setId(data.get().getDepartment().getId());
        department.setName(data.get().getDepartment().getName());

        outpatientCondition.setId(data.get().getOutpatientCondition().getId());
        outpatientCondition.setConditions(data.get().getOutpatientCondition().getConditions());

        obj.setId(data.get().getId());
        obj.setName(data.get().getName());
        obj.setDate(data.get().getDate());
        obj.setArrivalTime(data.get().getArrivalTime());
        obj.setCreateAt(data.get().getCreatedAt());

        obj.setPatient(patient);
        obj.setDoctor(doctor);
        obj.setDepartment(department);
        obj.setOutpatientCondition(outpatientCondition);
        obj.setQueue(data.get().getQueue());

        return obj;
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

        UserEntity existDoctor = userService.getDoctorById(outpatientDTO.getDoctor_id());

        DepartmentEntity existDepartment = departmentService.
                getDepartmentById(outpatientDTO.getDepartment_id());

        OutpatientConditionEntity existOutpatientCondition = outpatientConditionService
                .getOutpatientById(1L);

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

        return this.outpatientRepository.save(newOutpatient);
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

        return this.outpatientRepository.save(existOutpatient);
    }

    public void deleteOutpatient(Long id){
        OutpatientEntity existPatient = this.getOutpatientById(id);
        this.outpatientRepository.delete(existPatient);
    }

    public List<OutpatientEntity> findAllTodayOutpatient() {
    }
}
