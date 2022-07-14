package com.hospital.hospitalmanagement.service;

import com.hospital.hospitalmanagement.controller.dto.AdminDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorScheduleDTO;
import com.hospital.hospitalmanagement.controller.dto.EmailPasswordDTO;
import com.hospital.hospitalmanagement.controller.response.*;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.controller.validation.UnprocessableException;
import com.hospital.hospitalmanagement.entities.*;
import com.hospital.hospitalmanagement.repository.UserRepository;
import com.hospital.hospitalmanagement.security.JwtProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final DepartmentServiceImpl departmentService;

    private final RoleServiceImpl roleService;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(@Lazy UserRepository userRepository,
                           @Lazy DepartmentServiceImpl departmentService,
                           @Lazy RoleServiceImpl roleService,
                           @Lazy JwtProvider jwtProvider,
                           @Lazy PasswordEncoder passwordEncoder,
                           @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.departmentService = departmentService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

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

    public UserEntity getUserByEmail(String email){
         return this.userRepository.findByEmail(email);
    }

    public UserEntity createAdmin(AdminDTO adminDTO) {
        RoleEntity existRole = this.roleService.getRoleById(1L);

        UserEntity unique = this.getUserByEmail(adminDTO.getEmail());

        if (unique != null){
            throw new UnprocessableException("This Email Is Duplicate");
        }

        LocalDate dob = LocalDate.parse(adminDTO.getDob());

        UserEntity newAdmin = UserEntity.builder()
                .name(adminDTO.getName())
                .username(adminDTO.getEmail())
                .dob(dob)
                .email(adminDTO.getEmail())
                .password(passwordEncoder.encode(adminDTO.getPassword()))
                .phoneNumber(adminDTO.getPhoneNumber())
                .role(existRole)
                .createdAt(LocalDateTime.now())
                .build();

        UserEntity save = this.userRepository.save(newAdmin);
        return save;
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

    public GetDoctorTwoDTO convertDoctorEntityToResponse(UserEntity doctor){
        GetDoctorTwoDTO getDoctorTwoDTO = GetDoctorTwoDTO.builder()
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

            GetDoctorTwoDTO getDoctorTwoDTO = this.convertDoctorEntityToResponse(doctor);

            getDoctorDTOList.add(getDoctorTwoDTO);
        }

        return getDoctorDTOList;
    }

    public GetDoctorTwoDTO getById(Long id){
        RoleEntity existRole = this.roleService.getRoleById(2L);
        Optional<UserEntity> doctor = Optional.ofNullable(this.userRepository.findByIdAndRole(id, existRole));

        if (doctor.isEmpty()){
            throw new NotFoundException("Data Not Found");
        }

        UserEntity existDoctor = doctor.get();

        return convertDoctorEntityToResponse(existDoctor);
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

        return this.userRepository.findByNameContainsIgnoreCaseAndRole(name, existRole);
    }

    public UserEntity getDoctorByEmail(String email){
        RoleEntity existRole = this.roleService.getRoleById(2L);

        return this.userRepository.findByEmailAndRole(email, existRole);
    }


    public UserEntity createDoctor(DoctorDTO doctorDTO) {
        LocalDate dob = LocalDate.parse(doctorDTO.getDob());
        DepartmentEntity existDepartment = departmentService.getDepartmentById(doctorDTO.getDepartment_id());
        RoleEntity role = roleService.getRoleById(2L);

        UserEntity uniq = this.getUserByEmail(doctorDTO.getEmail());

        if (uniq != null){
            throw new UnprocessableException("This Email Is Duplicate");
        }

        UserEntity newDoctor = UserEntity.builder()
                .name(doctorDTO.getName())
                .dob(dob)
                .email(doctorDTO.getEmail())
                .username(doctorDTO.getEmail())
                .password(passwordEncoder.encode(doctorDTO.getPassword()))
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

//    public List<UserEntity> findAllAvailableDoctor(LocalTime arrivalTime, Long department_id) {
//        DepartmentEntity existsDepartment = this.departmentService.getDepartmentById(department_id);
//        return this.userRepository.findAllByAvailableFromLessThanAndAvailableToGreaterThanAndDepartment(arrivalTime, arrivalTime, existsDepartment);
//    }

    public void save(UserEntity user){
        this.userRepository.save(user);
    }

    public void creatOutpatient(OutpatientEntity savedOutpatient, Long doctor_id) {
        UserEntity doctor = this.getUserById(doctor_id);
        doctor.setOutpatient(List.of(savedOutpatient));
        this.userRepository.save(doctor);
    }

//    public UserEntity updateDoctorSchedule(Long doctorId, DoctorScheduleDTO doctorScheduleDTO) {
//        UserEntity existDoctor = this.getDoctorById(doctorId);
//        existDoctor.setAvailableFrom(doctorScheduleDTO.getAvailableFrom());
//        existDoctor.setAvailableTo(doctorScheduleDTO.getAvailableTo());
//
//        return this.userRepository.save(existDoctor);
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity existUser = this.userRepository.getDistinctTopByUsername(username);
        if(existUser == null){
            throw new UsernameNotFoundException("Username Not Found");
        }
        return existUser;
    }

    public GetTokenDTO generateToken(EmailPasswordDTO usernamePasswordDTO) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usernamePasswordDTO.getEmail(),
                            usernamePasswordDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserEntity existUser = this.userRepository.getDistinctTopByUsername(usernamePasswordDTO.getEmail());
            GetTokenDTO getTokenDTO = new GetTokenDTO();
            getTokenDTO.setToken(jwt);
            getTokenDTO.setRole(existUser.getRole().getName());
            getTokenDTO.setEmail(existUser.getEmail());
            return getTokenDTO;
        }catch (BadCredentialsException e){
            log.error("Bad Credential ", e);
            throw new RuntimeException(e.getMessage(), e);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
