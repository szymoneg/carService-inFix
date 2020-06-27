package com.company.infix.dao;

import com.company.infix.dto.UserDto;
import com.company.infix.service.CheckValues;
import com.company.infix.service.HashPassword;
import com.company.infix.service.SendMails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;

@Component
public class RegisterDao {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    private HashPassword hashPassword;
    @Autowired
    private CheckValues chkVal;
    @Autowired
    private SendMails sendMails;

    public ResponseEntity<Integer> testRegister(UserDto db) throws NoSuchAlgorithmException, MessagingException {
        String name = db.getName();
        String surname = db.getSurname();
        String psl = db.getPesel();
        String license = db.getDriversLicense();
        String mail = db.getEmail();
        String phoneNumber = db.getTelephoneNumber();
        String login = db.getLogin();
        if (chkVal.checkNameAndSurname(name, surname) && chkVal.checkPESEL(psl) && chkVal.checkLicense(license) && chkVal.checkEmail(mail) && chkVal.checkPhoneNumber(phoneNumber) && chkVal.checkLogin(login)) {
            try {
                jdbc.queryForObject("SELECT login FROM user WHERE login=?", new Object[]{login}, String.class);
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (IncorrectResultSizeDataAccessException e) {
                sendMails.sendMail(mail,
                        "witamy na platformie inFix!",
                        "<b>Witaj, " + login + "!</b><br>", true);
                jdbc.update("INSERT INTO user(permision,name,surname,pesel,drivers_license,password,login,email,tele_no) values ('0',?,?,?,?,?,?,?,?)",
                         name, surname, psl, license, hashPassword.HashMethod(db.getPassword()), login,
                        mail, phoneNumber);
                return new ResponseEntity<>(1,HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Void> testAddWorker(UserDto userDto) throws NoSuchAlgorithmException {
        String name = userDto.getName();
        String surname = userDto.getSurname();
        String psl = userDto.getPesel();
        String license = userDto.getDriversLicense();
        String mail = userDto.getEmail();
        String phoneNumber = userDto.getTelephoneNumber();
        String login = userDto.getLogin();
        if (chkVal.checkNameAndSurname(name, surname) && chkVal.checkPESEL(psl) && chkVal.checkLicense(license) && chkVal.checkEmail(mail) && chkVal.checkPhoneNumber(phoneNumber) && chkVal.checkLogin(login)) {
            try {
                jdbc.queryForObject("SELECT login FROM user WHERE login=?", new Object[]{login}, String.class);
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (IncorrectResultSizeDataAccessException e) {
                jdbc.update("INSERT INTO user(permision,name,surname,pesel,drivers_license,password,login,email,tele_no) values ('1',?,?,?,?,?,?,?,?)",
                        name, surname, psl, license, hashPassword.HashMethod(userDto.getPassword()), login,
                        mail, phoneNumber);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
