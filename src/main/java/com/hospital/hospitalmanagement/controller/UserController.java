package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.dto.EmailPasswordDTO;
import com.hospital.hospitalmanagement.controller.response.GetTokenDTO;
import com.hospital.hospitalmanagement.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public GetTokenDTO userLogin(@RequestBody EmailPasswordDTO usernamePasswordDTO){
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
