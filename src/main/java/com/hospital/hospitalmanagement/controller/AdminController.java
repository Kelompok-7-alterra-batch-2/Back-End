package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.AdminDTO;
import com.hospital.hospitalmanagement.controller.dto.EmailPasswordDTO;
import com.hospital.hospitalmanagement.controller.response.GetTokenDTO;
import com.hospital.hospitalmanagement.controller.validation.NotFoundException;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllAdmin(){
        return ResponseEntity.ok().body(this.userService.getAllAdmin());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getAdminById(@PathVariable("id") Long id){
        UserEntity user=this.userService.getAdminById(id);
        if (user == null){
            throw new NotFoundException("Data Not Found");
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUserAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        return ResponseEntity.ok().body(this.userService.createAdmin(adminDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateAdminById(@Valid @RequestBody AdminDTO adminDTO, @PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.userService.updateAdmin(id, adminDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminById(@PathVariable("id")Long id){
        this.userService.deleteAdmin(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/emails/{email}")
    public ResponseEntity<UserEntity> getAdminByEmail(@PathVariable("email") String email){
        UserEntity user = this.userService.getAdminByEmail(email);
        if (user == null){
            throw new NotFoundException("Data Not Found");
        }
        return ResponseEntity.ok().body(user);
    }

}
