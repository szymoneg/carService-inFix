package com.company.infix.dao;


import com.company.infix.controler.EditAPIController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
public class EditUserDao{
    //kurwa tutaj troche nie wiem jak to rozergrac
    //narazie nie działa ale jutro juz napewno bedzie działac
    String test = new EditAPIController().logintest;
    @Autowired
    JdbcTemplate jdbc;
    SpringJdbcConfig conn = new SpringJdbcConfig();

    @CrossOrigin
    @RequestMapping(value = "/edit-send/{login}",method = RequestMethod.GET)
    public ResponseEntity<String> sendEditData(@PathVariable("login") String login1) throws SQLException {
        try {
            System.out.println(test);
            PreparedStatement st = conn.mysqlDataSource().getConnection().prepareStatement("UPDATE user SET name=? WHERE login=?");
            st.setString(1, test);
            st.setString(2, login1);
            st.executeUpdate();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
