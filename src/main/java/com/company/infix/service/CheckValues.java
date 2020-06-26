package com.company.infix.service;


public interface CheckValues {
    public boolean checkNameAndSurname(String name, String surname);
    public boolean checkLogin(String login);
    public boolean checkCapacity(String cap);
    public boolean checkPESEL(String psl);
    public boolean checkVIN(String vin);
    public boolean checkYear(String yr);
    public boolean checkLicense(String nr);
    public boolean checkDesc(String desc);
    public boolean checkStatus(String stat);
    public boolean checkCar(String name);
    public boolean checkDate(String date);
    public boolean checkPhoneNumber(String number);
    public boolean checkEmail(String mail);
}

