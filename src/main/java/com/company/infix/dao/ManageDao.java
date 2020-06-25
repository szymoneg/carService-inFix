package com.company.infix.dao;

import com.company.infix.dto.ReservationDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class ManageDao {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    SpringJdbcConfig conn;

    @CrossOrigin
    @RequestMapping(value = "/show-pending",method = RequestMethod.GET)
    public String showPendingRes(){
        ArrayList<ReservationDto> newList = jdbc.query("SELECT * FROM reservatiion", new ResultSetExtractor<ArrayList<ReservationDto>>() {
            @Override
            public ArrayList<ReservationDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<ReservationDto> newList = new ArrayList<ReservationDto>();
                while(rs.next()){
                    ReservationDto reservationDto = new ReservationDto();
                    reservationDto.setIdReservation(rs.getString("idresevation"));
                    reservationDto.setIdCar(rs.getString("idcar"));
                    reservationDto.setIdUser(rs.getString("iduser"));
                    reservationDto.setDateFinish(rs.getString("date_start"));
                    reservationDto.setDateStart(rs.getString("date_finish"));
                    reservationDto.setStatus(rs.getString("status"));
                    reservationDto.setDescription(rs.getString("description"));
                    newList.add(reservationDto);
                }
                return newList;
            }
        });
        String json = new Gson().toJson(newList);
        return json;
    }

    @CrossOrigin
    @RequestMapping(value = "/edit-resevation/{idres}",method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Void> editReservation(@PathVariable("idres") String idres,
                                                              @RequestParam String status1,
                                                              @RequestParam String date){
        jdbc.execute("UPDATE reservation SET status="+status1+",date_start="+date+" WHERE idreservation="+idres);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
