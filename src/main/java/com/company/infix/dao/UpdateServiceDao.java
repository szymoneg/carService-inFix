package com.company.infix.dao;

import com.company.infix.dto.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;

public class UpdateServiceDao {
    @Autowired
    JdbcTemplate jdbc;

    public ResponseEntity<Void> updateTest(@RequestBody CarDto carDto){


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
