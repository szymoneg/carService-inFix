package com.company.infix.controler;

import com.company.infix.dto.CarDto;
import com.company.infix.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationAPIController {
    @Autowired
    JdbcTemplate jdbc;

    @CrossOrigin
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void>testReservation(@RequestBody ReservationDto resDb){

        jdbc.update("INSERT INTO reservation(iduser,idcar,data_start,data_finish,status,description) VALUES (?,?,?,?,?,?)",
                resDb.getIdUser(),resDb.getIdCar(),resDb.getDataStart(),null,resDb.getIdService(),null,resDb.getDescription());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/add-car", method = RequestMethod.POST)
    public ResponseEntity<Void>testAddCar(@RequestBody CarDto carDto){
        try {
            jdbc.queryForObject("SELECT vin FROM car WHERE vin=?",new Object[]{carDto.getVin()},String.class);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }catch (IncorrectResultSizeDataAccessException e){
            System.out.println(carDto.getMarka());
            jdbc.update("INSERT INTO car(marka,model,engine_capacity,vin,year_of,iduser) VALUES (?,?,?,?,?,?)",
                    carDto.getMarka(), carDto.getModel(), carDto.getEngineCapacity(), carDto.getVin(), carDto.getYearOf(), carDto.getIdUser());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


}
