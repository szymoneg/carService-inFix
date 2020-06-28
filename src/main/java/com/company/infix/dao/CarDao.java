package com.company.infix.dao;

import com.company.infix.dto.CarDto;
import com.company.infix.dto.UserDto;
import com.company.infix.service.CheckValues;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CarDao {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    CheckValues chkVal;

    //TODO login w linku
    public ResponseEntity<String> testAddCar(CarDto carDto,String login) {
        String mark = carDto.getMarka();
        String model = carDto.getModel();
        String cap = carDto.getEngineCapacity();
        String vin = carDto.getVin();
        String yr = carDto.getYearOf(); //yearofcar
        if (chkVal.checkCar(mark) && chkVal.checkCar(model) && chkVal.checkCapacity(cap) && chkVal.checkVIN(vin)) {
            try {
                jdbc.queryForObject("SELECT vin FROM car WHERE vin=?", new Object[]{vin}, String.class);
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (IncorrectResultSizeDataAccessException e) {
                String id_user = jdbc.queryForObject("SELECT iduser FROM user WHERE login=?", new Object[]{login}, String.class);
                jdbc.update("INSERT INTO car(marka,model,engine_capacity,vin,year_of,iduser) VALUES (?,?,?,?,?,?)",
                        mark, model, cap, vin, yr, id_user);

                return new ResponseEntity<String>("1",HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    public String testGetCars(String login) throws SQLException {
        ArrayList<CarDto> newList = jdbc.query("SELECT marka, model,engine_capacity,vin,year_of FROM car " +
                        "INNER JOIN user USING (iduser) WHERE login=?",
                new Object[]{login}, new ResultSetExtractor<ArrayList<CarDto>>() {
                    @Override
                    public ArrayList<CarDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        ArrayList<CarDto> newList = new ArrayList<CarDto>();
                        while (rs.next()) {
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
    public String testGetOwnedCars(String login) {
        ArrayList<CarDto> newList = jdbc.query("SELECT idcar,iduser,marka,vin FROM car " +
                        "INNER JOIN user USING (iduser) WHERE login=?",
                new Object[]{login}, new ResultSetExtractor<ArrayList<CarDto>>() {
                    @Override
                    public ArrayList<CarDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        ArrayList<CarDto> newList = new ArrayList<CarDto>();
                        while (rs.next()) {
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

    public String testGetAllCars() {
        ArrayList<Pair<UserDto, CarDto>> reservList = jdbc.query(
                "select u.name,u.surname,u.tele_no,u.email,c.* FROM user u inner join car c using(iduser)", rs -> {
                    ArrayList<Pair<UserDto, CarDto>> carListTemp = new ArrayList<>();
                    while (rs.next()) {
                        UserDto userDto = new UserDto();
                        CarDto carDto = new CarDto();
                        userDto.setName(rs.getString("name"));
                        userDto.setSurname(rs.getString("surname"));
                        userDto.setTelephoneNumber(rs.getString("tele_no"));
                        userDto.setEmail(rs.getString("email"));
                        carDto.setEngineCapacity(rs.getString("engine_capacity"));
                        carDto.setYearOf(rs.getString("year_of"));
                        carDto.setMarka(rs.getString("marka"));
                        carDto.setModel(rs.getString("model"));
                        carDto.setVin(rs.getString("vin"));
                        carListTemp.add(Pair.of(userDto, carDto));
                    }
                    return carListTemp;
                });
        String json = new Gson().toJson(reservList.stream().map(e -> {
            return Stream.of(
                    new AbstractMap.SimpleEntry<>("name", e.getFirst().getName()),
                    new AbstractMap.SimpleEntry<>("surname", e.getFirst().getSurname()),
                    new AbstractMap.SimpleEntry<>("tele_no", e.getFirst().getTelephoneNumber()),
                    new AbstractMap.SimpleEntry<>("email", e.getFirst().getEmail()),
                    new AbstractMap.SimpleEntry<>("engine_capacity", e.getSecond().getEngineCapacity()),
                    new AbstractMap.SimpleEntry<>("year_of", e.getSecond().getYearOf()),
                    new AbstractMap.SimpleEntry<>("marka", e.getSecond().getMarka()),
                    new AbstractMap.SimpleEntry<>("model", e.getSecond().getModel()),
                    new AbstractMap.SimpleEntry<>("vin", e.getSecond().getVin())
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }).collect(Collectors.toList()));
        return json;
    }
}
