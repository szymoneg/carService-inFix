package com.company.infix.dto;

import lombok.Data;

@Data
public class ReservationDto {
    private String idReservation;
    private String idUser;
    private String idCar;
    private String dateStart;
    private String dateFinish;
    private String status;
    private String description;
}
