package com.hospital.hospitalmanagement.service;


import com.hospital.hospitalmanagement.controller.dto.AdminDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorDTO;
import com.hospital.hospitalmanagement.controller.dto.DoctorScheduleDTO;
import com.hospital.hospitalmanagement.controller.dto.EmailPasswordDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorTwoDTO;
import com.hospital.hospitalmanagement.controller.response.GetTokenDTO;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.repository.UserRepository;
import com.hospital.hospitalmanagement.security.JwtProvider;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();

    private Long id;

    @InjectMocks
    @Spy
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleServiceImpl roleService;

    @Mock
    DepartmentServiceImpl departmentService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtProvider jwtProvider;

    @Spy
    private ModelMapper mapper = new ModelMapper();

    @Before
    public void setUp(){
        this.id = this.easyRandom.nextLong();
    }

    @Test
    public void getAllUser_willSuccess() {
        // Given
        UserEntity user1 = this.easyRandom.nextObject(UserEntity.class);
        UserEntity user2 = this.easyRandom.nextObject(UserEntity.class);

        List<UserEntity> userList = List.of(user1, user2);

        when(this.userRepository.findAll()).thenReturn(userList);

        // When
        var result = this.userService.getAllUser();

        // Then
        assertEquals(userList, result);
    }

    @Test
    public void getAllAdmin_willSuccess() {
        // Given
        RoleEntity role = new RoleEntity(1L, "admin", LocalDateTime.now());

        UserEntity user1 = this.easyRandom.nextObject(UserEntity.class);
        UserEntity user2 = this.easyRandom.nextObject(UserEntity.class);

        user1.setRole(role);
        user2.setRole(role);

        List<UserEntity> userList = List.of(user1, user2);

        when(this.roleService.getRoleById(1L)).thenReturn(role);
        when(this.userRepository.findAllByRole(role)).thenReturn(userList);

        // When
        var result = this.userService.getAllAdmin();

        // Then
        assertEquals(userList, result);
    }

    @Test
    public void getUserById_willSuccess() {
        // Given
        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        var result = this.userService.getUserById(id);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void getAdminById_willSuccess() {
        // Given
        RoleEntity role = new RoleEntity(1L, "admin", LocalDateTime.now());

        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.roleService.getRoleById(1L)).thenReturn(role);
        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);

        // When
        var result = this.userService.getAdminById(id);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void getAdminByEmail_willSuccess() {
        // Given
        String email = "michael@gmail.com";
        RoleEntity role = new RoleEntity(1L, "admin", LocalDateTime.now());
        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.roleService.getRoleById(1L)).thenReturn(role);
        when(this.userRepository.findByEmailAndRole(email, role)).thenReturn(user);

        // When
        var result = this.userService.getAdminByEmail(email);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void createAdmin_willSuccess() {
        RoleEntity role = new RoleEntity(1L, "admin", LocalDateTime.now());
        AdminDTO adminDTO = this.easyRandom.nextObject(AdminDTO.class);
        adminDTO.setDob("2001-10-23");
        UserEntity user = this.mapper.map(adminDTO, UserEntity.class);

        when(this.roleService.getRoleById(1L)).thenReturn(role);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(user);

        var result = this.userService.createAdmin(adminDTO);

        assertEquals(user, result);

    }

    @Test
    public void updateAdmin() {
        RoleEntity role = new RoleEntity(1L, "admin", LocalDateTime.now());
        AdminDTO adminDTO = this.easyRandom.nextObject(AdminDTO.class);
        adminDTO.setDob("2001-10-23");
        UserEntity user = this.mapper.map(adminDTO, UserEntity.class);

        when(this.roleService.getRoleById(1L)).thenReturn(role);
        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(user);

        var result = this.userService.updateAdmin(id, adminDTO);

        assertEquals(user, result);
    }

    @Test
    public void deleteAdmin() {
        // Given
        RoleEntity role = new RoleEntity(1L, "admin", LocalDateTime.now());
        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.roleService.getRoleById(1L)).thenReturn(role);
        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);

        // When
        this.userService.deleteAdmin(id);

        // Then
        verify(this.userRepository, times(1)).delete(user);
    }

    @Test
    public void convertDoctorEntityToResponse() {
    }

    @Test
    public void convertOutpatientEntityToResponse() {
    }

    @Test
    public void convertOutpatient() {
    }

    @Test
    public void getAllDoctor_willSuccess() {
        // Given
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());

        UserEntity user1 = this.easyRandom.nextObject(UserEntity.class);
        UserEntity user2 = this.easyRandom.nextObject(UserEntity.class);

        user1.setRole(role);
        user2.setRole(role);

        List<UserEntity> userList = List.of(user1, user2);

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findAllByRole(role)).thenReturn(userList);

        // When
        List<GetDoctorTwoDTO> allDoctor = this.userService.getAllDoctor();

        // Then
        verify(this.userService, times(2)).convertDoctorEntityToResponse(any(UserEntity.class));
    }

    @Test
    public void getById_willSuccess() {
        // Given
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());

        UserEntity user = this.easyRandom.nextObject(UserEntity.class);
        GetDoctorTwoDTO getDoctorTwoDTO = this.mapper.map(user, GetDoctorTwoDTO.class);

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);
        when(this.userService.convertDoctorEntityToResponse(user)).thenReturn(getDoctorTwoDTO);

        // When
        var result = this.userService.getById(id);

        // Then
        assertEquals(getDoctorTwoDTO, result);
    }

    @Test
    public void getDoctorById_willSuccess() {
        // Given
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());

        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);

        // When
        var result = this.userService.getDoctorById(id);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void getDoctorByDepartment() {
        // Given
        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);

        UserEntity user1 = this.easyRandom.nextObject(UserEntity.class);
        UserEntity user2 = this.easyRandom.nextObject(UserEntity.class);

        List<UserEntity> userList = List.of(user1, user2);

        when(this.departmentService.getDepartmentById(id)).thenReturn(department);
        when(this.userRepository.findAllByDepartment(department)).thenReturn(userList);

        // When
        var result = this.userService.getDoctorByDepartment(id);

        // Then
        assertEquals(userList, result);
    }

    @Test
    public void getDoctorByName() {
        // Given
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());
        String name = "michael";

        UserEntity user1 = this.easyRandom.nextObject(UserEntity.class);
        UserEntity user2 = this.easyRandom.nextObject(UserEntity.class);

        user1.setRole(role);
        user2.setRole(role);

        List<UserEntity> userList = List.of(user1, user2);

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findByNameContainsAndRole(name, role)).thenReturn(userList);

        var result = this.userService.getDoctorByName(name);

        assertEquals(userList, result);
    }

    @Test
    public void createDoctor() {
        // Given
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());
        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);
        DoctorDTO doctorDTO = this.easyRandom.nextObject(DoctorDTO.class);
        doctorDTO.setDob("2001-10-23");
        UserEntity user = this.mapper.map(doctorDTO, UserEntity.class);

        when(this.departmentService.getDepartmentById(doctorDTO.getDepartment_id())).thenReturn(department);
        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(user);

        // When
        var result = this.userService.createDoctor(doctorDTO);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void updateDoctor() {
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());
        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);
        DoctorDTO doctorDTO = this.easyRandom.nextObject(DoctorDTO.class);
        doctorDTO.setDob("2001-10-23");
        UserEntity user = this.mapper.map(doctorDTO, UserEntity.class);

        when(this.departmentService.getDepartmentById(doctorDTO.getDepartment_id())).thenReturn(department);
        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(user);

        var result = this.userService.updateDoctor(id, doctorDTO);

        assertEquals(user, result);
    }

    @Test
    public void deleteDoctor() {
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());

        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);

        this.userService.deleteDoctor(id);

        verify(this.userRepository, times(1)).delete(user);
    }

    @Test
    public void getAllDoctorPaginate() {
    }

    @Test
    public void countDoctor() {
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());

        Long count = 3L;

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.countByRole(role)).thenReturn(count);

        var result = this.userService.countDoctor();

        assertEquals(count, result);
    }


    @Test
    public void loadUserByUsername() {
        String username = "michael@gmail.com";
        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.userRepository.getDistinctTopByUsername(username)).thenReturn(user);

        var result = this.userService.loadUserByUsername(username);

        assertEquals(user, result);

    }

    @Test
    public void generateToken() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJ1c2VybmFtZSI6Im1pY2hhZWwxQGdtYWlsLmNvbSIsImlhdCI6MTY1NzQ1Mjk2OSwiZXhwIjoxNjU3NDU2NTY5fQ.Guk4bBMoSBilXaQNmvL9-imJHVZ4S5Y0HxQLzJJ0Lu8";
        EmailPasswordDTO emailPasswordDTO = this.easyRandom.nextObject(EmailPasswordDTO.class);
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticationToken);
        when(this.jwtProvider.generateToken(any(Authentication.class))).thenReturn(jwt);
        when(this.userRepository.getDistinctTopByUsername(emailPasswordDTO.getEmail())).thenReturn(user);

        var result = this.userService.generateToken(emailPasswordDTO);

        assertEquals(jwt, result.getToken());
        assertEquals(user.getRole().getName(), result.getRole());
        assertNull(result.getMessage());
    }
}