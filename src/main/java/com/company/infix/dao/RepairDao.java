package com.company.infix.dao;

import com.company.infix.dto.CarDto;
import com.company.infix.dto.RepairDto;
import com.company.infix.dto.ReservationDto;
import com.company.infix.dto.UserDto;
import com.company.infix.service.CheckValues;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RepairDao {
    @Autowired
    JdbcTemplate jbdc;
    @Autowired
    CheckValues chkVal;

    public String testShowWorker() {
        ArrayList<UserDto> newList = jbdc.query("SELECT name,surname FROM user WHERE permision='1'", rs -> {
            ArrayList<UserDto> newList1 = new ArrayList<UserDto>();
            while (rs.next()) {
                UserDto userDto = new UserDto();
                userDto.setName(rs.getString("name"));
                userDto.setSurname(rs.getString("surname"));
                newList1.add(userDto);
            }
            return newList1;
        });
        String json = new Gson().toJson(newList);
        return json;
    }

    //TODO dodawanie po logine
    public ResponseEntity<Void> testAddRepair(RepairDto repairDto,String login) {
        String vin = repairDto.getVin();
        String status = repairDto.getStatus();
        String per = jbdc.queryForObject("SELECT permision FROM user WHERE login=?", new Object[]{login}, String.class);
        if (chkVal.checkVIN(vin) && chkVal.checkStatus(status) && per.equals("1")) {
            String id_user = jbdc.queryForObject("SELECT iduser FROM user WHERE login=?", new Object[]{login}, String.class);
            jbdc.update("INSERT INTO repair(iduser,status,vin) values (?,?,?)",
                    id_user, status, vin);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //TODO wyswietlanie kazdej naprawy
    public String testSearchRepair(String vin) {
        ArrayList<Pair<UserDto, RepairDto>> repairList = jbdc.query("select r.status,name,surname FROM user inner join repair r using(iduser) where vin=?", new Object[]{vin}, rs -> {
            ArrayList<Pair<UserDto, RepairDto>> repairListTemp = new ArrayList<>();
            while (rs.next()) {
                RepairDto repairDto = new RepairDto();
                UserDto userDto = new UserDto();
                repairDto.setStatus(rs.getString("status"));
                userDto.setName(rs.getString("name"));
                userDto.setSurname(rs.getString("surname"));
                repairListTemp.add(Pair.of(userDto, repairDto));
            }
            return repairListTemp;
        });
        String json = new Gson().toJson(repairList.stream().map(e -> {
            return Stream.of(
                    new AbstractMap.SimpleEntry<>("name", e.getFirst().getName()),
                    new AbstractMap.SimpleEntry<>("surname", e.getFirst().getSurname()),
                    new AbstractMap.SimpleEntry<>("status", e.getSecond().getStatus())
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }).collect(Collectors.toList()));
        return json;
    }

    public String testShowAllRepair(){
        ArrayList<Pair<UserDto, RepairDto>> reservList = jbdc.query(
                "select u.name,u.surname,u.tele_no,u.email,r.vin,r.status FROM user u inner join repair r using(iduser)", rs -> {
                    ArrayList<Pair<UserDto, RepairDto>> reservListTemp = new ArrayList<>();
                    while (rs.next()) {
                        UserDto userDto = new UserDto();
                        RepairDto repairDto = new RepairDto();
                        userDto.setName(rs.getString("name"));
                        userDto.setSurname(rs.getString("surname"));
                        userDto.setTelephoneNumber(rs.getString("tele_no"));
                        userDto.setEmail(rs.getString("email"));
                        repairDto.setVin(rs.getString("vin"));
                        repairDto.setStatus(rs.getString("status"));
                        reservListTemp.add(Pair.of(userDto, repairDto));
                    }
                    return reservListTemp;
                });
        String json = new Gson().toJson(reservList.stream().map(e -> {
            return Stream.of(
                    new AbstractMap.SimpleEntry<>("name", e.getFirst().getName()),
                    new AbstractMap.SimpleEntry<>("surname", e.getFirst().getSurname()),
                    new AbstractMap.SimpleEntry<>("tele_no", e.getFirst().getTelephoneNumber()),
                    new AbstractMap.SimpleEntry<>("email", e.getFirst().getEmail()),
                    new AbstractMap.SimpleEntry<>("vin", e.getSecond().getVin()),
                    new AbstractMap.SimpleEntry<>("status", e.getSecond().getStatus())
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }).collect(Collectors.toList()));
        return json;
    }

    public String testShowUserRepair(String login){
        ArrayList<Pair<UserDto, RepairDto>> reservList = jbdc.query(
                "select u.name,u.surname,u.tele_no,u.email,r.vin,r.status FROM user u inner join repair r using(iduser) WHERE u.login=?",new Object[]{login}, rs -> {
                    ArrayList<Pair<UserDto, RepairDto>> reservListTemp = new ArrayList<>();
                    while (rs.next()) {
                        UserDto userDto = new UserDto();
                        RepairDto repairDto = new RepairDto();
                        userDto.setName(rs.getString("name"));
                        userDto.setSurname(rs.getString("surname"));
                        userDto.setTelephoneNumber(rs.getString("tele_no"));
                        userDto.setEmail(rs.getString("email"));
                        repairDto.setVin(rs.getString("vin"));
                        repairDto.setStatus(rs.getString("status"));
                        reservListTemp.add(Pair.of(userDto, repairDto));
                    }
                    return reservListTemp;
                });
        String json = new Gson().toJson(reservList.stream().map(e -> {
            return Stream.of(
                    new AbstractMap.SimpleEntry<>("name", e.getFirst().getName()),
                    new AbstractMap.SimpleEntry<>("surname", e.getFirst().getSurname()),
                    new AbstractMap.SimpleEntry<>("tele_no", e.getFirst().getTelephoneNumber()),
                    new AbstractMap.SimpleEntry<>("email", e.getFirst().getEmail()),
                    new AbstractMap.SimpleEntry<>("vin", e.getSecond().getVin()),
                    new AbstractMap.SimpleEntry<>("status", e.getSecond().getStatus())
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }).collect(Collectors.toList()));
        return json;
    }

    //TODO wycena usługi
    public ResponseEntity<Void> testChangeStatus(RepairDto repairDto, String flag) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String vin = repairDto.getVin();
        String status = repairDto.getStatus();
        if (chkVal.checkVIN(vin) && chkVal.checkStatus(status)) {
            jbdc.execute("UPDATE repair SET status='" + status + "' WHERE vin=" + vin);
            if (flag.equals("1")) {
                jbdc.execute("UPDATE reservation SET date_finish='" + formatter.format(date) + "', status='ukonczono' WHERE vin=" + repairDto.getVin());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
