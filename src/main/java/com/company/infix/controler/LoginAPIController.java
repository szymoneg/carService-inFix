package com.company.infix.controler;


import com.company.infix.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class LoginAPIController {
    @Autowired
    JdbcTemplate jdbc;
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Void> testLogin(@RequestBody UserLoginDto db) throws NoSuchAlgorithmException {
        RegisterAPIController n1 = new RegisterAPIController();
        String password = n1.HashMethod(db.getPassword());
        try {
            String test = jdbc.queryForObject("select name from user where login=? and password=?", new Object[]{db.getLogin(), password}, String.class);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IncorrectResultSizeDataAccessException e){
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
