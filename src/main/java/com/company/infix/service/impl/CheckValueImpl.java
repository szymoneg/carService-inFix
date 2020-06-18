package com.company.infix.service.impl;

import com.company.infix.service.CheckValue;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CheckValueImpl implements CheckValue {
    @Override
    public boolean check(String a){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(a);
        boolean b = m.find();

        return b;
    }
}
