package com.company.infix.controler;

import com.company.infix.dto.UserDto;
import com.google.gson.Gson;
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
    @Autowired
    JdbcTemplate jdbc;

    @CrossOrigin
    @RequestMapping(value = "/edit/{login}", method = RequestMethod.GET)
    public String getDataUser(@PathVariable("login") String login){
        ArrayList<UserDto> newList = jdbc.query("SELECT name,surname FROM user WHERE login=?",new Object[]{login}, new ResultSetExtractor<ArrayList<UserDto>>() {
            @Override
            public ArrayList<UserDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<UserDto> newList = new ArrayList<UserDto>();
                while(rs.next()){
                    UserDto userEdit = new UserDto();
                    userEdit.setName(rs.getString("name"));
                    userEdit.setSurname(rs.getString("surname"));

                    newList.add(userEdit);
                }
                return newList;
            }
        });
        String json = new Gson().toJson(newList);
        return json;
    }
}
