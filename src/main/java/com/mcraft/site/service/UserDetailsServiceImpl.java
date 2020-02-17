package com.mcraft.site.service;

import java.util.ArrayList;
import java.util.List;
 
import entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
 @Autowired
 UserService userService;
 
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = this.userService.getUserByName(userName);
 
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        if (appUser.getEnabled() == false) {
            System.out.println("User not activated, check your email! " + userName);
            throw new UsernameNotFoundException("User " + userName + " User not activated, check your email for activate");
        }
 
        System.out.println("Found User: " + appUser.getUserName());
 
        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.userService.getRoleNames(appUser);
        
        for(String s : roleNames) {
        	System.out.println(s);
        }
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
 
        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncrytedPassword(), grantList);
 
        return userDetails;
    }
 
}
