package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetOutpatientDTO;
import com.hospital.hospitalmanagement.controller.response.GetPatientDTO;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.*;
import com.hospital.hospitalmanagement.repository.OutpatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public GetDoctorDTO convertDoctorEntityToResponse(UserEntity doctor){
        GetDoctorDTO getDoctorDTO = GetDoctorDTO.builder()
                .name(doctor.getName())
                .id(doctor.getId())
                .email(doctor.getEmail())
                .dob(doctor.getDob())
                .role(doctor.getRole())
                .nid(doctor.getNid())
                .phoneNumber(doctor.getPhoneNumber())
                .department(doctor.getDepartment())
                .createdAt(doctor.getCreatedAt())
                .build();

        return getDoctorDTO;
    }

    public GetPatientDTO convertPatientEntityToResponse(PatientEntity patient){
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

    public GetOutpatientDTO convertOutpatientEntityToResponse(OutpatientEntity outpatient, GetDoctorDTO getDoctorDTO, GetPatientDTO getPatientDTO){
        GetOutpatientDTO getOutpatientDTO = GetOutpatientDTO.builder()
                .queue(outpatient.getQueue())
                .outpatientCondition(outpatient.getOutpatientCondition())
                .patient(getPatientDTO)
                .doctor(getDoctorDTO)
                .department(outpatient.getDepartment())
                .createAt(outpatient.getCreatedAt())
                .date(outpatient.getDate())
                .id(outpatient.getId())
                .arrivalTime(outpatient.getArrivalTime())
                .appointmentReason(outpatient.getAppointmentReason())
                .diagnosis(outpatient.getDiagnosis())
                .prescription(outpatient.getPrescription())
                .build();

        return getOutpatientDTO;
    }

    public List<GetOutpatientDTO> getAllOutpatient(){
        List<OutpatientEntity> all = this.outpatientRepository.findAll();

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : all){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public GetOutpatientDTO getOutpatientById(Long id){
        Optional<OutpatientEntity> data = this.outpatientRepository.findById(id);

        if (data.isEmpty()){
            throw new NotFoundException("Data Not Found");
        }

        OutpatientEntity existOutpatient = data.get();
        UserEntity existDoctor = data.get().getDoctor();
        PatientEntity existPatient = data.get().getPatient();

        GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(existDoctor);
        GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(existPatient);

        return this.convertOutpatientEntityToResponse(existOutpatient, getDoctorDTO, getPatientDTO);
    }

    public OutpatientEntity getById(Long id){
        Optional<OutpatientEntity> optionalOutpatient = this.outpatientRepository.findById(id);

        if (optionalOutpatient.isEmpty()){
            throw new NotFoundException("Data Not Found");
        }
        return optionalOutpatient.get();
    }

    public GetOutpatientDTO createOutpatient(OutpatientDTO outpatientDTO){
        PatientEntity existPatient = patientService.getPatientById(outpatientDTO.getPatient_id());

        UserEntity existDoctor = this.userService.getUserById(outpatientDTO.getDoctor_id());

        DepartmentEntity existDepartment = departmentService.getDepartmentById(outpatientDTO.getDepartment_id());

        OutpatientConditionEntity existOutpatientCondition = outpatientConditionService.getOutpatientById(1L);

        OutpatientEntity newOutpatient = OutpatientEntity.builder()
                .patient(existPatient)
                .doctor(existDoctor)
                .department(existDepartment)
                .outpatientCondition(existOutpatientCondition)
                .appointmentReason(outpatientDTO.getAppointmentReason())
                .date(outpatientDTO.getDate())
                .arrivalTime(outpatientDTO.getArrivalTime())
                .createdAt(LocalDateTime.now())
                .build();

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(newOutpatient);
        savedOutpatient.setQueue(Math.toIntExact(savedOutpatient.getId()));
        OutpatientEntity updatedOutpatient = this.outpatientRepository.save(savedOutpatient);

        GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(savedOutpatient.getDoctor());
        GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(savedOutpatient.getPatient());

        return this.convertOutpatientEntityToResponse(updatedOutpatient, getDoctorDTO, getPatientDTO);
    }

    public GetOutpatientDTO updateOutpatient(Long id, OutpatientDTO outpatientDTO){
        PatientEntity existPatient = patientService.getPatientById(outpatientDTO.getPatient_id());

        UserEntity existDoctor = userService.getDoctorById(outpatientDTO.getDoctor_id());

        DepartmentEntity existDepartment = departmentService.
                getDepartmentById(outpatientDTO.getDepartment_id());

        OutpatientConditionEntity existOutpatientCondition = outpatientConditionService
                .getOutpatientById(outpatientDTO.getOutpatientCondition_id());

        OutpatientEntity existOutpatient = this.getById(id);
        existOutpatient.setPatient(existPatient);
        existOutpatient.setDoctor(existDoctor);
        existOutpatient.setDepartment(existDepartment);
        existOutpatient.setOutpatientCondition(existOutpatientCondition);
        existOutpatient.setQueue(outpatientDTO.getQueue());
        existOutpatient.setDate(outpatientDTO.getDate());
        existOutpatient.setAppointmentReason(outpatientDTO.getAppointmentReason());
        existOutpatient.setArrivalTime(outpatientDTO.getArrivalTime());

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(existOutpatient);
        GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(savedOutpatient.getDoctor());
        GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(savedOutpatient.getPatient());

        return this.convertOutpatientEntityToResponse(savedOutpatient, getDoctorDTO, getPatientDTO);
    }

    public void deleteOutpatient(Long id){
        OutpatientEntity existPatient = this.getById(id);
        this.outpatientRepository.delete(existPatient);
    }

    public List<GetOutpatientDTO> findAllTodayOutpatient() {
        LocalDate now = LocalDate.now();
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByDate(now);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public Long countTodayOutpatient() {
        LocalDate now = LocalDate.now();
        return this.outpatientRepository.countByDate(now);
    }

//    public List<UserEntity> getAllAvailableDoctor(String arrivalTime, Long department_id) {
//        return this.userService.findAllAvailableDoctor(LocalTime.parse(arrivalTime), department_id);
//    }

    public List<GetOutpatientDTO> getAllPendingOutpatientToday(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(1L);
        LocalDate now = LocalDate.now();
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByOutpatientConditionAndDateOrderByQueueAsc(existCondition, now);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public List<GetOutpatientDTO> findAllTodayPendingOutpatientByDoctor(Long doctorId){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(1L);

        LocalDate now = LocalDate.now();
        UserEntity existDoctor = this.userService.getDoctorById(doctorId);
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctorOrderByQueueAsc(existCondition, now, existDoctor);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public GetOutpatientDTO processOutpatient(Long outpatient_id){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(2L);

        OutpatientEntity existOutpatient = this.getById(outpatient_id);
        existOutpatient.setOutpatientCondition(existCondition);

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(existOutpatient);
        GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(savedOutpatient.getDoctor());
        GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(savedOutpatient.getPatient());

        return this.convertOutpatientEntityToResponse(savedOutpatient, getDoctorDTO, getPatientDTO);
    }

    public List<GetOutpatientDTO> getAllProcessOutpatientToday(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(2L);
        LocalDate now = LocalDate.now();
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByOutpatientConditionAndDateOrderByQueueAsc(existCondition, now);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public List<GetOutpatientDTO> findAllTodayProcessOutpatientByDoctor(Long doctorId){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(2L);

        LocalDate now = LocalDate.now();
        UserEntity existDoctor = this.userService.getDoctorById(doctorId);
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctorOrderByQueueAsc(existCondition, now, existDoctor);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public GetOutpatientDTO doneOutpatient(Long outpatient_id){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(3L);

        OutpatientEntity existOutpatient = this.getById(outpatient_id);
        existOutpatient.setOutpatientCondition(existCondition);

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(existOutpatient);
        GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(savedOutpatient.getDoctor());
        GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(savedOutpatient.getPatient());

        return this.convertOutpatientEntityToResponse(savedOutpatient, getDoctorDTO, getPatientDTO);
    }

    public List<GetOutpatientDTO> getAllDoneOutpatientToday(){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(3L);
        LocalDate now = LocalDate.now();
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByOutpatientConditionAndDateOrderByQueueAsc(existCondition, now);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public List<GetOutpatientDTO> findAllTodayDoneOutpatientByDoctor(Long doctorId){
        OutpatientConditionEntity existCondition = this.outpatientConditionService.getOutpatientById(3L);

        LocalDate now = LocalDate.now();
        UserEntity existDoctor = this.userService.getDoctorById(doctorId);
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByOutpatientConditionAndDateAndDoctorOrderByQueueAsc(existCondition, now, existDoctor);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public List<GetOutpatientDTO> getAllTodayOutpatientByDepartment(Long department_id){
        DepartmentEntity existDepartment = this.departmentService.getDepartmentById(department_id);
        LocalDate now = LocalDate.now();
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByDepartmentAndDate(existDepartment, now);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public GetOutpatientDTO diagnosisOutpatient(Long outpatient_id, DiagnosisDTO diagnosisDTO) {
        OutpatientEntity existOutpatient = this.getById(outpatient_id);
        existOutpatient.setDiagnosis(diagnosisDTO.getDiagnosis());
        existOutpatient.setPrescription(diagnosisDTO.getPrescription());

        OutpatientEntity savedOutpatient = this.outpatientRepository.save(existOutpatient);
        GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(savedOutpatient.getDoctor());
        GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(savedOutpatient.getPatient());

        return this.convertOutpatientEntityToResponse(savedOutpatient, getDoctorDTO, getPatientDTO);
    }

    public List<GetOutpatientDTO> getAllOutpatientByDoctor(Long doctor_id) {
        UserEntity existDoctor = this.userService.getDoctorById(doctor_id);
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByDoctorOrderByQueueAsc(existDoctor);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public List<GetOutpatientDTO> getAllTodayOutpatientByDoctor(Long doctor_id){
        UserEntity existDoctor = this.userService.getDoctorById(doctor_id);
        LocalDate now = LocalDate.now();
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByDoctorAndDateOrderByQueueAsc(existDoctor, now);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    @Transactional
    public void truncateOutpatientTable(){
        this.outpatientRepository.truncateMyTable();
    }

    public List<GetOutpatientDTO> getAllOutpatientByPatientName(String name){
        LocalDate now = LocalDate.now();
        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByPatientNameContainsIgnoreCaseAndDate(name, now);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public List<GetOutpatientDTO> getAllOutpatientByPatientIdToday(Long patientId) {
        PatientEntity existPatient = this.patientService.getPatientById(patientId);
        LocalDate now = LocalDate.now();

        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByPatientAndDate(existPatient, now);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public List<GetOutpatientDTO> getAllOutpatientByPatientId(Long patientId) {
        PatientEntity existPatient = this.patientService.getPatientById(patientId);

        List<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAllByPatient(existPatient);

        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();

        for(OutpatientEntity outpatient : existOutpatientList){
            UserEntity doctor = outpatient.getDoctor();
            PatientEntity patient = outpatient.getPatient();

            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);

            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);

            outpatientDTOList.add(getOutpatientDTO);
        }

        return outpatientDTOList;
    }

    public Page<OutpatientEntity> getAllOutpatientByPaginate(int index, int element) {

        Page<OutpatientEntity> existOutpatientList = this.outpatientRepository.findAll(PageRequest.of(index, element));

//        List<GetOutpatientDTO> outpatientDTOList = new ArrayList<>();
//
//        for(OutpatientEntity outpatient : existOutpatientList){
//            UserEntity doctor = outpatient.getDoctor();
//            PatientEntity patient = outpatient.getPatient();
//
//            GetDoctorDTO getDoctorDTO = this.convertDoctorEntityToResponse(doctor);
//            GetPatientDTO getPatientDTO = this.convertPatientEntityToResponse(patient);
//
//            GetOutpatientDTO getOutpatientDTO = this.convertOutpatientEntityToResponse(outpatient, getDoctorDTO, getPatientDTO);
//
//            outpatientDTOList.add(getOutpatientDTO);
//        }

        return  existOutpatientList;
    }
}
