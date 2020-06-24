package com.company.infix.controler;

import com.company.infix.dto.UserRegisterDto;
import com.company.infix.service.CheckValue;
import com.company.infix.service.HashPassword;
import com.company.infix.service.SendMails;
import com.company.infix.service.impl.CheckValueImpl;
import com.company.infix.service.impl.HashPasswordImpl;
import com.company.infix.service.impl.SendMailsImpl;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class RegisterAPIController {
    @Autowired
    JdbcTemplate jdbc;
    private HashPassword hashPassword = new HashPasswordImpl();
    private CheckValue checkValue = new CheckValueImpl();

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> testRegister(@RequestBody UserRegisterDto db) throws NoSuchAlgorithmException, MessagingException {
        //Walidacja znaków specjalnych
        String name = db.getName();
        if(!checkValue.check(name)) {
            try {
                jdbc.queryForObject("SELECT login FROM user WHERE login=?", new Object[]{db.getLogin()}, String.class);
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (IncorrectResultSizeDataAccessException e) {
                jdbc.update("INSERT INTO user(permision,name,surname,pesel,drivers_license,password,login,email,tele_no) values (?,?,?,?,?,?,?,?,?)",
                        db.getPermision(), db.getName(), db.getSurname(), db.getPesel(), db.getDriversLicense(), hashPassword.HashMethod(db.getPassword()), db.getLogin(),
                        db.getEmail(),db.getTelephoneNumber());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
