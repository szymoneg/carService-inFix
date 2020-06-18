package com.company.infix.service;

import java.security.NoSuchAlgorithmException;

public interface HashPassword {
    public String HashMethod(String pass) throws NoSuchAlgorithmException;
}
