package com.example.loginjwtdemo.service;

import com.example.loginjwtdemo.util.TokenDetailImpl;
import com.example.loginjwtdemo.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private TokenUtils tokenUtils;

    public String login(String name, String password) {
        if (name.equals("hearing") && password.equals("123456")) {
            return tokenUtils.generateToken(new TokenDetailImpl(name, password));
        }
        return null;
    }
}
