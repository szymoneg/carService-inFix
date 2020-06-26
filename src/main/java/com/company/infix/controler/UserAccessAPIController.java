package com.company.infix.controler;

import com.company.infix.dao.RegisterDao;
import com.company.infix.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserAccessAPIController {
    @Autowired
    RegisterDao registerDao;
    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> register(@RequestBody UserDto db) throws MessagingException, NoSuchAlgorithmException {
    return registerDao.testRegister(db);
    }
}
