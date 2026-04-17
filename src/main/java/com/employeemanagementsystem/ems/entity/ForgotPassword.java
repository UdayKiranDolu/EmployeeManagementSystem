package com.employeemanagementsystem.ems.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ForgotPassword {

    private String email;

    private String newPassword;


}
