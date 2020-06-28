package com.company.infix.dto;

import lombok.Data;

@Data
public class RepairDto {
    private String idUser;
    private String status;
    private String vin;
    private String idRepair;
    private String idCar;
}
