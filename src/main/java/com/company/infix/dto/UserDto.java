package com.company.infix.dto;

import lombok.Data;
import lombok.Getter;

//To skurwiałe gówno nie działa, nie wiem jakim chujem, może sie naprawi innym
// razem mam namyśli tego skurwiałego lombok'a
@Getter
public class UserDto {
    private int idUser;
    private int idCar;
    private String permision;
    private String name1;
    private String surname;
    private String pesel;
    private String driversLicense;

}
