package com.company.infix.controler;

import com.company.infix.dao.PricingDao;
import com.company.infix.dto.PricingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PricingAPIController {
    @Autowired
    PricingDao pricingDao;

    @CrossOrigin
    @RequestMapping(value = "/add-pricing", method = RequestMethod.POST)
    public ResponseEntity<String> addPricing(@RequestBody PricingDto pricingDto){
        return pricingDao.testAddPricing(pricingDto);
    }

    @CrossOrigin
    @RequestMapping(value = "/update-pricing/{id}",method = RequestMethod.PUT)
    public ResponseEntity<String> updatePricing(@RequestBody PricingDto pricingDto,@PathVariable("id") String id){
        return pricingDao.testUpdatePricing(pricingDto,id);
    }

    @CrossOrigin
    @RequestMapping(value = "/show-pricing",method = RequestMethod.GET)
    public String showPricing(){
        return pricingDao.testShowPricing();
    }

    @CrossOrigin
    @RequestMapping(value = "/show-pricing/{login}",method = RequestMethod.GET)
    public String showUserPricing(@PathVariable("login") String login){
        return pricingDao.testShowUserPricing(login);
    }
}
