package com.mcraft.site.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mcraft.site.service.UserService;
import com.mcraft.site.utils.EmailSender;

import entity.AppUser;

@Controller
public class StoreController {
	
	@Autowired
	UserService us;
	@Autowired
	EmailSender emailSender;

	 @RequestMapping(value = "/store", method = RequestMethod.GET)
	    public String store(Model model) {
	    	User userPr =  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	if(userPr != null) {
	    	model.addAttribute("balance",us.getUserByName(userPr.getUsername()).getCredits());
	    	} else {
	    		return "login";
	    	}
	        return "store";
	    }

	    @RequestMapping(value = "/donate", method = RequestMethod.GET)
	    public String donate(Model model) {
	 
	        return "donate";
	    }
	    
	   @RequestMapping(value = "/store/basic", method = RequestMethod.GET)
	    public ModelAndView basic(ModelAndView model) {
	    	
	    	User userPr =  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	if(userPr != null) {
	    		AppUser user = us.getUserByName(userPr.getUsername());
	    		if(user.getCredits() < 49) {
	    			model.addObject("message", "На вашем счету недостаточно средтсв для покупки подписки.");
	    			model.addObject("user", user);
	    			model.setViewName("store");
	    			return model;
	    		} else {
			    		if(user.getSubscription() == false) {
						    		user.setCredits(user.getCredits()- 49);
						    		//user.setSubscription(true);
						    		user.setSubscriptionType("Basic Access");
						    		Calendar c = new GregorianCalendar();
						    		user.setDateSubscriptionStart(c);
						    		c.add(Calendar.MONTH, 1);
						    		user.setDateSubscriptionEnd(c);
						    		us.saveUser(user);
						    	
						    		SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
						        	String date = format1.format(user.getDateSubscriptionEnd().getTime());
						        	model.addObject("date", date);
						    		model.addObject("user", user);
									model.setViewName("dashboard");
						    		return model;
			    		} else {
			    			model.addObject("message", "Подписака уже активирована");
			    			SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
				        	String date = format1.format(user.getDateSubscriptionEnd().getTime());
				        	model.addObject("date", date);
			    			model.addObject("user", user);
							model.setViewName("dashboard");
				    		return model;
			    		}
	    		}
	    	} else {
	    		model.setViewName("login");
	    		return model;
	    	}
	    }

	   @RequestMapping(value = "/store/premium", method = RequestMethod.GET)
	    public ModelAndView premium(ModelAndView model) {
	    	
	    	User userPr =  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	if(userPr != null) {
	    		AppUser user = us.getUserByName(userPr.getUsername());
	    		if(user.getCredits() < 299) {
	    			model.addObject("message", "На вашем счету недостаточно средтсв для покупки подписки.");
	    			model.addObject("user", user);
	    			model.setViewName("store");
	    			return model;
	    		} else {
			    		if(user.getSubscription() == false || user.getSubscriptionType().equals("basic") ) {
						    		user.setCredits(user.getCredits()- 299);
						    		//user.setSubscription(true);
						    		user.setSubscriptionType("Premium Access");
						    		
						    		Calendar c = new GregorianCalendar();
						    		user.setDateSubscriptionStart(c);
						    		c.add(Calendar.MONTH, 1);
						    		user.setDateSubscriptionEnd(c);
						    		
						    		us.saveUser(user);
						    		
						    		SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
						        	String date = format1.format(user.getDateSubscriptionEnd().getTime());
						        	model.addObject("date", date);
						    		model.addObject("user", user);
									model.setViewName("dashboard");
						    		return model;
			    		} else {
			    			model.addObject("message", "Подписака уже активирована");
			    			SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
				        	String date = format1.format(user.getDateSubscriptionEnd().getTime());
				        	model.addObject("date", date);
			    			model.addObject("user", user);
							model.setViewName("dashboard");
				    		return model;
			    		}
	    		}
	    	} else {
	    		model.setViewName("login");
	    		return model;
	    	}
	    }
	   @RequestMapping(value = "/store/extreme", method = RequestMethod.GET)
	    public ModelAndView extreme(ModelAndView model) {
	    	User userPr =  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	if(userPr != null) {
	    		AppUser user = us.getUserByName(userPr.getUsername());
	    		if(user.getCredits() < 499) {
	    			model.addObject("message", "На вашем счету недостаточно средтсв для покупки подписки.");
	    			model.addObject("user", user);
	    			model.setViewName("store");
	    			return model;
	    		} else {
			    		if(user.getSubscription() == false || user.getSubscriptionType().equals("basic") || user.getSubscriptionType().equals("premium")  ) {
						    		user.setCredits(user.getCredits()- 499);
						    		//user.setSubscription(true);
						    		user.setSubscriptionType("Extreme Access");
						    		
						    		Calendar c = new GregorianCalendar();
						    		user.setDateSubscriptionStart(c);
						    		c.add(Calendar.MONTH, 1);
						    		user.setDateSubscriptionEnd(c);
						    		
						    		us.saveUser(user);
						    	
						    		SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
						        	String date = format1.format(user.getDateSubscriptionEnd().getTime());
						        	model.addObject("date", date);
						    		model.addObject("user", user);
									model.setViewName("dashboard");
						    		return model;
			    		} else {
			    			model.addObject("message", "Подписака уже активирована");
			    			SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
				        	String date = format1.format(user.getDateSubscriptionEnd().getTime());
				        	model.addObject("date", date);
			    			model.addObject("user", user);
							model.setViewName("dashboard");
				    		return model;
			    		}
	    		}
	    	} else {
	    		model.setViewName("login");
	    		return model;
	    	}
	    }
}
