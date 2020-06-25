package com.company.infix.dao;


import com.company.infix.dto.UserDto;
import com.company.infix.service.HashPassword;
import com.company.infix.service.impl.HashPasswordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@RestController
public class EditUserDao{

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    private HashPassword hashPassword;
    SpringJdbcConfig conn = new SpringJdbcConfig();

    @CrossOrigin
    @RequestMapping(value = "/edit-send",method = RequestMethod.POST)
    public ResponseEntity<String> sendEditData(@RequestBody UserDto edit) throws SQLException {
        try {
            PreparedStatement st = conn.mysqlDataSource().getConnection().prepareStatement("UPDATE user SET password=?,email=?,tele_no=? WHERE login=?");
            st.setString(1, hashPassword.HashMethod(edit.getPassword()));
            st.setString(2, edit.getEmail());
            st.setString(3, edit.getTelephoneNumber());
            st.setString(4, edit.getLogin());
            st.executeUpdate();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SQLException | NoSuchAlgorithmException e) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
