package com.hospital.hospitalmanagement.service;


import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.controller.response.GetDoctorDTO;
import com.hospital.hospitalmanagement.controller.response.GetDoctorTwoDTO;
import com.hospital.hospitalmanagement.controller.response.GetTokenDTO;
import com.hospital.hospitalmanagement.controller.validation.UnprocessableException;
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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
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

//    @Test
//    public void updateAdmin() {
//        RoleEntity role = new RoleEntity(1L, "admin", LocalDateTime.now());
//        AdminDTO adminDTO = this.easyRandom.nextObject(AdminDTO.class);
//        adminDTO.setDob("2001-10-23");
//        UserEntity user = this.mapper.map(adminDTO, UserEntity.class);
//
//        when(this.roleService.getRoleById(1L)).thenReturn(role);
//        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);
//        when(this.userRepository.save(any(UserEntity.class))).thenReturn(user);
//
//        var result = this.userService.updateAdmin(id, adminDTO);
//
//        assertEquals(user, result);
//    }

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
    public void getAllDoctor_willSuccess() {
        // Given
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());

        UserEntity user1 = this.easyRandom.nextObject(UserEntity.class);
        UserEntity user2 = this.easyRandom.nextObject(UserEntity.class);

        user1.setRole(role);
        user2.setRole(role);

        List<UserEntity> userList = List.of(user1, user2);

        GetDoctorTwoDTO getDoctorTwoDTO1 = this.mapper.map(user1, GetDoctorTwoDTO.class);
        GetDoctorTwoDTO getDoctorTwoDTO2 = this.mapper.map(user2, GetDoctorTwoDTO.class);

        List<GetDoctorTwoDTO> getDoctorTwoDTOList = List.of(getDoctorTwoDTO1, getDoctorTwoDTO2);

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findAllByRole(role)).thenReturn(userList);

        // When
        var result = this.userService.getAllDoctor();

        // Then
        for (int i = 0; i < getDoctorTwoDTOList.size(); i++) {
            GetDoctorTwoDTO dto = getDoctorTwoDTOList.get(i);
            GetDoctorTwoDTO res = result.get(i);

            assertEquals(dto.getId(), res.getId());
            assertEquals(dto.getEmail(), res.getEmail());
            assertEquals(dto.getName(), res.getName());
            assertEquals(dto.getDepartment(), res.getDepartment());
            assertEquals(dto.getRole(), res.getRole());
            assertEquals(dto.getCreatedAt(), res.getCreatedAt());
            assertEquals(dto.getNid(), dto.getNid());
            assertEquals(dto.getDob(), dto.getDob());
        }
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
        when(this.userRepository.findByNameContainsIgnoreCaseAndRole(name, role)).thenReturn(userList);

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

//    @Test
//    public void updateDoctor() {
//        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());
//        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);
//        DoctorDTO doctorDTO = this.easyRandom.nextObject(DoctorDTO.class);
//        doctorDTO.setDob("2001-10-23");
//        UserEntity user = this.mapper.map(doctorDTO, UserEntity.class);
//
//        when(this.departmentService.getDepartmentById(doctorDTO.getDepartment_id())).thenReturn(department);
//        when(this.roleService.getRoleById(2L)).thenReturn(role);
//        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);
//        when(this.userRepository.save(any(UserEntity.class))).thenReturn(user);
//
//        var result = this.userService.updateDoctor(id, doctorDTO);
//
//        assertEquals(user, result);
//    }

    @Test
    public void deleteDoctor() {
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());

        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findByIdAndRole(id, role)).thenReturn(user);

        this.userService.deleteDoctor(id);

        verify(this.userRepository, times(1)).delete(user);
    }

