package com.company.infix.controler;

import com.company.infix.dto.CarDto;
import com.company.infix.dto.ReservationDto;
import com.company.infix.service.CarList;
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
    @Autowired
    CarList carList;

    @CrossOrigin
    @RequestMapping(value = "/add-res/{login}",method = RequestMethod.GET)
    public String test(@PathVariable("login") String login){
        return carList.getOwnedCars(login);
    }

    @CrossOrigin
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void>testReservation(@RequestBody ReservationDto resDb){
        jdbc.update("INSERT INTO reservation(iduser,idcar,date_start,date_finish,status,description) VALUES (?,?,?,?,?,?)",
                resDb.getIdUser(),resDb.getIdCar(),null,null,"oczekujący",resDb.getDescription());
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
