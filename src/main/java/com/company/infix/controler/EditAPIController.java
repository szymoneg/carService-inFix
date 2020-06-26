package com.company.infix.controler;

import com.company.infix.dao.EditUserDao;
import com.company.infix.dto.UserDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class EditAPIController {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    EditUserDao editUserDao;

    @CrossOrigin
    @RequestMapping(value = "/edit/{login}", method = RequestMethod.GET)
    public String getDataUser(@PathVariable("login") String login){
        return editUserDao.testGetDataUser(login);
    }

    @CrossOrigin
    @RequestMapping(value = "/edit-send",method = RequestMethod.PUT)
    public ResponseEntity<String> sendEditData(@RequestBody UserDto edit) throws SQLException{
        return editUserDao.sendEditData(edit);
    }
}
