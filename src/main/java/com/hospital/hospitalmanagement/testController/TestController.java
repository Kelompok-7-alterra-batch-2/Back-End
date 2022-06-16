package com.hospital.hospitalmanagement.testController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String getHello(){
        return "Hello";
    }
}
