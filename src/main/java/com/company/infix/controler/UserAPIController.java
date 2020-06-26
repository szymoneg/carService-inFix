package com.company.infix.controler;

import com.company.infix.dao.*;
import com.company.infix.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
public class UserAPIController {
    @Autowired
    RegisterDao registerDao;
    @Autowired
    LoginDao loginDao;
    @Autowired
    CarDao carDao;
    @Autowired
    RepairDao repairDao;
    @Autowired
    ReservationDao reservationDao;

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> register(@RequestBody UserDto db) throws MessagingException, NoSuchAlgorithmException {
        return registerDao.testRegister(db);
    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Void> login(@RequestBody UserDto db) throws NoSuchAlgorithmException {
        return loginDao.testLogin(db);
    }

    @CrossOrigin
    @RequestMapping(value = "/get-car/{login}", method = RequestMethod.GET)
    public String getCars(@PathVariable("login") String login) throws SQLException {
        return carDao.testGetCars(login);
    }

    @CrossOrigin
    @RequestMapping(value = "/search/{vin}",method = RequestMethod.GET)
    public String searchRepair(@PathVariable("vin") String vin){
        return repairDao.testSearchRepair(vin);
    }

    @CrossOrigin
    @RequestMapping(value = "/show-history/{login}",method = RequestMethod.GET)
    public String showHistory(@PathVariable("login") String login){
        return reservationDao.testShowOldReservation(login);
    }
}
