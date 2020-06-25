package com.company.infix.service.impl;

import com.company.infix.dto.CarDto;
import com.company.infix.service.CarList;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class CarListImpl implements CarList {
    @Autowired
    JdbcTemplate jdbc;

    public String getOwnedCars(String login) {
        ArrayList<CarDto> newList = jdbc.query("SELECT idcar,iduser,marka,vin FROM car " +
                        "INNER JOIN user USING (iduser) WHERE login=?",
                new Object[]{login}, new ResultSetExtractor<ArrayList<CarDto>>() {
                    @Override
                    public ArrayList<CarDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        ArrayList<CarDto> newList = new ArrayList<CarDto>();
                        while(rs.next()){
                            CarDto newCar = new CarDto();
                            newCar.setIdCar(rs.getString("idcar"));
                            newCar.setIdUser(rs.getString("iduser"));
                            newCar.setVin(rs.getString("vin"));
                            newCar.setMarka(rs.getString("marka"));

                            newList.add(newCar);
                        }
                        return newList;
                    }
                });
        String json = new Gson().toJson(newList);
        return json;
    }
}
