package com.company.infix.controler;

import com.company.infix.dao.CarDao;
import com.company.infix.dao.ManageDao;
import com.company.infix.dao.RegisterDao;
import com.company.infix.dao.RepairDao;
import com.company.infix.dto.RepairDto;
import com.company.infix.dto.UserDto;
import com.company.infix.service.CarList;
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
    @Autowired
    CarList carList;


    @CrossOrigin
    @RequestMapping(value = "/show-pending",method = RequestMethod.GET)
    public String testShowPendingRes(){
        return manageDao.showPendingRes();
    }

    @CrossOrigin
    @RequestMapping(value = "/edit-reservation/{idres}",method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Void> editReservation(@PathVariable("idres") String idres,
                                         @RequestParam String status1){
        return manageDao.editReservation(idres,status1);
    }

    //--------------------------------------------------------------------

    @CrossOrigin
    @RequestMapping(value = "/show-workers",method = RequestMethod.GET)
    public String testShowWorker(){
        return repairDao.testShowWorker();
    }

    @CrossOrigin
    @RequestMapping(value = "/add-repair/{login}",method = RequestMethod.POST)
    public ResponseEntity<Void> addReapir(@RequestBody RepairDto repairDto,@PathVariable("login") String login){
        return repairDao.testAddRepair(repairDto,login);
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

    @CrossOrigin
    @RequestMapping(value = "/show-allrepair",method = RequestMethod.GET)
    public String showAllRepair(){
        return repairDao.testShowAllRepair();
    }

    @CrossOrigin
    @RequestMapping(value = "/show-allrepair/{login}",method = RequestMethod.GET)
    public String showAllRepair(@PathVariable("login") String login){
        return repairDao.testShowUserRepair(login);
    }

    @CrossOrigin
    @RequestMapping(value = "/show-allcars",method = RequestMethod.GET)
    public String showAllCars(){
        return carList.getAllCars();
    }

}
