package com.company.infix.controler;

import com.company.infix.dto.CarDto;
import com.company.infix.dto.UserRegisterDto;
import com.google.gson.Gson;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class EditAPIController {
    public String logintest;
    @Autowired
    JdbcTemplate jdbc;

    @CrossOrigin
    @RequestMapping(value = "/edit/{login}", method = RequestMethod.GET)
    public String getDataUser(@PathVariable("login") String login){
        ArrayList<UserRegisterDto> newList = jdbc.query("SELECT name,surname FROM user WHERE login=?",new Object[]{login}, new ResultSetExtractor<ArrayList<UserRegisterDto>>() {
            @Override
            public ArrayList<UserRegisterDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<UserRegisterDto> newList = new ArrayList<UserRegisterDto>();
                while(rs.next()){
                    UserRegisterDto userEdit = new UserRegisterDto();
                    userEdit.setName(rs.getString("name"));
                    userEdit.setSurname(rs.getString("surname"));

                    newList.add(userEdit);
                }
                return newList;
            }
        });
        logintest = login;
        String json = new Gson().toJson(newList);
        return json;
    }
}
