package com.mcraft.site.controller;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mcraft.site.service.UserService;
import com.mcraft.site.utils.EmailSender;
import com.mcraft.site.utils.EncrytedPasswordUtils;

import entity.AppUser;
import entity.ChangeEmailRequest;

@Controller
public class RegController {

    @Autowired
    UserService us;
    @Autowired
    EmailSender emailSender;

    @RequestMapping(value = "/account/reg", method = RequestMethod.GET)
    public ModelAndView regGet(ModelAndView model) {
        model.addObject("message", "");
        model.addObject("appuser", new AppUser());
        model.setViewName("reg");
        return model;
    }

    @RequestMapping(value = "/account/reg", method = RequestMethod.POST)
    public ModelAndView regPost(@ModelAttribute AppUser appuser, ModelAndView model) {
        model.addObject("appuser", appuser);
        AppUser userByName = us.getUserByName(appuser.getUserName());
        AppUser userByMail = us.getUserByEmail(appuser.getEmail());
        if (userByMail == null) {
            if (userByName == null) {
                us.saveUser(appuser);
                AppUser user = us.getUserByName(appuser.getUserName());
                String message = String.format(
                        "Здарвствуйте, %s! \r\n" +
                                "На Вашу электронную почту был зарегистрирован аккаунт на сайте https://mwmw.icu/ \r\n" +
                                "Для подтверждения регистрации перейдите по этой ссылке — https://mwmw.icu/verify/%s \r\n" +
                                "Если Вы не запрашивали отправку данного письма, то просто проигнорируйте его.",
                        user.getUserName(),
                        user.getActivationCode()
                );
                System.out.println("https://mwmw.icu/verify/" + user.getActivationCode());
                emailSender.send(appuser.getEmail(), "Подтверждение регистрации Minecraft Union", message);
                model.addObject("message", "На Ваш E-Mail отправлено письмо с подтверждением");
                model.setViewName("login");
                return model;
            } else {
                model.addObject("message", "Пользователь с таким никнеймом уже существует");
                model.setViewName("reg");
                return model;
            }
        } else {
            model.addObject("message", "Пользователь с таким E-Mail уже существует");
            model.setViewName("reg");
            return model;
        }

    }

    @RequestMapping(value = "/account/reset", method = RequestMethod.GET)
    public String resetPost(@ModelAttribute AppUser appuser, Model model) {
        return "reset";
    }

    @RequestMapping(value = "/account/reset", method = RequestMethod.POST)
    public String resetPost(@RequestParam String mail, Model model) {
        AppUser user = us.getUserByEmail(mail);
        if (user != null) {
            String newpas = UUID.randomUUID().toString().substring(0, 9);
            user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(newpas));
            String message = String.format(
                    "Здравствуйте, %s! Была запрошена смена пароля для аккаунта Minecraft Union, связанного с Вашей электронной почтой. \r\n" +
                            "Новый пароль - %s \r\n" +
                            "Если Вы не инициировали восстановление пароля - немедленно обратитесь в техническую поддержку по адресу https://vk.me/mineunion_main .",
                    user.getUserName(),
                    newpas
            );
            us.updatePassAuthme(newpas, user.getUserName());
            us.updateUser(user);
            emailSender.send(user.getEmail(), "Восстановление пароля Minecraft Union", message);
            model.addAttribute("message", "Новый пароль отправлен Вам на E-Mail");
        } else {
            model.addAttribute("message", "Введённый E-Mail не найден");
        }
        return "login";
    }

    @RequestMapping(value = "/verify/{code}", method = RequestMethod.GET)
    public String activateAccount(Model model, @PathVariable String code) {
        AppUser user = us.getUserByActivationCode(code);
        if (user != null) {
            if (user.getEnabled()) {
                model.addAttribute("message", "Учетная запись уже активирована");
            } else {
                user.setEnabled(true);
                us.updateUser(user);
                model.addAttribute("message", "Учетная запись активирована");
            }
        } else {
            model.addAttribute("message", "Неверная ссылка активации пользователя");
        }
        return "login";
    }

    @RequestMapping(value = "/account/passwordchange", method = RequestMethod.GET)
    public String changePassGet(Model model) {
        return "passwordchange";
    }

    @RequestMapping(value = "/account/passwordchange", method = RequestMethod.POST)
    public ModelAndView changePass(@RequestParam("oldpass") String oldpass, @RequestParam("newpass") String newpass, ModelAndView model) {
        User userPr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPr != null) {
            AppUser user = us.getUserByName(userPr.getUsername());
            model.addObject("user", user);
            model.setViewName("passwordchange");
            if (EncrytedPasswordUtils.matches(oldpass, user.getEncrytedPassword())) {
                user.setEncrytedPassword(EncrytedPasswordUtils.encrytePassword(newpass));
                model.addObject("message", "Ваш пароль успешно изменен");
            } else {
                model.addObject("message", "Неверно введен старый пароль");
            }
            return model;
        } else
            model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/account/emailchange", method = RequestMethod.POST)
    public ModelAndView changeEmailRequest(@RequestParam("oldpass") String oldpass, @RequestParam("newmail") String newmail, ModelAndView model) {
        User userPr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPr != null) {
            AppUser user = us.getUserByName(userPr.getUsername());
            model.addObject("user", user);
            model.setViewName("emailchange");
            if (us.findReq(user) != null) {
                model.addObject("message", "Ссылка для смены E-Mail уже отправлена");
                return model;
            } else {
                if (EncrytedPasswordUtils.matches(oldpass, user.getEncrytedPassword())) {
                    us.changeMailReq(user, newmail);
                    String message = String.format(
                            "Здравствуйте, %s! С Вашего аккаунта поступил запрос на смену адреса электронной почты. \r\n" +
                                    "Для подтверждения перейдите по этой ссылке - https://mwmw.icu/changemail/%s \r\n" +
                                    "Если Вы не запрашивали смены адреса электронной почты, то немедленно обратитесь в техническую поддержку по адресу https://vk.me/mineunion_main .",
                            user.getUserName(),
                            us.findReq(user).getToken()
                    );
                    emailSender.send(user.getEmail(), "Изменение E-Mail Minecraft Union", message);
                    model.addObject("message", "Ссылка для подтверждения отправлена вам на E-Mail");
                } else {
                    model.addObject("message", "Неверный пароль");
                }
                return model;
            }
        } else
            model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/account/emailchange", method = RequestMethod.GET)
    public String changeEmailGet(Model model) {
        return "emailchange";
    }

    @RequestMapping(value = "/changemail/{token}", method = RequestMethod.GET)
    public String changeMailAccept(Model model, @PathVariable String token) {
        ChangeEmailRequest req = us.findReqByToken(token);
        if (req != null) {
            AppUser user = req.getUser();
            user.setEmail(req.getNewEmail());
            us.saveUser(user);
            us.delReq(req);
            model.addAttribute("message", "E-Mail успешно изменён");
        } else {
            model.addAttribute("message", "Ссылка устарела");
        }
        return "login";
    }


}
