package com.company.infix.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String permision;
    private String name;
    private String surname;
    private String pesel;
    private String driversLicense;
    private String password;
    private String login;
}
