package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.AdminDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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

    public UserEntity getDoctorById(Long id){
        RoleEntity existRole = this.roleService.getRoleById(2L);

        return this.userRepository.findByIdAndRole(id, existRole);
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

        return this.userRepository.save(existDoctor);
    }

    public void deleteDoctor(Long id) {
        UserEntity existDoctor = this.getDoctorById(id);
        this.userRepository.delete(existDoctor);
    }

    public Page<UserEntity> getAllDoctorPaginate(int index, int element) {
        RoleEntity role = roleService.getRoleById(2L);
//        return this.userRepository.findAll(PageRequest.of(index, element));
        return this.userRepository.findAllByRole(role, PageRequest.of(index, element));
    }
}
