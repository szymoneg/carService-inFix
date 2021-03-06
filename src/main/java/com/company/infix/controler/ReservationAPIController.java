package com.company.infix.controler;

import com.company.infix.dao.CarDao;
import com.company.infix.dao.ReservationDao;
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
    @Autowired
    CarDao carDao;
    @Autowired
    ReservationDao reservationDao;

    //Pierwszy krok rezerwacji
    //Metoda zwraca liste samochodów usera
    @CrossOrigin
    @RequestMapping(value = "/add-res/{login}",method = RequestMethod.GET)
    public String test(@PathVariable("login") String login){
        return carDao.testGetOwnedCars(login);
    }

    //Drugi krok rezerwacji
    @CrossOrigin
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String>reservation(@RequestBody ReservationDto resDb){
        return reservationDao.testReservation(resDb);
    }

    //Dodanie samochodu
    @CrossOrigin
    @RequestMapping(value = "/add-car/{login}", method = RequestMethod.POST)
    public ResponseEntity<String>addCar(@RequestBody CarDto carDto,@PathVariable("login") String login){
        return carDao.testAddCar(carDto,login);
    }


}
