package com.mcraft.site.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import entity.UserRole;

 
public interface UserRoleDAO extends CrudRepository<UserRole, Long>{
	
	List<Long> findByAppUser(Long appUser);

}