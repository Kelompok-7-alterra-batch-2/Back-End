package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.controller.response.*;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.entities.*;
import com.hospital.hospitalmanagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    GenderServiceImpl genderService;

    @Autowired
    BloodTypeServiceImpl bloodTypeService;

    @Autowired
    OutpatientServiceImpl outpatientService;

    public GetPatientTwoDTO convertPatientEntityToResponse(PatientEntity patient){
        GetPatientTwoDTO getPatientDTO = GetPatientTwoDTO.builder()
                .id(patient.getId())
                .dob(patient.getDob())
                .name(patient.getName())
                .bloodType(patient.getBloodType())
                .city(patient.getCity())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .createdAt(patient.getCreatedAt())
                .phoneNumber(patient.getPhoneNumber())
                .build();

        return getPatientDTO;
    }

//    public GetOutpatientTwoDTO convertOutpatientEntityToResponse(OutpatientEntity outpatient, GetDoctorDTO getDoctorDTO){
//        GetOutpatientTwoDTO getOutpatientTwoDTO = GetOutpatientTwoDTO.builder()
//                .queue(outpatient.getQueue())
//                .outpatientCondition(outpatient.getOutpatientCondition())
//                .doctor(getDoctorDTO)
//                .department(outpatient.getDepartment())
//                .createAt(outpatient.getCreatedAt())
//                .date(outpatient.getDate())
//                .id(outpatient.getId())
//                .arrivalTime(outpatient.getArrivalTime())
//                .appointmentReason(outpatient.getAppointmentReason())
//                .diagnosis(outpatient.getDiagnosis())
//                .prescription(outpatient.getPrescription())
//                .build();
//
//        return getOutpatientTwoDTO;
//    }

//    public OutpatientEntity convertOutpatient(GetOutpatientDTO outpatient){
//        OutpatientEntity getOutpatient = OutpatientEntity.builder()
//                .queue(outpatient.getQueue())
//                .outpatientCondition(outpatient.getOutpatientCondition())
//                .department(outpatient.getDepartment())
//                .date(outpatient.getDate())
//                .id(outpatient.getId())
//                .arrivalTime(outpatient.getArrivalTime())
//                .appointmentReason(outpatient.getAppointmentReason())
//                .diagnosis(outpatient.getDiagnosis())
//                .prescription(outpatient.getPrescription())
//                .createdAt(outpatient.getCreateAt())
//                .build();
//
//        return getOutpatient;
//    }

    public List<GetPatientTwoDTO>getAllPatient(){
        List<PatientEntity> all = this.patientRepository.findAll();

        List<GetPatientTwoDTO> patientDTOList = new ArrayList<>();

        for(PatientEntity patient : all){

            GetPatientTwoDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            patientDTOList.add(getPatientDTO);
        }

        return patientDTOList;
    }

    public GetPatientTwoDTO getById(Long id){
        Optional<PatientEntity> patient = this.patientRepository.findById(id);

        if (patient.isEmpty()){
            throw new NotFoundException("Data Not Found");
        }
        PatientEntity data = patient.get();

        return this.convertPatientEntityToResponse(data);
    }

    public PatientEntity getPatientById(Long id){
        Optional<PatientEntity> optionalPatient = this.patientRepository.findById(id);

        if (optionalPatient.isEmpty()){
            throw new NotFoundException("Data Not Found");
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
        return this.patientRepository.findByNameContainsIgnoreCase(name);
    }

    public Page<PatientEntity> getAllPatientPaginate(int index, int element) {
        return this.patientRepository.findAll(PageRequest.of(index, element));
    }
}
