package com.company.infix.controler;

import com.company.infix.dao.ManageDao;
import com.company.infix.dao.RegisterDao;
import com.company.infix.dao.RepairDao;
import com.company.infix.dto.RepairDto;
import com.company.infix.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class AdministrationAPIController {
    @Autowired
    ManageDao manageDao;
    @Autowired
    RepairDao repairDao;
    @Autowired
    RegisterDao registerDao;

    @CrossOrigin
    @RequestMapping(value = "/show-pending/{login}",method = RequestMethod.GET)
    public String testShowPendingRes(@PathVariable("login") String login){
        return manageDao.showPendingRes(login);
    }

    @CrossOrigin
    @RequestMapping(value = "/edit-reservation/{idres}",method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Void> editReservation(@PathVariable("idres") String idres,
                                         @RequestParam String status1,
                                         @RequestParam String date){
        return manageDao.editReservation(idres,status1,date);
    }

    //--------------------------------------------------------------------

    @CrossOrigin
    @RequestMapping(value = "/show-workers",method = RequestMethod.GET)
    public String testShowWorker(){
        return repairDao.testShowWorker();
    }

    @CrossOrigin
    @RequestMapping(value = "/add-repair",method = RequestMethod.POST)
    public ResponseEntity<Void> addReapir(@RequestBody RepairDto repairDto){
        return repairDao.testAddRepair(repairDto);
    }

    @CrossOrigin
    @RequestMapping(value = "/change-status/{flag}",method = RequestMethod.PUT)
    public ResponseEntity<Void> changeStatus(@RequestBody RepairDto repairDto,@PathVariable("flag") String flag){
        return repairDao.testChangeStatus(repairDto,flag);
    }

    @CrossOrigin
    @RequestMapping(value = "/add-worker",method = RequestMethod.POST)
    public ResponseEntity<Void> addWorker(@RequestBody UserDto userDto) throws NoSuchAlgorithmException {
        return registerDao.testAddWorker(userDto);
    }
}
