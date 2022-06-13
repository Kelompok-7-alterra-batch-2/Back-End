package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl {
    @Autowired
    PatientRepository patientRepository;

    public List<GetPatientDTO>getAllPatient(){
        List<GetPatientDTO>patients = new ArrayList<>();

        List<PatientEntity>patient = this.patientRepository.findAll();
        for (PatientEntity data : patient){
            GetPatientDTO obj = new GetPatientDTO();

            obj.setId(data.getId());
            obj.setName(data.getName());
            obj.setMedicalRecord(data.getMedicalRecord());
            obj.setDob(data.getDob());

            patients.add(obj);
        }
        return patients;
    }

    public GetPatientDTO getById(Long id){
        Optional<PatientEntity> patient = this.patientRepository.findById(id);

        GetPatientDTO obj = new GetPatientDTO();

        if (patient.isEmpty()){
            return null;
        }

        obj.setId(patient.get().getId());
        obj.setName(patient.get().getName());
        obj.setMedicalRecord(patient.get().getMedicalRecord());
        obj.setDob(patient.get().getDob());

        return obj;
    }

    public PatientEntity getPatientById(Long id){
        Optional<PatientEntity> optionalPatient = this.patientRepository.findById(id);

        if (optionalPatient.isEmpty()){
            return null;
        }
        return optionalPatient.get();
    }

    public PatientEntity createPatient(PatientDTO patientDTO){

        PatientEntity patientEntity = PatientEntity.builder()
                .name(patientDTO.getName())
                .medicalRecord(patientDTO.getMedicalRecord())
                .dob(LocalDate.parse(patientDTO.getDob()))
                .createdAt(LocalDateTime.now())
                .build();
        return this.patientRepository.save(patientEntity);
    }

    public PatientEntity updatePatient(Long id, PatientDTO patientDTO){
        PatientEntity existPatient = this.getPatientById(id);

        existPatient.setName(patientDTO.getName());
        existPatient.setMedicalRecord(patientDTO.getMedicalRecord());
        existPatient.setDob(LocalDate.parse(patientDTO.getDob()));

        return this.patientRepository.save(existPatient);
    }

    public void deletePatient(Long id){
        PatientEntity existPatient = this.getPatientById(id);
        this.patientRepository.delete(existPatient);
    }
}
