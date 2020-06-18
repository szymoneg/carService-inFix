package com.company.infix.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelectData {
    @Autowired
    JdbcTemplate jdbc;
    @RequestMapping("/insert")
    public String index(){
        jdbc.execute("insert into cars(idcars,car_name,car_color)values(1,'javatpoint','java@javatpoint.com')");
        return "data inserted Successfully";
    }

    @RequestMapping("/select")
    public int select_index() {
        return jdbc.queryForObject("select count(*) from car",Integer.class);
    }
}
