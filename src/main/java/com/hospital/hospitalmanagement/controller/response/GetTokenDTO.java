package com.hospital.hospitalmanagement.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTokenDTO {
    private String role;
    private String message;
    private String token;
}
