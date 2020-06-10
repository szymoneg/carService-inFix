package com.company.infix.controler;


import com.company.infix.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class LoginAPIController {
    @Autowired
    JdbcTemplate jdbc;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Void> testLogin(@RequestBody UserLoginDto db) throws NoSuchAlgorithmException {
        RegisterAPIController n1 = new RegisterAPIController();
        String password = n1.HashMethod(db.getPass());
        try {
            String test = jdbc.queryForObject("select name from user where name=? and password=?", new Object[]{db.getLogin(), password}, String.class);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IncorrectResultSizeDataAccessException e){
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
