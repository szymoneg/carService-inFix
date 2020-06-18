package com.company.infix.controler;

import com.company.infix.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationAPIController {
    @Autowired
    JdbcTemplate jdbc;
    @CrossOrigin
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void>testReservation(ReservationDto resDb){
        jdbc.update("INSERT INTO reservation(iduser,idcar,data_start,data_finish,id_service,status,description) VALUES (?,?,?,?,?,?,?)",
                resDb.getIdUser(),resDb.getIdCar(),resDb.getDataStart(),null,resDb.getIdService(),null,resDb.getDescription());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
