package com.employeemanagementsystem.ems.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class LoginRequest {

    private String identifier;

    private String password;


}
