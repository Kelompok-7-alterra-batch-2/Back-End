package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.AdminDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        return this.userRepository.save(existAdmin);
    }

    public void deleteAdmin(Long id){
        UserEntity existAdmin = this.getAdminById(id);
        this.userRepository.delete(existAdmin);
    }

    public List<UserEntity> getAllDoctor(){
        RoleEntity role = this.roleService.getRoleById(2L);
        return this.userRepository.findAllByRole(role);
    }

    public GetDoctorDTO getById(Long id){
        RoleEntity existRole = this.roleService.getRoleById(2L);
        UserEntity optional = this.userRepository.findByIdAndRole(id, existRole);

        Optional<UserEntity> exist = this.userRepository.findById(id);

        if (exist.isEmpty()){
            return null;
        }

        GetDoctorDTO obj = new GetDoctorDTO();

        obj.setId(optional.getId());
        obj.setId(optional.getId());
        obj.setName(optional.getName());
        obj.setEmail(optional.getEmail());
        obj.setDob(optional.getDob().toString());
        obj.setAvailableFrom(optional.getAvailableFrom());
        obj.setAvailableTo(optional.getAvailableTo());

        return obj;
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
                .availableFrom(doctorDTO.getAvailableFrom())
                .availableTo(doctorDTO.getAvailableTo())
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
        existDoctor.setAvailableFrom(doctorDTO.getAvailableFrom());
        existDoctor.setAvailableTo(doctorDTO.getAvailableTo());
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
        return this.userRepository.findAllAvailableDoctor(arrivalTime, department_id);
    }

    public void save(UserEntity user){
        this.userRepository.save(user);
    }

    public void creatOutpatient(OutpatientEntity savedOutpatient, Long doctor_id) {
        UserEntity doctor = this.getUserById(doctor_id);
        doctor.setOutpatient(List.of(savedOutpatient));
        this.userRepository.save(doctor);
    }
}
