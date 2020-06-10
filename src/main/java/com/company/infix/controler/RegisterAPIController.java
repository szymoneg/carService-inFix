package com.company.infix.controler;

import com.company.infix.dto.UserRegisterDto;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//To bedzie klasa do rejestracji, ten format klasy to odczyt z JSON
//trzeba zrobic zapytanie INSERT
@RestController
public class RegisterAPIController {
    @Autowired
    JdbcTemplate jdbc;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> testRegister(@RequestBody UserRegisterDto db) throws NoSuchAlgorithmException {

        jdbc.update("INSERT INTO user(permision,name,surname,pesel,drivers_license,idcar,password) values (?,?,?,?,?,?,?)",
                db.getPermision(),db.getName(),db.getSurname(),db.getPesel(),db.getDriversLicense(),db.getIdCar(),HashMethod(db.getPass()));

        return new ResponseEntity<>(HttpStatus.OK);
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
        //System.out.println(generatedPass);

        return generatedPass;
    }
}
