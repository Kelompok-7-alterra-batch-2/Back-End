package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.AdminDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorScheduleDTO;
import com.hospital.hospitalmanagement.controller.response.*;
import com.hospital.hospitalmanagement.entities.*;
import com.hospital.hospitalmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentServiceImpl departmentService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    private OutpatientServiceImpl outpatientService;

    public List<UserEntity> getAllUser(){
        return this.userRepository.findAll();
    }

    public List<UserEntity> getAllAdmin(){
        RoleEntity role = this.roleService.getRoleById(1L);

        return this.userRepository.findAllByRole(role);
    }

    public UserEntity getUserById(Long id){
        return this.userRepository.findById(id).get();
    }

    public UserEntity getAdminById(Long id){
        RoleEntity existRole = this.roleService.getRoleById(1L);

        return this.userRepository.findByIdAndRole(id, existRole);
    }

    public UserEntity getAdminByEmail(String email){
        RoleEntity existRole = this.roleService.getRoleById(1L);

        return this.userRepository.findByEmailAndRole(email, existRole);
    }

    public UserEntity createAdmin(AdminDTO adminDTO) {
        RoleEntity existRole = this.roleService.getRoleById(1L);

        LocalDate dob = LocalDate.parse(adminDTO.getDob());

        UserEntity newAdmin = UserEntity.builder()
                .name(adminDTO.getName())
                .dob(dob)
                .email(adminDTO.getEmail())
                .password(adminDTO.getPassword())
                .phoneNumber(adminDTO.getPhoneNumber())
                .role(existRole)
                .createdAt(LocalDateTime.now())
                .build();

        return this.userRepository.save(newAdmin);
    }

    public UserEntity updateAdmin(Long id, AdminDTO adminDTO) {
        UserEntity existAdmin = this.getAdminById(id);

        LocalDate dob = LocalDate.parse(adminDTO.getDob());

        existAdmin.setName(adminDTO.getName());
        existAdmin.setDob(dob);
        existAdmin.setEmail(adminDTO.getEmail());
        existAdmin.setPassword(adminDTO.getPassword());
        existAdmin.setPhoneNumber(adminDTO.getPhoneNumber());

        return this.userRepository.save(existAdmin);
    }

    public void deleteAdmin(Long id){
        UserEntity existAdmin = this.getAdminById(id);
        this.userRepository.delete(existAdmin);
    }

    public GetDoctorTwoDTO convertDoctorEntityToResponse(UserEntity doctor,List<GetOutpatientThreeDTO> outpatientThreeDTOS){
        GetDoctorTwoDTO getDoctorTwoDTO = GetDoctorTwoDTO.builder()
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
                .outpatient(outpatientThreeDTOS)
                .createdAt(doctor.getCreatedAt())
                .build();

        return getDoctorTwoDTO;
    }

    public GetOutpatientThreeDTO convertOutpatientEntityToResponse(OutpatientEntity outpatient, GetPatientDTO getPatientDTO){
        GetOutpatientThreeDTO getOutpatientThreeDTO = GetOutpatientThreeDTO.builder()
                .queue(outpatient.getQueue())
                .outpatientCondition(outpatient.getOutpatientCondition())
                .patient(getPatientDTO)
                .department(outpatient.getDepartment())
                .createAt(outpatient.getCreatedAt())
                .date(outpatient.getDate())
                .id(outpatient.getId())
                .arrivalTime(outpatient.getArrivalTime())
                .appointmentReason(outpatient.getAppointmentReason())
                .diagnosis(outpatient.getDiagnosis())
                .prescription(outpatient.getPrescription())
                .build();

        return getOutpatientThreeDTO;
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
                .diagnosis(outpatient.getDiagnosis())
                .prescription(outpatient.getPrescription())
                .build();

        return getOutpatient;
    }

    public List<GetDoctorTwoDTO> getAllDoctor(){
        RoleEntity role = this.roleService.getRoleById(2L);
        List<UserEntity> all = this.userRepository.findAllByRole(role);

        List<GetDoctorTwoDTO> getDoctorDTOList = new ArrayList<>();

        for(UserEntity doctor : all){

            List<GetOutpatientDTO> get = this.outpatientService.getAllOutpatient();

            List<GetOutpatientThreeDTO> outpatientThreeDTOList = new ArrayList<>();

            for (GetOutpatientDTO outpatient : get){
                GetPatientDTO patient = outpatient.getPatient();

                OutpatientEntity convert = this.convertOutpatient(outpatient);

                GetOutpatientThreeDTO getOutpatientThreeDTO = this.convertOutpatientEntityToResponse(convert,patient);

                outpatientThreeDTOList.add(getOutpatientThreeDTO);
            }

            GetDoctorTwoDTO getDoctorTwoDTO = this.convertDoctorEntityToResponse(doctor,outpatientThreeDTOList);

            getDoctorDTOList.add(getDoctorTwoDTO);
        }

        return getDoctorDTOList;
    }

    public GetDoctorTwoDTO getById(Long id){
        RoleEntity existRole = this.roleService.getRoleById(2L);
        Optional<UserEntity> doctor = Optional.ofNullable(this.userRepository.findByIdAndRole(id, existRole));

        UserEntity existDoctor = doctor.get();

        List<GetOutpatientDTO> get = this.outpatientService.getAllOutpatient();

        List<GetOutpatientThreeDTO> outpatientThreeDTOList = new ArrayList<>();

        for (GetOutpatientDTO outpatient : get){
            GetPatientDTO patient = outpatient.getPatient();

            OutpatientEntity convert = this.convertOutpatient(outpatient);

            GetOutpatientThreeDTO getOutpatientThreeDTO = this.convertOutpatientEntityToResponse(convert,patient);

            outpatientThreeDTOList.add(getOutpatientThreeDTO);
        }

        return convertDoctorEntityToResponse(existDoctor,outpatientThreeDTOList);
    }

    public UserEntity getDoctorById(Long id){
        RoleEntity existRole = this.roleService.getRoleById(2L);

        return this.userRepository.findByIdAndRole(id, existRole);
    }

    public List<UserEntity> getDoctorByDepartment(Long departmentId){
        DepartmentEntity existDepartment = this.departmentService.getDepartmentById(departmentId);

        return this.userRepository.findAllByDepartment(existDepartment);
    }
  
    public List<UserEntity> getDoctorByName(String name){
        RoleEntity existRole = this.roleService.getRoleById(2L);

        return this.userRepository.findByNameContainsAndRole(name, existRole);
    }


    public UserEntity createDoctor(DoctorDTO doctorDTO) {
        LocalDate dob = LocalDate.parse(doctorDTO.getDob());
        DepartmentEntity existDepartment = departmentService.getDepartmentById(doctorDTO.getDepartment_id());
        RoleEntity role = roleService.getRoleById(2L);


        UserEntity newDoctor = UserEntity.builder()
                .name(doctorDTO.getName())
                .dob(dob)
                .email(doctorDTO.getEmail())
                .password(doctorDTO.getPassword())
                .role(role)
                .department(existDepartment)
                .nid(doctorDTO.getNid())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();

        return this.userRepository.save(newDoctor);
    }


    public UserEntity updateDoctor(Long id, DoctorDTO doctorDTO) {
        LocalDate dob = LocalDate.parse(doctorDTO.getDob());
        DepartmentEntity existDepartment = departmentService.getDepartmentById(doctorDTO.getDepartment_id());

        UserEntity existDoctor = this.getDoctorById(id);
        existDoctor.setName(doctorDTO.getName());
        existDoctor.setDob(dob);
        existDoctor.setEmail(doctorDTO.getEmail());
        existDoctor.setPassword(doctorDTO.getPassword());
        existDoctor.setDepartment(existDepartment);
        existDoctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        existDoctor.setNid(doctorDTO.getNid());

        return this.userRepository.save(existDoctor);
    }

    public void deleteDoctor(Long id) {
        UserEntity existDoctor = this.getDoctorById(id);
        this.userRepository.delete(existDoctor);
    }

    public Page<UserEntity> getAllDoctorPaginate(int index, int element) {
        RoleEntity role = roleService.getRoleById(2L);
        return this.userRepository.findAllByRole(role, PageRequest.of(index, element));
    }

    public Long countDoctor() {
        RoleEntity role = this.roleService.getRoleById(2L);
        return this.userRepository.countByRole(role);
    }

    public List<UserEntity> findAllAvailableDoctor(LocalTime arrivalTime, Long department_id) {
        DepartmentEntity existDepartment = this.departmentService.getDepartmentById(department_id);
        return this.userRepository.findAllByAvailableFromLessThanAndAvailableToGreaterThanAndDepartment(arrivalTime, arrivalTime, existDepartment);
    }

    public void save(UserEntity user){
        this.userRepository.save(user);
    }

    public void creatOutpatient(OutpatientEntity savedOutpatient, Long doctor_id) {
        UserEntity doctor = this.getUserById(doctor_id);
        doctor.setOutpatient(List.of(savedOutpatient));
        this.userRepository.save(doctor);
    }

    public UserEntity updateDoctorSchedule(Long doctorId, DoctorScheduleDTO doctorScheduleDTO) {
        UserEntity existDoctor = this.getDoctorById(doctorId);
        existDoctor.setAvailableFrom(doctorScheduleDTO.getAvailableFrom());
        existDoctor.setAvailableTo(doctorScheduleDTO.getAvailableTo());

        return this.userRepository.save(existDoctor);
    }
}
