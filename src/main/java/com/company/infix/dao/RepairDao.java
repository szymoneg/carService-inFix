package com.company.infix.dao;

import com.company.infix.dto.RepairDto;
import com.company.infix.dto.UserDto;
import com.company.infix.service.CheckValues;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RepairDao {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    CheckValues chkVal;

    public String testShowWorker() {
        ArrayList<UserDto> newList = jdbc.query("SELECT name,surname FROM user WHERE permision='1'", rs -> {
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

    public ResponseEntity<Void> testAddRepair(RepairDto repairDto,String login) {
        String vin = repairDto.getVin();
        String per = jdbc.queryForObject("SELECT permision FROM user WHERE login=?", new Object[]{login}, String.class);
        if (chkVal.checkVIN(vin) && per.equals("1")) {
            String id_user = jdbc.queryForObject("SELECT iduser FROM user WHERE login=?", new Object[]{login}, String.class);
            jdbc.update("INSERT INTO repair(iduser,status,vin,idcar) values (?,?,?,?)",
                    id_user, "W trakcie diagnozowania usterki", vin, repairDto.getIdCar());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //TODO wyswietlanie kazdej naprawy
    public String testSearchRepair(String vin) {
        ArrayList<Pair<UserDto, RepairDto>> repairList = jdbc.query("select r.status,name,surname FROM user inner join repair r using(iduser) where vin=?", new Object[]{vin}, rs -> {
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
        ArrayList arr;

        arr = jdbc.query("select u.name,u.surname,u.tele_no,u.email,r.vin,r.status,r.date_finish,c.model,c.marka FROM user u inner join repair r using(iduser) inner join car c using(iduser)",
                rs->{
                    ArrayList<HashMap<String,Object>> lis = new ArrayList<>();
                    while(rs.next()){
                        HashMap<String,Object> entry = new HashMap<>();
                        for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
                            entry.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                        }
                        lis.add(entry);
                    }
                    return lis;
                });
        return new Gson().toJson(arr);
    }

    public String testShowUserRepair(String login){
        ArrayList arr;

        arr = jdbc.query("select u.name,u.surname,u.tele_no,u.email,r.vin,r.status,r.date_finish,c.model,c.marka FROM user u inner join repair r using(iduser) inner join car c using(iduser) WHERE u.login=?",new Object[]{login},
        rs->{
            ArrayList<HashMap<String,Object>> lis = new ArrayList<>();
            while(rs.next()){
                HashMap<String,Object> entry = new HashMap<>();
                for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
                    entry.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                lis.add(entry);
            }
            return lis;
        });
        return new Gson().toJson(arr);
    }


    public ResponseEntity<Void> testChangeStatus(RepairDto repairDto, String flag) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String vin = repairDto.getVin();
        String status = repairDto.getStatus();
        String id_car = jdbc.queryForObject("SELECT c.idcar from car c inner join reservation using(idcar) where c.vin=? ",new Object[]{vin},String.class);
        if (chkVal.checkVIN(vin) && chkVal.checkStatus(status)) {
            jdbc.execute("UPDATE repair SET status='" + status + "' WHERE vin=" + vin);
            if (flag.equals("1")) {
                jdbc.execute("UPDATE reservation SET date_finish='" + formatter.format(date) + "' WHERE idcar=" + id_car);
                jdbc.execute("UPDATE repair SET date_finish='" + formatter.format(date) + "', status='ukonczono' WHERE vin=" + vin);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
