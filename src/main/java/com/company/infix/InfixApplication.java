package com.company.infix;

import com.company.infix.service.CheckValues;
import com.company.infix.service.impl.CheckValuesImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class InfixApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfixApplication.class, args);
    }
}
