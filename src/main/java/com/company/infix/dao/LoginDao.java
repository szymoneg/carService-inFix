package com.company.infix.dao;

import com.company.infix.dto.UserDto;
import com.company.infix.service.CheckValues;
import com.company.infix.service.HashPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Component
public class LoginDao {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    private HashPassword hashPassword;
    @Autowired
    CheckValues chkVal;


    public ResponseEntity<Void> testLogin(UserDto db) throws NoSuchAlgorithmException {
        String password = hashPassword.HashMethod(db.getPassword());
        String login = db.getLogin();
        if (chkVal.checkLogin(login)) {
            try {
                String test = jdbc.queryForObject("select name from user where login=? and password=?", new Object[]{login, password}, String.class);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (IncorrectResultSizeDataAccessException e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