//    @Test
//    public void getAllDoctorPaginate() {
//        // Given
//        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());
//        int index = 1;
//        int element = 2;
//
//        UserEntity user1 = this.easyRandom.nextObject(UserEntity.class);
//        UserEntity user2 = this.easyRandom.nextObject(UserEntity.class);
//
//        user1.setRole(role);
//        user2.setRole(role);
//
//        Page<UserEntity> page = new Page<UserEntity>() {
//            @Override
//            public int getTotalPages() {
//                return 0;
//            }
//
//            @Override
//            public long getTotalElements() {
//                return 0;
//            }
//
//            @Override
//            public <U> Page<U> map(Function<? super UserEntity, ? extends U> converter) {
//                return null;
//            }
//
//            @Override
//            public int getNumber() {
//                return 0;
//            }
//
//            @Override
//            public int getSize() {
//                return 0;
//            }
//
//            @Override
//            public int getNumberOfElements() {
//                return 0;
//            }
//
//            @Override
//            public List<UserEntity> getContent() {
//                return List.of(user1, user2);
//            }
//
//            @Override
//            public boolean hasContent() {
//                return false;
//            }
//
//            @Override
//            public Sort getSort() {
//                return null;
//            }
//
//            @Override
//            public boolean isFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean isLast() {
//                return false;
//            }
//
//            @Override
//            public boolean hasNext() {
//                return false;
//            }
//
//            @Override
//            public boolean hasPrevious() {
//                return false;
//            }
//
//            @Override
//            public Pageable nextPageable() {
//                return null;
//            }
//
//            @Override
//            public Pageable previousPageable() {
//                return null;
//            }
//
//            @Override
//            public Iterator<UserEntity> iterator() {
//                return null;
//            }
//        };
//
//        when(this.roleService.getRoleById(2L)).thenReturn(role);
//        when(this.userRepository.findAllByRole(role, PageRequest.of(index, element))).thenReturn(page);
//
//        var result = this.userService.getAllDoctorPaginate(index, element);
//
//        assertEquals(page, result);
//
//    }

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

    @Test
    public void getDoctorByEmail() {
        // Given
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());
        String email = "admin@gmail.com";

        UserEntity user = this.easyRandom.nextObject(UserEntity.class);

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findByEmailAndRole(email, role)).thenReturn(user);

        // When
        var result = this.userService.getDoctorByEmail(email);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void updateAdmin() {
        UpdateAdminDTO updateAdminDTO = this.easyRandom.nextObject(UpdateAdminDTO.class);
        updateAdminDTO.setDob("2001-10-23");
        UserEntity admin = this.easyRandom.nextObject(UserEntity.class);

        when(this.userService.getAdminById(id)).thenReturn(admin);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(admin);

        var result = this.userService.updateAdmin(id, updateAdminDTO);

        assertEquals(admin, result);
    }

    @Test
    public void updateAdmin_willFailed() {
        UpdateAdminDTO updateAdminDTO = this.easyRandom.nextObject(UpdateAdminDTO.class);
        updateAdminDTO.setDob("2001-10-23");
        UserEntity admin = this.easyRandom.nextObject(UserEntity.class);
        UserEntity admin2 = this.easyRandom.nextObject(UserEntity.class);

        when(this.userService.getAdminById(id)).thenReturn(admin);
        when(this.userService.getUserByEmail(updateAdminDTO.getEmail())).thenReturn(admin2);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(admin);

        assertThrows(UnprocessableException.class, () -> userService.updateAdmin(id,updateAdminDTO), "This Email Is Duplicate");
    }

    @Test
    public void updateDoctor() {
        UpdateDoctorDTO doctorDTO = this.easyRandom.nextObject(UpdateDoctorDTO.class);
        doctorDTO.setDob("2001-10-23");
        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);
        UserEntity doctor = this.easyRandom.nextObject(UserEntity.class);

        when(this.departmentService.getDepartmentById(doctorDTO.getDepartment_id())).thenReturn(department);
        when(this.userService.getDoctorById(id)).thenReturn(doctor);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(doctor);

        var result = this.userService.updateDoctor(id, doctorDTO);

        assertEquals(doctor, result);
    }

    @Test
    public void updateDoctor_willFailed() {
        UpdateDoctorDTO doctorDTO = this.easyRandom.nextObject(UpdateDoctorDTO.class);
        doctorDTO.setDob("2001-10-23");
        DepartmentEntity department = this.easyRandom.nextObject(DepartmentEntity.class);
        UserEntity doctor = this.easyRandom.nextObject(UserEntity.class);
        UserEntity doctor2 = this.easyRandom.nextObject(UserEntity.class);

        when(this.userService.getUserByEmail(doctorDTO.getEmail())).thenReturn(doctor2);
        when(this.departmentService.getDepartmentById(doctorDTO.getDepartment_id())).thenReturn(department);
        when(this.userService.getDoctorById(id)).thenReturn(doctor);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(doctor);

//        var result = this.userService.updateDoctor(id, doctorDTO);

        assertThrows(UnprocessableException.class, () -> userService.updateDoctor(id, doctorDTO), "This Email Is Duplicate");
    }

    @Test
    public void getAllDoctorPaginate() {
        RoleEntity role = new RoleEntity(2L, "doctor", LocalDateTime.now());
        Page<UserEntity> doctors = mock(Page.class);

        Page<GetDoctorDTO> dtoPage = doctors.map(entity -> GetDoctorDTO.builder()
                .email(entity.getEmail())
                .id(entity.getId())
                .name(entity.getName())
                .department(entity.getDepartment())
                .role(entity.getRole())
                .phoneNumber(entity.getPhoneNumber())
                .nid(entity.getNid())
                .createdAt(entity.getCreatedAt())
                .dob(entity.getDob())
                .build());

        when(this.roleService.getRoleById(2L)).thenReturn(role);
        when(this.userRepository.findAllByRole(any(RoleEntity.class), any())).thenReturn(doctors);

        var result = this.userService.getAllDoctorPaginate(any());

        assertEquals(dtoPage, result);
    }

    @Test
    public void resetPassword() {
        UserEntity user = this.easyRandom.nextObject(UserEntity.class);
        PasswordDTO passwordDTO = this.easyRandom.nextObject(PasswordDTO.class);

        when(this.userRepository.findById(id)).thenReturn(Optional.of(user));
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(user);

        var result = this.userService.resetPassword(id, passwordDTO);

        assertEquals(user, result);
    }
}