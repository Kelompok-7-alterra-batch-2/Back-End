package com.hospital.hospitalmanagement.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetTokenDTO {
    private String role;
    private String message;
    private String token;
}
