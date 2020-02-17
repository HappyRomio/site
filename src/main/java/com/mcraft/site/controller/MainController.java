package com.mcraft.site.controller;

import entity.AppUser;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.mcraft.site.service.UserService;
import com.mcraft.site.utils.EmailSender;


@Controller
public class MainController {

    @Autowired
    UserService us;
    @Autowired
    EmailSender emailSender;

    @RequestMapping(value = {"/",}, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        return "index";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfule(Model model) {
        return "login";
    }

    @RequestMapping(value = "/account/login", method = RequestMethod.GET)
    public String adminPage(@RequestParam(required = false) boolean error, Model model) {
        model.addAttribute("message", error ? "Вы ввели неверный логин или пароль" : "");
        return "login";
    }

    @RequestMapping(value = "/account/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard(ModelAndView model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User userPr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AppUser user = us.getUserByName(userPr.getUsername());
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
            String date = format1.format(user.getDateSubscriptionEnd().getTime());

            String status;
            if (user.getSubscription() == true) {
                status = "Активна";
            } else {
                status = "Неактивна";
            }
            model.addObject("status", status);

            model.addObject("date", date);
            model.addObject("user", user);

            model.setViewName("dashboard");
            return model;
        } else
            model.setViewName("login");
        return model;
    }

    @RequestMapping(value = {"/promo",}, method = RequestMethod.GET)
    public String promo(Model model) {
        return "promo";
    }

    @RequestMapping(value = {"/rules",}, method = RequestMethod.GET)
    public String rules(Model model) {
        return "rules";
    }

    @RequestMapping(value = {"/rules/civ",}, method = RequestMethod.GET)
    public String civrules(Model model) {
        return "civrules";
    }

    @RequestMapping(value = {"/rules/cl",}, method = RequestMethod.GET)
    public String clrules(Model model) {
        return "clrules";
    }

    @RequestMapping(value = {"/user-license",}, method = RequestMethod.GET)
    public String userlicsense(Model model) {
        return "user-license";
    }
}