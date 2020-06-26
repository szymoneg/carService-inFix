package com.company.infix.dao;


import com.company.infix.dto.UserDto;
import com.company.infix.service.HashPassword;
import com.company.infix.service.impl.HashPasswordImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@RestController
public class EditUserDao{

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    private HashPassword hashPassword;
    @Autowired
    SpringJdbcConfig conn;

    public ResponseEntity<String> sendEditData(UserDto edit) throws SQLException {
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

    public String testGetDataUser(String login){
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
