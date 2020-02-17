package com.mcraft.site.dao;


import org.springframework.data.repository.CrudRepository;

import entity.AppUser;


 
public interface AppUserDAO extends CrudRepository<AppUser, Long>{

	AppUser findByUserName(String userName);
	
	AppUser findByActivationCode (String activationCode) ;

	AppUser findByEmail (String email) ;

}
