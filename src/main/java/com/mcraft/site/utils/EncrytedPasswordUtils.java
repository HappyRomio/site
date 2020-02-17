package com.mcraft.site.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mcraft.site.dao.AppRoleDAO;

import entity.AppRole;

public class EncrytedPasswordUtils {
 
    // Encryte Password with BCryptPasswordEncoder
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
    public static boolean matches(String passwordBd, String fromPage) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(passwordBd, fromPage);
    }
}