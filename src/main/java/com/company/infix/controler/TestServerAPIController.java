package com.company.infix.controler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/infix")
public class TestServerAPIController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServerAPIController.class);

    @CrossOrigin
    @GetMapping(value = "/test")
    public ResponseEntity<Map<String, String>> serverTest() {
        LOGGER.info("--- server is running");

        Map<String, String> serverTestMessage = new HashMap<>();
        serverTestMessage.put("server-status", "RUN");

        return new ResponseEntity<>(serverTestMessage, HttpStatus.OK);
    }
}
