package com.mcraft.site.dao;

import org.springframework.data.repository.CrudRepository;

import entity.AuthmeUser;

public interface AuthMeDAO extends CrudRepository<AuthmeUser, Long>{
	
	AuthmeUser findByUsername(String username);
	
}
