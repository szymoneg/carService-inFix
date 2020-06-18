package com.company.infix.service.impl;

import com.company.infix.service.HashPassword;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashPasswordImpl implements HashPassword {

    @Override
    public String HashMethod(String pass) throws NoSuchAlgorithmException {
        String passToHash = pass;
        String generatedPass = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(passToHash.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<bytes.length; i++){
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPass = sb.toString();

        return generatedPass;
    }
}
