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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;

public class RegisterDao {
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    private HashPassword hashPassword;
    @Autowired
    private CheckValues checkValues;
    @Autowired
    private SendMails sendMails;

    public ResponseEntity<Void> testRegister(UserDto db) throws NoSuchAlgorithmException, MessagingException {
        //Walidacja znaków specjalnych
        String name = db.getName();
        String surname = db.getSurname();
        if (!checkValues.checkNameAndSurname(name, surname)) {
            try {
                jdbc.queryForObject("SELECT login FROM user WHERE login=?", new Object[]{db.getLogin()}, String.class);
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (IncorrectResultSizeDataAccessException e) {
                sendMails.sendMail(db.getEmail(),
                        "witamy na platformie inFix!",
                        "<b>Witaj, " + db.getLogin() + "!</b><br>", true);
                jdbc.update("INSERT INTO user(permision,name,surname,pesel,drivers_license,password,login,email,tele_no) values (?,?,?,?,?,?,?,?,?)",
                        db.getPermision(), db.getName(), db.getSurname(), db.getPesel(), db.getDriversLicense(), hashPassword.HashMethod(db.getPassword()), db.getLogin(),
                        db.getEmail(), db.getTelephoneNumber());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
