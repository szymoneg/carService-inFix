package com.company.infix.service.impl;
import com.company.infix.service.CheckValues;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CheckValuesImpl implements CheckValues {

    @Override
    public boolean checkNameAndSurname(String name, String surname) {
        Pattern p = Pattern.compile("^([A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]{1,40})$");
        Matcher m1 = p.matcher(name);
        Matcher m2 = p.matcher(surname);
        boolean b1 = m1.find();
        boolean b2 = m2.find();
        return b1 && b2;
    }
    @Override
    public boolean checkLogin(String login) {
        Pattern p = Pattern.compile("[^a-z0-9_.]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(login);
        return !m.find();
    }

    @Override
    public boolean checkCapacity(String cap) {
        Pattern p = Pattern.compile("^[0-9]{1,5}$");
        Matcher m = p.matcher(cap);
        return m.find();
    }

    @Override
    public boolean checkVIN(String vin) {
        Pattern p = Pattern.compile("[A-HJ-NPR-Z0-9]{17}", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(vin);
        return m.find();
    }

    @Override
    public boolean checkYear(String yr) {
        Pattern p = Pattern.compile("^19[0-9]{2}$|^20[0-9]{2}$");
        Matcher m = p.matcher(yr);
        return m.find();
    }

    @Override
    public boolean checkLicense(String nr) {
        Pattern p = Pattern.compile("^[0-9]{5}/[0-9]{2}/[0-9]{4}$");
        Matcher m = p.matcher(nr);
        return m.find();
    }

    @Override
    public boolean checkDesc(String desc) {
        Pattern p = Pattern.compile("[^a-z0-9.,()!/?\\sżźćńółęąśŻŹĆĄŚĘŁÓŃ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(desc);
        return !m.find();
    }

    @Override
    public boolean checkStatus(String stat) {
        Pattern p = Pattern.compile("[^a-z0-9.,()!/?\\sżźćńółęąśŻŹĆĄŚĘŁÓŃ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stat);
        return !m.find();
    }

    @Override
    //mark and model
    public boolean checkCar(String mark) {
        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mark);
        return !m.find();
    }

    @Override
    public boolean checkDate(String date) {
        Pattern p = Pattern.compile("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
        Matcher m = p.matcher(date);
        return m.find();
    }

    @Override
    public boolean checkPESEL(String psl) {
        Pattern p = Pattern.compile("^[0-9]{11}$");
        Matcher m = p.matcher(psl);
        return m.find();
    }

    @Override
    public boolean checkPhoneNumber(String number) {
        Pattern p = Pattern.compile("^[0-9]{9}$");
        Matcher m = p.matcher(number);
        return m.find();
    }

    @Override
    public boolean checkEmail(String email) {
        Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(email);
        return m.find();
    }
}
