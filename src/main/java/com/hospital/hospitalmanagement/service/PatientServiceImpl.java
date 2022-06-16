package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.entities.GenderEntity;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    GenderServiceImpl genderService;

    @Autowired
    BloodTypeServiceImpl bloodTypeService;

    public List<PatientEntity>getAllPatient(){
        return this.patientRepository.findAll();
    }

    public PatientEntity getById(Long id){
        Optional<PatientEntity> patient = this.patientRepository.findById(id);

        if (patient.isEmpty()){
            return null;
        }

        return patient.get();
    }

    public PatientEntity getPatientById(Long id){
        Optional<PatientEntity> optionalPatient = this.patientRepository.findById(id);

        if (optionalPatient.isEmpty()){
            return null;
        }
        return optionalPatient.get();
    }

    public PatientEntity createPatient(PatientDTO patientDTO){
        BloodTypeEntity existBloodType = this.bloodTypeService.getBloodTypeById(patientDTO.getBlood_type_id());
        GenderEntity existGender = this.genderService.getGenderById(patientDTO.getGender_id());

        PatientEntity patientEntity = PatientEntity.builder()
                .name(patientDTO.getName())
                .dob(LocalDate.parse(patientDTO.getDob()))
                .address(patientDTO.getAddress())
                .city(patientDTO.getCity())
                .phoneNumber(patientDTO.getPhoneNumber())
                .gender(existGender)
                .bloodType(existBloodType)
                .createdAt(LocalDateTime.now())
                .build();
        
        return this.patientRepository.save(patientEntity);
    }

    public PatientEntity updatePatient(Long id, PatientDTO patientDTO){
        PatientEntity existPatient = this.getPatientById(id);
        BloodTypeEntity existBloodType = this.bloodTypeService.getBloodTypeById(patientDTO.getBlood_type_id());
        GenderEntity existGender = this.genderService.getGenderById(patientDTO.getGender_id());

        existPatient.setName(patientDTO.getName());
        existPatient.setDob(LocalDate.parse(patientDTO.getDob()));
        existPatient.setAddress(patientDTO.getAddress());
        existPatient.setCity(patientDTO.getCity());
        existPatient.setPhoneNumber(patientDTO.getPhoneNumber());
        existPatient.setBloodType(existBloodType);
        existPatient.setGender(existGender);

        return this.patientRepository.save(existPatient);
    }

    public void deletePatient(Long id){
        PatientEntity existPatient = this.getPatientById(id);
        this.patientRepository.delete(existPatient);
    }

    public List<PatientEntity> getPatientByName(String name) {
        return this.patientRepository.findByNameContains(name);
    }

    public Page<PatientEntity> getAllPatientPaginate(int index, int element) {
        return this.patientRepository.findAll(PageRequest.of(index, element));
    }

    public void save(PatientEntity patient){
        this.patientRepository.save(patient);
    }

    public void createOutpatient(OutpatientEntity savedOutpatient, Long patient_id) {
        PatientEntity patient = this.getPatientById(patient_id);
        patient.setOutpatient(List.of(savedOutpatient));
    }
}
