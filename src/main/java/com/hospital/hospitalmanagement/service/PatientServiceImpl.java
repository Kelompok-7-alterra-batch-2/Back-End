package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl {
    @Autowired
    PatientRepository patientRepository;

    public List<PatientEntity>getAllPatient(){
        return this.patientRepository.findAll();
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
                .dob(LocalDate.parse(patientDTO.getDob()))
                .createdAt(LocalDateTime.now())
                .build();
        return this.patientRepository.save(patientEntity);
    }

    public PatientEntity updatePatient(Long id, PatientDTO patientDTO){
        PatientEntity existPatient = this.getPatientById(id);

        existPatient.setName(patientDTO.getName());
        existPatient.setDob(LocalDate.parse(patientDTO.getDob()));

        return this.patientRepository.save(existPatient);
    }

    public void deletePatient(Long id){
        PatientEntity existPatient = this.getPatientById(id);
        this.patientRepository.delete(existPatient);
    }
}
