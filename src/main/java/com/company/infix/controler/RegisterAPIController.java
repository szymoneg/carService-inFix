package com.company.infix.controler;

import com.company.infix.dto.UserRegisterDto;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class RegisterAPIController {
    @Autowired
    JdbcTemplate jdbc;
    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> testRegister(@RequestBody UserRegisterDto db) throws NoSuchAlgorithmException {
        try {
            jdbc.queryForObject("SELECT login FROM user WHERE login=?",new Object[]{db.getLogin()}, String.class);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }catch (IncorrectResultSizeDataAccessException e){
            jdbc.update("INSERT INTO user(permision,name,surname,pesel,drivers_license,password,login) values (?,?,?,?,?,?,?)",
                    db.getPermision(),db.getName(),db.getSurname(),db.getPesel(),db.getDriversLicense(),HashMethod(db.getPassword()),db.getLogin());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public String HashMethod(String pass) throws NoSuchAlgorithmException {
        String passToHash = pass;
        String generatedPass = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(passToHash.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<bytes.length; i++){
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPass = sb.toString();

        return generatedPass;
    }
}
