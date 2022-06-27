package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.PatientDTO;
import com.hospital.hospitalmanagement.controller.response.*;
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

    public GetPatientTwoDTO convertPatientEntityToResponse(PatientEntity patient, List<GetOutpatientTwoDTO> getOutpatientTwoDTO){
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
                .outpatient(getOutpatientTwoDTO)
                .build();

        return getPatientDTO;
    }

    public GetOutpatientTwoDTO convertOutpatientEntityToResponse(OutpatientEntity outpatient, GetDoctorDTO getDoctorDTO){
        GetOutpatientTwoDTO getOutpatientTwoDTO = GetOutpatientTwoDTO.builder()
                .queue(outpatient.getQueue())
                .outpatientCondition(outpatient.getOutpatientCondition())
                .doctor(getDoctorDTO)
                .department(outpatient.getDepartment())
                .createAt(outpatient.getCreatedAt())
                .date(outpatient.getDate())
                .id(outpatient.getId())
                .arrivalTime(outpatient.getArrivalTime())
                .appointmentReason(outpatient.getAppointmentReason())
                .medicalRecord(outpatient.getMedicalRecord())
                .diagnosis(outpatient.getDiagnosis())
                .prescription(outpatient.getPrescription())
                .build();

        return getOutpatientTwoDTO;
    }

    public OutpatientEntity convertOutpatient(GetOutpatientDTO outpatient){
        OutpatientEntity getOutpatient = OutpatientEntity.builder()
                .queue(outpatient.getQueue())
                .outpatientCondition(outpatient.getOutpatientCondition())
                .department(outpatient.getDepartment())
                .date(outpatient.getDate())
                .id(outpatient.getId())
                .arrivalTime(outpatient.getArrivalTime())
                .appointmentReason(outpatient.getAppointmentReason())
                .medicalRecord(outpatient.getMedicalRecord())
                .diagnosis(outpatient.getDiagnosis())
                .prescription(outpatient.getPrescription())
                .build();

        return getOutpatient;
    }

    public List<GetPatientTwoDTO>getAllPatient(){
        List<PatientEntity> all = this.patientRepository.findAll();

        List<GetPatientTwoDTO> patientDTOList = new ArrayList<>();

        for(PatientEntity patient : all){

            List<GetOutpatientDTO> get = this.outpatientService.getAllOutpatient();

            List<GetOutpatientTwoDTO> outpatientNewDTOList = new ArrayList<>();

            for (GetOutpatientDTO outpatient : get){
                GetDoctorDTO doctor = outpatient.getDoctor();

                OutpatientEntity convert = this.convertOutpatient(outpatient);

                GetOutpatientTwoDTO getOutpatientTwoDTO = this.convertOutpatientEntityToResponse(convert,doctor);

                outpatientNewDTOList.add(getOutpatientTwoDTO);
            }

            GetPatientTwoDTO getPatientDTO = this.convertPatientEntityToResponse(patient,outpatientNewDTOList);

            patientDTOList.add(getPatientDTO);
        }

        return patientDTOList;
    }

    public GetPatientTwoDTO getById(Long id){
        Optional<PatientEntity> patient = this.patientRepository.findById(id);

        if (patient.isEmpty()){
            return null;
        }
        PatientEntity data = patient.get();

        List<GetOutpatientDTO> get = this.outpatientService.getAllOutpatient();

        List<GetOutpatientTwoDTO> outpatientNewDTOList = new ArrayList<>();

        for (GetOutpatientDTO outpatient : get){
            GetDoctorDTO doctor = outpatient.getDoctor();

            OutpatientEntity convert = this.convertOutpatient(outpatient);

            GetOutpatientTwoDTO getOutpatientTwoDTO = this.convertOutpatientEntityToResponse(convert,doctor);

            outpatientNewDTOList.add(getOutpatientTwoDTO);
        }

        return this.convertPatientEntityToResponse(data,outpatientNewDTOList);
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
