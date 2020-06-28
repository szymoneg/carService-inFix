package com.company.infix.dao;

import com.company.infix.dto.UserDto;
import com.company.infix.service.CheckValues;
import com.company.infix.service.HashPassword;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Component
public class LoginDao {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    private HashPassword hashPassword;
    @Autowired
    CheckValues chkVal;


    public ResponseEntity<String> testLogin(UserDto db) throws NoSuchAlgorithmException {
        String password = hashPassword.HashMethod(db.getPassword());
        String login = db.getLogin();
        ArrayList loginArr = new ArrayList<>();
        if (chkVal.checkLogin(login)) {
            try {
                loginArr = (ArrayList) jdbc.queryForList("select iduser,login,permision from user where login=? and password=?", new Object[]{login, password});

                String json = new Gson().toJson(loginArr);
                return new ResponseEntity<String>(json,HttpStatus.OK);
            } catch (IncorrectResultSizeDataAccessException e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
