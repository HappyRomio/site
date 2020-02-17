package com.mcraft.site.dao;


import org.springframework.data.repository.CrudRepository;

import entity.AppUser;
import entity.ChangeEmailRequest;

public interface ChangeEmailRequestDAO extends CrudRepository<ChangeEmailRequest, Long>{

	ChangeEmailRequest findByUser(AppUser appUser);

	ChangeEmailRequest findByToken(String token);
	
}
