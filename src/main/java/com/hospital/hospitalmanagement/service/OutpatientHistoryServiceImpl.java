package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.OutpatientHistoryEntity;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.OutpatientHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OutpatientHistoryServiceImpl {
    @Autowired
    OutpatientHistoryRepository outpatientHistoryRepository;

    public GetDoctorDTO convertDoctorEntityToResponse(UserEntity doctor) {
        GetDoctorDTO getDoctorDTO = GetDoctorDTO.builder()
                .name(doctor.getName())
                .id(doctor.getId())
                .email(doctor.getEmail())
                .availableTo(doctor.getAvailableTo())
                .availableFrom(doctor.getAvailableFrom())
                .dob(doctor.getDob())
                .role(doctor.getRole())
                .nid(doctor.getNid())
                .phoneNumber(doctor.getPhoneNumber())
                .department(doctor.getDepartment())
                .createdAt(doctor.getCreatedAt())
                .build();

        return getDoctorDTO;
    }

    public GetPatientDTO convertPatientEntityToResponse(PatientEntity patient) {
        GetPatientDTO getPatientDTO = GetPatientDTO.builder()
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

    public GetOutpatientDTO convertOutpatientEntityToResponse(OutpatientHistoryEntity outpatient, GetDoctorDTO getDoctorDTO, GetPatientDTO getPatientDTO) {
        GetOutpatientDTO getOutpatientDTO = GetOutpatientDTO.builder()
                .queue(outpatient.getQueue())
                .outpatientCondition(outpatient.getOutpatientCondition())
                .patient(getPatientDTO)
                .doctor(getDoctorDTO)
                .department(outpatient.getDepartment())
                .createAt(outpatient.getCreatedAt())
                .date(outpatient.getDate())
                .arrivalTime(outpatient.getArrivalTime())
                .appointmentReason(outpatient.getAppointmentReason())
                .diagnosis(outpatient.getDiagnosis())
                .id_today(outpatient.getId_today())
                .prescription(outpatient.getPrescription())
                .build();

        return getOutpatientDTO;

    }

    public List<GetOutpatientDTO> getAllOutpatientHistories() {
        List<OutpatientHistoryEntity> all = this.outpatientHistoryRepository.findAll();

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for (OutpatientHistoryEntity outpatient : all) {
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }
}
