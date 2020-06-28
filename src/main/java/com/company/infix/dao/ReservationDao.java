package com.company.infix.dao;

import com.company.infix.dto.CarDto;
import com.company.infix.dto.ReservationDto;
import com.company.infix.service.CheckValues;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReservationDao {
    @Autowired
    JdbcTemplate jbdc;
    @Autowired
    CheckValues chkVal;

    public ResponseEntity<String> testReservation(ReservationDto resDb) {
        String desc = resDb.getDescription();
        if (chkVal.checkDesc(desc)) {
            jbdc.update("INSERT INTO reservation(iduser,idcar,date_start,date_finish,status,description) VALUES (?,?,?,?,?,?)",
                    resDb.getIdUser(), resDb.getIdCar(), resDb.getDateStart(), null, "1", desc);
            return new ResponseEntity<>("1",HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    public String testShowOldReservation(String login) {
        ArrayList<Pair<CarDto, ReservationDto>> reservList = jbdc.query(
                "select r.date_start,r.date_finish,c.marka,c.model,c.vin FROM reservation r inner join car c using(idcar) inner join user u where u.login=?", new Object[]{login}, rs -> {
                    ArrayList<Pair<CarDto, ReservationDto>> reservListTemp = new ArrayList<>();
                    while (rs.next()) {
                        ReservationDto reservationDto = new ReservationDto();
                        CarDto carDto = new CarDto();
                        carDto.setMarka(rs.getString("marka"));
                        carDto.setModel(rs.getString("model"));
                        carDto.setVin(rs.getString("vin"));
                        reservationDto.setDateFinish(rs.getString("date_finish"));
                        reservationDto.setDateStart(rs.getString("date_start"));
                        reservListTemp.add(Pair.of(carDto, reservationDto));
                    }
                    return reservListTemp;
                });
        String json = new Gson().toJson(reservList.stream().map(e -> {
            return Stream.of(
                    new AbstractMap.SimpleEntry<>("marka", e.getFirst().getMarka()),
                    new AbstractMap.SimpleEntry<>("model", e.getFirst().getModel()),
                    new AbstractMap.SimpleEntry<>("vin", e.getFirst().getVin()),
                    new AbstractMap.SimpleEntry<>("date_start", e.getSecond().getDateStart()),
                    new AbstractMap.SimpleEntry<>("date_finish", e.getSecond().getDateFinish())
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }).collect(Collectors.toList()));
        return json;
    }
}
