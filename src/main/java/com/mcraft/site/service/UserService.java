package com.mcraft.site.service;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mcraft.site.dao.AppRoleDAO;
import com.mcraft.site.dao.AppUserDAO;
import com.mcraft.site.dao.AuthMeDAO;
import com.mcraft.site.dao.ChangeEmailRequestDAO;
import com.mcraft.site.dao.UserRoleDAO;
import com.mcraft.site.utils.EncrytedPasswordUtils;

import entity.AppRole;
import entity.AppUser;
import entity.AuthmeUser;
import entity.ChangeEmailRequest;
import entity.UserRole;

@Service
public class UserService {

    @Autowired
    AppRoleDAO appRoleDao;

    @Autowired
    AppUserDAO appUserDao;

    @Autowired
    UserRoleDAO userRoleDao;

    @Autowired
    AuthMeDAO authmeDao;

    @Autowired
    ChangeEmailRequestDAO cer;

    public List<String> getRoleNames(AppUser appUser) {
        List<String> l = new ArrayList<String>();
        l.add("ROLE_USER");
        return l;
    }

    public AppUser getUserByName(String userName) {
        return appUserDao.findByUserName(userName);
    }

    public boolean saveUser(AppUser user) {

        AppUser userInDB = appUserDao.findByUserName(user.getUserName());

        if (userInDB == null) {

            AuthmeUser authUser = new AuthmeUser();

            authUser.setPassword("$SHA$" + "a1f9d6d409f03e2c" + "$" + DigestUtils.sha256Hex(DigestUtils.sha256Hex(user.getEncrytedPassword()) + "a1f9d6d409f03e2c"));

            user.setEnabled(false);
            user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword()));
            user.setActivationCode(UUID.randomUUID().toString());
            user.setCredits(0L);
            user.setSubscription(false);
            user.setSubscriptionType("Отсутствует");
            user.setDateSubscriptionStart(new GregorianCalendar());
            user.setDateSubscriptionEnd(new GregorianCalendar());
            appUserDao.save(user);

            authUser.setId(user.getUserId());
            authUser.setUsername(user.getUserName());
            authUser.setEmail(user.getEmail());
            authUser.setIsLoged((short) 0);
            authUser.setHasSession((short) 0);
            authUser.setLastlogin(0L);
            authUser.setIp("0.0.0.0");
            authUser.setPitch(0.0f);
            authUser.setRealname("");
            authUser.setRegdate(0);
            authUser.setRegip("0.0.0.0");
            authUser.setWorld("");
            authUser.setX(0);
            authUser.setY(0);
            authUser.setZ(0);
            authUser.setYaw(0.0f);
            authmeDao.save(authUser);

            UserRole ur = new UserRole();
            ur.setAppRole(appRoleDao.findByRoleName("ADMIN"));
            ur.setAppUser(user);
            userRoleDao.save(ur);
            return true;
        } else {
            appUserDao.save(user);
            return false;
        }
    }

    public AppUser getUserByActivationCode(String code) {
        return appUserDao.findByActivationCode(code);
    }

    public AppUser getUserByEmail(String email) {
        return appUserDao.findByEmail(email);
    }

    public void updateUser(AppUser user) {
        appUserDao.save(user);

    }

    public void changeMailReq(AppUser user, String email) {
        ChangeEmailRequest req = new ChangeEmailRequest();
        req.setToken(UUID.randomUUID().toString());
        req.setUser(user);
        req.setAccepted(false);
        req.setNewEmail(email);
        cer.save(req);

    }

    public ChangeEmailRequest findReq(AppUser user) {
        return cer.findByUser(user);
    }

    public ChangeEmailRequest findReqByToken(String token) {
        return cer.findByToken(token);
    }

    public void delReq(ChangeEmailRequest req) {
        cer.delete(req);
    }

    public void updatePassAuthme(String password, String username) {
        AuthmeUser user = authmeDao.findByUsername(username);
        user.setPassword("$SHA$" + "a1f9d6d409f03e2c" + "$" + (DigestUtils.sha256Hex(DigestUtils.sha256Hex(password) + "a1f9d6d409f03e2c")));
        authmeDao.save(user);
    }

    public void updateMailAuthme(String email, String username) {
        AuthmeUser user = authmeDao.findByUsername(username);
        user.setEmail(email);
        authmeDao.save(user);
    }

}
