package com.company.infix.dao;

import com.company.infix.dto.CarDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class CarsDao {
    @Autowired
    JdbcTemplate jdbc;

    //Metoda zwraca json listy samochodów usera o konkretynm id
    @CrossOrigin
    @RequestMapping(value = "/owned-cars/{iduser}",method = RequestMethod.GET)
    public String getCars(@PathVariable("iduser") String iduser) throws SQLException {
        ArrayList<CarDto> newList = jdbc.query("SELECT marka, model FROM car WHERE iduser=?",new Object[]{iduser}, new ResultSetExtractor<ArrayList<CarDto>>() {
            @Override
            public ArrayList<CarDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<CarDto> newList = new ArrayList<CarDto>();
                while(rs.next()){
                    CarDto newCar = new CarDto();
                    newCar.setEngineCapacity(rs.getString("engine_capacity"));
                    newCar.setVin(rs.getString("vin"));
                    newCar.setYearOf(rs.getString("year_of"));
                    newCar.setMarka(rs.getString("marka"));
                    newCar.setModel(rs.getString("model"));

                    newList.add(newCar);
                }
                return newList;
            }
        });

        String json = new Gson().toJson(newList);
        return json;
    }
}
