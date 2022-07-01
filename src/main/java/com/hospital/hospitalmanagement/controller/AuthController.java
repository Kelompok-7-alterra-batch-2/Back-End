package com.hospital.hospitalmanagement.controller;

import com.hospital.hospitalmanagement.controller.payload.Login;
import com.hospital.hospitalmanagement.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Login login){
        authService.register(login);
        return ResponseEntity.noContent().build(); ///204 atau bisa ok()
    }

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody Login login){
        return  ResponseEntity.ok(authService.generateToken(login));
    }
}
