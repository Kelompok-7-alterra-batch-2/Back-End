package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.AdminDTO;
import com.hospital.hospitalmanagement.controller.dto.EmailPasswordDTO;
import com.hospital.hospitalmanagement.controller.response.GetTokenDTO;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/users")
    public List<UserEntity> getAllUsers(){
        return this.userService.getAllUser();
    }

    @GetMapping
    public List<UserEntity> getAllAdmin(){
        return this.userService.getAllAdmin();
    }

    @GetMapping("/{id}")
    public UserEntity getAdminById(@PathVariable("id") Long id){
        UserEntity user=this.userService.getAdminById(id);
        if (user == null){
            throw new NotFoundException("Data Not Found");
        }
        return user;
    }

    @PostMapping
    public UserEntity createUserAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        return this.userService.createAdmin(adminDTO);
    }


    @PutMapping("/{id}")
    public UserEntity updateAdminById(@Valid @RequestBody AdminDTO adminDTO, @PathVariable("id") Long id){
        return this.userService.updateAdmin(id, adminDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAdminById(@PathVariable("id")Long id){
        this.userService.deleteAdmin(id);
    }

    @GetMapping("/emails/{email}")
    public UserEntity getAdminByEmail(@PathVariable("email") String email){
        UserEntity user = this.userService.getAdminByEmail(email);
        if (user == null){
            throw new NotFoundException("Data Not Found");
        }
        return user;
    }

}
