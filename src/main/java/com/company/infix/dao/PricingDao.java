package com.company.infix.dao;

import com.company.infix.dto.PricingDto;
import com.company.infix.service.CheckValues;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PricingDao {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    CheckValues chkVal;

    public ResponseEntity<String> testAddPricing(PricingDto pricingDto){
        String description = pricingDto.getDescription();
        if (chkVal.checkDesc(description)) {
            jdbc.update("INSERT INTO pricing(description,price,iduser) values (?,?,?)",
                    description, null,pricingDto.getIdUser());
            return new ResponseEntity<String>("1",HttpStatus.OK);
        }
        return new ResponseEntity<String>("0", HttpStatus.CONFLICT);
    }

    public ResponseEntity<String> testUpdatePricing(PricingDto pricingDto,String id) {
        jdbc.execute("UPDATE pricing SET price='" + pricingDto.getPrice() + "' WHERE idpricing=" + id);
        return new ResponseEntity<>("1",HttpStatus.OK);
    }

    public String testShowPricing(){
        ArrayList pricingList = new ArrayList();
        pricingList = (ArrayList) jdbc.queryForList("SELECT * from pricing");
        String json = new Gson().toJson(pricingList);
        return json;
    }

    public String testShowUserPricing(String login){
        ArrayList pricingList = new ArrayList();
        pricingList = (ArrayList) jdbc.queryForList("SELECT p.* from pricing p inner join user u using(iduser) WHERE u.login=?",new Object[]{login});
        String json = new Gson().toJson(pricingList);
        return json;
    }

}
