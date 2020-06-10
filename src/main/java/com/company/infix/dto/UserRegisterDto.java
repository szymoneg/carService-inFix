package com.company.infix.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private int idCar;
    private String permision;
    private String name;
    private String surname;
    private String pesel;
    private String driversLicense;
    private String pass;
}
