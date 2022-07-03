package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.AdminDTO;
import com.hospital.hospitalmanagement.controller.dto.EmailPasswordDTO;
import com.hospital.hospitalmanagement.controller.response.GetTokenDTO;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return this.userService.getAdminById(id);
    }

    @PostMapping
    public UserEntity createUserAdmin(@RequestBody AdminDTO adminDTO) {
        return this.userService.createAdmin(adminDTO);
    }


    @PutMapping("/{id}")
    public UserEntity updateAdminById(@RequestBody AdminDTO adminDTO, @PathVariable("id") Long id){
        return this.userService.updateAdmin(id, adminDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAdminById(@PathVariable("id")Long id){
        this.userService.deleteAdmin(id);
    }

    @GetMapping("/emails/{email}")
    public UserEntity getAdminByEmail(@PathVariable("email") String email){
        return this.userService.getAdminByEmail(email);
    }

    @PostMapping("/login")
    public GetTokenDTO getToken(@RequestBody EmailPasswordDTO usernamePasswordDTO, Principal principal){
        GetTokenDTO getTokenDTO = new GetTokenDTO();

        try{
            GetTokenDTO obj = this.userService.generateToken(usernamePasswordDTO);

            getTokenDTO.setToken(obj.getToken());
            getTokenDTO.setRole(obj.getRole());
            getTokenDTO.setMessage("Success");
        }catch (Exception e){
            getTokenDTO.setMessage(e.getMessage());
        }

        return getTokenDTO;
    }

}
