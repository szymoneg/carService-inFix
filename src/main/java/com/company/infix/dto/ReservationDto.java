package com.company.infix.dto;

import lombok.Data;

@Data
public class ReservationDto {
    private int idUser;
    private int idCar;
    private String dataStart;
    private String dataFinish;
    private int idService;
    private String status;
    private String description;
}
