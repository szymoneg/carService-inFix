package com.company.infix.dao;

import com.company.infix.dto.CarDto;
import com.company.infix.dto.RepairDto;
import com.company.infix.dto.ReservationDto;
import com.company.infix.dto.UserDto;
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
    JdbcTemplate jbdc;

    public String testShowWorker(){
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

    public ResponseEntity<Void> testAddRepair(RepairDto repairDto){
        jbdc.update("INSERT INTO repair(iduser,status,vin) values (?,?,?)",
                repairDto.getIdUser(),repairDto.getStatus(),repairDto.getVin());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String testSearchReapair(String vin){
        ArrayList<Pair<UserDto,RepairDto>> repairList = jbdc.query("select r.status,name,surname FROM user inner join repair r using(iduser) where vin=?",new Object[]{vin} , rs -> {
            ArrayList<Pair<UserDto,RepairDto>> repairListTemp = new ArrayList<>();
            while (rs.next()) {
                RepairDto repairDto = new RepairDto();
                UserDto userDto = new UserDto();
                repairDto.setStatus(rs.getString("status"));
                userDto.setName(rs.getString("name"));
                userDto.setSurname(rs.getString("surname"));
                repairListTemp.add(Pair.of(userDto,repairDto));
            }
            return repairListTemp;
        });
        String json = new Gson().toJson(repairList.stream().map(e-> {
            return Stream.of(
            new AbstractMap.SimpleEntry<>("name",e.getFirst().getName()),
            new AbstractMap.SimpleEntry<>("surname",e.getFirst().getSurname()),
            new AbstractMap.SimpleEntry<>("status",e.getSecond().getStatus())
            ).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));}).collect(Collectors.toList()));
        return json;
    }

    public ResponseEntity<Void> testChangeStatus(RepairDto repairDto,String flag){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        jbdc.execute("UPDATE repair SET status='"+repairDto.getStatus()+"' WHERE vin="+repairDto.getVin());
        if (flag.equals("1")) {
            jbdc.execute("UPDATE reservation SET date_finish='" + formatter.format(date)+"', status='ukonczono' WHERE vin="+repairDto.getVin());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
