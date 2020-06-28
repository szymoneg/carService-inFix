package com.company.infix.dao;


import com.company.infix.dto.UserDto;
import com.company.infix.service.CheckValues;
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
public class EditUserDao {

    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    private HashPassword hashPassword;
    @Autowired
    SpringJdbcConfig conn;
    @Autowired
    CheckValues chkVal;

    public ResponseEntity<String> sendEditData(UserDto edit) throws SQLException {
        String mail = edit.getEmail();
        String phoneNumber = edit.getTelephoneNumber();
        String login = edit.getLogin();
        String permision = edit.getPermision();
        if(permision!=null){
            PreparedStatement st = conn.mysqlDataSource().getConnection().prepareStatement("UPDATE user SET permision=? WHERE login=?");
            st.setString(1, permision);
            st.setString(2, login);
            st.executeUpdate();
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            if (chkVal.checkLogin(login) && chkVal.checkPhoneNumber(phoneNumber) && chkVal.checkEmail(mail)) {
                try {
                    PreparedStatement st;
                    if (!(edit.getPassword() == null)) {
                        st = conn.mysqlDataSource().getConnection().prepareStatement("UPDATE user SET password=?,email=?,tele_no=? WHERE login=?");
                        st.setString(1, hashPassword.HashMethod(edit.getPassword()));
                        st.setString(2, mail);
                        st.setString(3, phoneNumber);
                        st.setString(4, login);
                    } else {
                        st = conn.mysqlDataSource().getConnection().prepareStatement("UPDATE user SET email=?,tele_no=? WHERE login=?");
                        st.setString(1, mail);
                        st.setString(2, phoneNumber);
                        st.setString(3, login);
                    }
                    st.executeUpdate();
                    return new ResponseEntity<>(HttpStatus.OK);
                } catch (SQLException | NoSuchAlgorithmException e) {
                    return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
    }

    public String testGetDataUser(String login) {
        ArrayList<UserDto> newList = jdbc.query("SELECT email, tele_no FROM user WHERE login=?", new Object[]{login}, new ResultSetExtractor<ArrayList<UserDto>>() {
            @Override
            public ArrayList<UserDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<UserDto> newList = new ArrayList<UserDto>();
                while (rs.next()) {
                    UserDto userEdit = new UserDto();
                    userEdit.setEmail(rs.getString("email"));
                    userEdit.setTelephoneNumber(rs.getString("tele_no"));
                    newList.add(userEdit);
                }
                return newList;
            }
        });
        String json = new Gson().toJson(newList);
        return json;
    }

    public String testShowAllUsers(){
        ArrayList<UserDto> newList = jdbc.query("SELECT * FROM user", rs -> {
            ArrayList<UserDto> newList1 = new ArrayList<UserDto>();
            while (rs.next()) {
                UserDto userEdit = new UserDto();
                userEdit.setIdUser(rs.getString("iduser"));
                userEdit.setName(rs.getString("name"));
                userEdit.setSurname(rs.getString("surname"));
                userEdit.setEmail(rs.getString("email"));
                userEdit.setDriversLicense(rs.getString("drivers_license"));
                userEdit.setLogin(rs.getString("login"));
                userEdit.setTelephoneNumber(rs.getString("tele_no"));
                userEdit.setPermision(rs.getString("permision"));
                userEdit.setPesel(rs.getString("pesel"));
                newList1.add(userEdit);
            }
            return newList1;
        });
        String json = new Gson().toJson(newList);
        return json;
    }
}
