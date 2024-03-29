package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.EmailPasswordDTO;
import com.hospital.hospitalmanagement.controller.dto.PasswordDTO;
import com.hospital.hospitalmanagement.controller.response.GetTokenDTO;
import com.hospital.hospitalmanagement.entities.UserEntity;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<GetTokenDTO> userLogin(@RequestBody EmailPasswordDTO usernamePasswordDTO){
        GetTokenDTO getTokenDTO = new GetTokenDTO();

        try{
            GetTokenDTO obj = this.userService.generateToken(usernamePasswordDTO);

            getTokenDTO.setToken(obj.getToken());
            getTokenDTO.setRole(obj.getRole());
            getTokenDTO.setMessage("Success");
            getTokenDTO.setEmail(obj.getEmail());
            return ResponseEntity.ok().body(getTokenDTO);
        }catch (Exception e){
            getTokenDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getTokenDTO);
        }
    }

    @PutMapping("/password/{userId}")
    public ResponseEntity<UserEntity> resetPassword(@PathVariable("userId") Long userId ,@Valid @RequestBody PasswordDTO passwordDTO){
        try {
            UserEntity savedUser = this.userService.resetPassword(userId, passwordDTO);
            return ResponseEntity.ok().body(savedUser);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
