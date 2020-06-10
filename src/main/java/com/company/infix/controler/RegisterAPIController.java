package com.company.infix.controler;

import com.company.infix.dto.UserDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//To bedzie klasa do rejestracji, ten format klasy to odczyt z JSON
//trzeba zrobic zapytanie INSERT
@RestController
public class RegisterAPIController {
    @Autowired
    JdbcTemplate jdbc;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> testRegister(@RequestBody UserDto userDto){
        System.out.println("Metoda działą"+userDto.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
